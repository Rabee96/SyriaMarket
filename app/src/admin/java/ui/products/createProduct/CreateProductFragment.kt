package ui.products.createProduct

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.CreateProductFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreateProduct
import com.example.syriamarket.pojo.productCreateResponse.ProductCreateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CreateProductFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CreateProductFragment()
    }

    private lateinit var viewModel: CreateProductViewModel
    private var _binding: CreateProductFragmentBinding? = null
    private val binding get() = _binding!!
    private val productsService: ApiInterface = App.signedIn(AppConstants.ADMIN_TOKEN).create(
        ApiInterface::class.java
    )
    private var catID = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        catID = requireArguments().getString("catID", "N/A")
        _binding = CreateProductFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateProductViewModel::class.java)
        binding.productsAdminViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getProduct(catID)
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        binding.ivBtnCancelCreateProduct.setOnClickListener(this)
        binding.tvBtnCreateProduct.setOnClickListener(this)
        binding.ivBtnCreateProduct.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_btn_create_product -> {
                binding.clProductsManager.visibility = ConstraintLayout.GONE
                binding.clProductsManagerCreation.visibility = ConstraintLayout.VISIBLE
            }
            R.id.tv_btn_create_product -> {
                createProduct(
                    binding.etProductName.text.toString(),
                    binding.etProductImg.text.toString(),
                    binding.etProductPriceA.text.toString().toDouble(),
                    binding.etProductPriceB.text.toString().toDouble(),
                    binding.etProductPriceC.text.toString().toDouble()
                )
                Log.d("Rabee", "Bigo Clicked and catID $catID")
            }
            R.id.iv_btn_cancel_create_product -> {
                binding.clProductsManager.visibility = ConstraintLayout.VISIBLE
                binding.clProductsManagerCreation.visibility = ConstraintLayout.GONE
            }
        }
    }

    private fun createProduct(name: String, img: String, priceA: Double, priceB: Double, priceC: Double) {
        productsService.createProduct(CreateProduct(catID, priceA, priceB, priceC, name, img))
            .enqueue(object :
                Callback<ProductCreateResponse> {
                override fun onResponse(
                    call: Call<ProductCreateResponse>,
                    response: Response<ProductCreateResponse>
                ) {
                    Log.d("Rabee", "Code : ${response.code()}")
                    if (response.code() == 201) {
                        if (response.body() != null)
                            viewModel.getProduct(catID)
                        binding.clProductsManagerCreation.visibility = ConstraintLayout.GONE
                        binding.clProductsManager.visibility = ConstraintLayout.VISIBLE
                    }
                }

                override fun onFailure(call: Call<ProductCreateResponse>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cause : ${t.cause}")
                }
            })
    }



}