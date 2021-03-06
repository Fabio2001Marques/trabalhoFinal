package pt.ipg.trabalho_final

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import pt.ipg.trabalho_final.databinding.ActivityMainBinding
import pt.ipg.trabalho_final.ui.doses.EditaDoseFragment
import pt.ipg.trabalho_final.ui.doses.EliminaDoseFragment
import pt.ipg.trabalho_final.ui.doses.ListaDosesFragment
import pt.ipg.trabalho_final.ui.doses.NovaDoseFragment
import pt.ipg.trabalho_final.ui.enfermeiros.EditaEnfermeiroFragment
import pt.ipg.trabalho_final.ui.enfermeiros.EliminaEnfermeiroFragment
import pt.ipg.trabalho_final.ui.enfermeiros.ListaEnfermeirosFragment
import pt.ipg.trabalho_final.ui.enfermeiros.NovoEnfermeiroFragment
import pt.ipg.trabalho_final.ui.pessoas.EditaPessoaFragment
import pt.ipg.trabalho_final.ui.pessoas.EliminaPessoaFragment
import pt.ipg.trabalho_final.ui.pessoas.ListaPessoasFragment
import pt.ipg.trabalho_final.ui.pessoas.NovaPessoaFragment
import pt.ipg.trabalho_final.ui.vacinas.EditaVacinaFragment
import pt.ipg.trabalho_final.ui.vacinas.EliminaVacinaFragment
import pt.ipg.trabalho_final.ui.vacinas.ListaVacinasFragment
import pt.ipg.trabalho_final.ui.vacinas.NovaVacinaFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var menu: Menu

    var menuAtual = R.menu.activity_main_drawer
        set(value) {
            field = value
            invalidateOptionsMenu()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.ListaEnfermeirosFragment, R.id.ListaPessoasFragment,R.id.ListaVacinasFragment,R.id.ListaDosesFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        DadosApp.activity = this
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(menuAtual, menu)
        this.menu = menu
        when (menuAtual) {
            R.menu.menu_enfermeiros -> {
                atualizaMenuListaEnfermeiros(false)
            }
            R.menu.menu_pessoas -> {
                atualizaMenuListaPessoas(false)
            }
            R.menu.menu_vacinas -> {
                atualizaMenuListaVacinas(false)
            }
            R.menu.menu_dose -> {
                atualizaMenuListaDoses(false)
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.


           val opcaoProcessada = when (item.itemId) {
                R.id.action_settings -> {
                    Toast.makeText(this, R.string.versao, Toast.LENGTH_LONG).show()
                    true
                }

                else -> when (menuAtual) {
                    R.menu.menu_enfermeiros -> (DadosApp.fragment as ListaEnfermeirosFragment).processaOpcaoMenu(item)
                    R.menu.menu_novo_enfermeiro -> (DadosApp.fragment as NovoEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_enfermeiro -> (DadosApp.fragment as EditaEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_enfermeiro -> (DadosApp.fragment as EliminaEnfermeiroFragment).processaOpcaoMenu(item)
                    R.menu.menu_pessoas -> (DadosApp.fragment as ListaPessoasFragment).processaOpcaoMenu(item)
                    R.menu.menu_nova_pessoa -> (DadosApp.fragment as NovaPessoaFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_pessoa -> (DadosApp.fragment as EditaPessoaFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_pessoa -> (DadosApp.fragment as EliminaPessoaFragment).processaOpcaoMenu(item)
                    R.menu.menu_vacinas -> (DadosApp.fragment as ListaVacinasFragment).processaOpcaoMenu(item)
                    R.menu.menu_nova_vacina -> (DadosApp.fragment as NovaVacinaFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_vacina -> (DadosApp.fragment as EditaVacinaFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_vacina -> (DadosApp.fragment as EliminaVacinaFragment).processaOpcaoMenu(item)
                    R.menu.menu_dose -> (DadosApp.fragment as ListaDosesFragment).processaOpcaoMenu(item)
                    R.menu.menu_nova_dose -> (DadosApp.fragment as NovaDoseFragment).processaOpcaoMenu(item)
                    R.menu.menu_edita_dose -> (DadosApp.fragment as EditaDoseFragment).processaOpcaoMenu(item)
                    R.menu.menu_elimina_dose -> (DadosApp.fragment as EliminaDoseFragment).processaOpcaoMenu(item)
                    else -> false
                }
            }
            return if (opcaoProcessada) true else super.onOptionsItemSelected(item)

        }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun atualizaMenuListaEnfermeiros(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_enfermeiros).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_enfermeiro).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaPessoas(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_pessoas).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_pessoas).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaVacinas(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_vacinas).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_vacinas).setVisible(mostraBotoesAlterarEliminar)
    }

    fun atualizaMenuListaDoses(mostraBotoesAlterarEliminar : Boolean) {
        menu.findItem(R.id.action_alterar_dose).setVisible(mostraBotoesAlterarEliminar)
        menu.findItem(R.id.action_eliminar_dose).setVisible(mostraBotoesAlterarEliminar)
    }

    fun navegaListaEnfermeiros(view: View) {
        findNavController(view.id).navigate(R.id.action_nav_home_to_ListaEnfermeirosFragment)
    }

    fun navegaListaPessoas(view: View) {
        findNavController(view.id).navigate(R.id.action_nav_home_to_ListaPessoasFragment)
    }

    fun navegaListaVacinas(view: View) {
        findNavController(view.id).navigate(R.id.action_nav_home_to_ListaVacinasFragment)
    }

    fun navegaListaDoses(view: View) {
        findNavController(view.id).navigate(R.id.action_nav_home_to_listaDosesFragment)
    }

}