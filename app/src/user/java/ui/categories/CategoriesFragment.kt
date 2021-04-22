package ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.syriamarket.databinding.CategoriesFragmentBinding
import kotlinx.android.synthetic.user.product_page_fragment.view.*

class CategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel
    private var _binding: CategoriesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        binding.categories = viewModel
        binding.lifecycleOwner = this
        viewModel.responseCats
        viewModel.balance
        return binding.root
    }

    fun showProgressBar() {
        binding.llPbQueue.visibility = LinearLayout.VISIBLE
    }

    fun hideProgressBar() {
        binding.llPbQueue.visibility = LinearLayout.GONE
    }
}