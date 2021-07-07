package pt.ipg.trabalho_final.ui.pessoas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import pt.ipg.trabalho_final.DadosApp
import pt.ipg.trabalho_final.MainActivity
import pt.ipg.trabalho_final.R
import pt.ipg.trabalho_final.databinding.FragmentNovoPessoasBinding

class NovaPessoaFragment : Fragment() {
    private var _binding: FragmentNovoPessoasBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        DadosApp.fragment = this
        (activity as MainActivity).menuAtual = R.menu.menu_nova_pessoa


        _binding = FragmentNovoPessoasBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}