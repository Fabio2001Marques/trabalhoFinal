package pt.ipg.trabalho_final

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TestBaseDados {

    private fun getAppContext() = InstrumentationRegistry.getInstrumentation().targetContext
    private fun getBDCovidOpenHelper() = BDCovidOpenHelper(getAppContext())
    private fun getTabelaVacinas(db: SQLiteDatabase) = TabelaVacinas(db)
    private fun getTabelaPessoas(db: SQLiteDatabase) = TabelaPessoas(db)
    private fun getTabelaEnfermeiros(db: SQLiteDatabase) = TabelaEnfermeiros(db)
    private fun getTabelaDose(db: SQLiteDatabase) = TabelaDose(db)

    private fun insertVacina(tabelaVacinas: TabelaVacinas, vacina: Vacina): Long {
        val id = tabelaVacinas.insert(vacina.toContentValues())
        assertNotEquals(-1, id)

        return id
    }

    private fun GetVacinaBd(tabelaCategorias: TabelaVacinas, id: Long): Vacina {
        val cursor = tabelaCategorias.query(
            TabelaVacinas.TODOS_CAMPOS,
            "${BaseColumns._ID}=?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        assertNotNull(cursor)
        assert(cursor!!.moveToNext())

        return Vacina.fromCursor(cursor)

    }

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BDCovidOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {

        val db = getBDCovidOpenHelper().readableDatabase
        assert(db.isOpen)
        db.close()
    }
    @Test

    fun consegueInserirVacinas(){

        val db = getBDCovidOpenHelper().writableDatabase

        insertVacina(getTabelaVacinas(db), Vacina(nome ="Moderna",quantidade = 15,data = "26/05/2021"))

        db.close()

    }

    @Test

    fun consegueAlterarVacinas(){

        val db = getBDCovidOpenHelper().writableDatabase

        val tabelaVacinas = getTabelaVacinas(db)
        val vacina = Vacina(nome ="Moderna",quantidade = 15,data = "26/05/2021")
        vacina.id = insertVacina(tabelaVacinas, vacina)
        vacina.nome = "phizer"

        val registosAlterados = tabelaVacinas.update(
            vacina.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(vacina.id.toString())
        )

        assertEquals(1, registosAlterados)

        db.close()
    }

    @Test

    fun consegueApagarVacinas() {

        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db)
        val vacina = Vacina(nome ="Teste",quantidade = 15,data = "26/05/2021")
        vacina.id = insertVacina(tabelaVacinas, vacina)

        val registosApagados = tabelaVacinas.delete("${BaseColumns._ID}=?",arrayOf(vacina.id.toString()))
        assertEquals(1, registosApagados)

        db.close()
    }

    @Test

    fun consegueLerVacinas() {

        val db = getBDCovidOpenHelper().writableDatabase
        val tabelaVacinas = getTabelaVacinas(db)

        val vacina = Vacina(nome ="Astrazeneca",quantidade = 20,data = "26/05/2021")
        vacina.id = insertVacina(tabelaVacinas, vacina)

        val vacinaBd = GetVacinaBd(tabelaVacinas, vacina.id)
        assertEquals(vacina, vacinaBd)

        db.close()
    }


}