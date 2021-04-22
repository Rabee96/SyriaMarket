package ui.accounts.allAccounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.databinding.AllAccountsFragmentBinding

class AllAccountsFragment : Fragment() {

    companion object {
        fun newInstance() = AllAccountsFragment()
    }

    private lateinit var viewModel: AllAccountsViewModel
    private var _binding: AllAccountsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AllAccountsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AllAccountsViewModel::class.java)
        binding.allUsersViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseAllUsers
        return binding.root
    }
}