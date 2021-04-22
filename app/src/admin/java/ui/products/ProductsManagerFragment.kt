package ui.products

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.ProductsManagerFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreateCategory
import com.example.syriamarket.pojo.CreatePhone
import com.example.syriamarket.pojo.catCreatedResponse.CatCreatedResponse
import com.example.syriamarket.pojo.phoneCreateResponse.PhoneCreateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsManagerFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = ProductsManagerFragment()
    }

    private lateinit var viewModel: ProductsManagerViewModel
    private var _binding: ProductsManagerFragmentBinding? = null
    private val binding get() = _binding!!
    private val categoryProductAdminService: ApiInterface =
        signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductsManagerFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProductsManagerViewModel::class.java)
        binding.productsManagerViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseCatsAdmin
        viewModel.balance
        binding.tvBtnCreateCategory.setOnClickListener(this)
        binding.ivBtnCancelCreateProductCategory.setOnClickListener(this)
        binding.ivBtnAddNewCategory.setOnClickListener(this)
        return binding.root
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_btn_create_category -> createProductCategory()
            R.id.iv_btn_cancel_create_product_category -> {
                binding.clCategoriesLayoutManager.visibility = ConstraintLayout.VISIBLE
                binding.clCategoriesLayoutCreation.visibility = ConstraintLayout.GONE
            }
            R.id.iv_btn_add_new_category -> {
                binding.clCategoriesLayoutCreation.visibility = ConstraintLayout.VISIBLE
                binding.clCategoriesLayoutManager.visibility = ConstraintLayout.GONE
            }
        }
    }

    private fun createProductCategory() {
        categoryProductAdminService.createProductCategory(
            CreateCategory(
                binding.etCategoryName.text.toString(),
                binding.etCategoryIcon.text.toString()
            )
        ).enqueue(object :
            Callback<CatCreatedResponse> {
            override fun onResponse(
                call: Call<CatCreatedResponse>,
                response: Response<CatCreatedResponse>
            ) {
                Log.e("Rabee", response.body().toString())
                if (response.code() == 201) {
                    Toast.makeText(requireContext(), "تم إضافة التصنيف", Toast.LENGTH_LONG).show()
                    findNavController().navigate(R.id.nav_products_manager)
                    binding.clCategoriesLayoutManager.visibility = ConstraintLayout.VISIBLE
                    binding.clCategoriesLayoutCreation.visibility = ConstraintLayout.GONE
                }
            }

            override fun onFailure(call: Call<CatCreatedResponse>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

}

