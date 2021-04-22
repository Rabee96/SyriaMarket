package ui.products.productDetails

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreatePhoneCategory
import com.example.syriamarket.pojo.createPhoneCategoryResponse.CreatePhoneCategoryResponse
import com.example.syriamarket.pojo.orderDetails.OrderDetails
import com.example.syriamarket.pojo.updateToDeliverResponse.UpdateToDeliverResponse
import kotlinx.android.synthetic.admin.product_details_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailsFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = ProductDetailsFragment()
    }

    private lateinit var viewModel: ProductDetailsViewModel
    private lateinit var root: View
    private var productID = "N/A"
    private var id = "N/A"
    private var isProduct = false
    private var countryID = "N/A"
    private var productImg = "N/A"
    private var productName = "N/A"
    private var userName = "N/A"
    private var categoryName = "N/A"
    private var orderID = "N/A"
    private var productPrice = 0.0
    private var quantity = 1
    private var totalCost = 0.0
    private val productDetailsService: ApiInterface =
            App.signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        isProduct = requireArguments().getBoolean("isProduct", false)
        if (isProduct) {
            userName = requireArguments().getString("userName", "N/A")
            id = requireArguments().getString("id", "N/A")
            productID = requireArguments().getString("productID", "N/A")
            productName = requireArguments().getString("productTitle", "N/A")
            categoryName = requireArguments().getString("categoryName", "N/A")
            productImg = requireArguments().getString("productImg", "N/A")
            productPrice = requireArguments().getInt("price", -1).toDouble()
            orderID = requireArguments().getString("orderId", "N/A")

        } else {
            countryID = requireArguments().getString("countryID", "N/A")
            productImg = requireArguments().getString("flag", "N/A")
            productName = requireArguments().getString("countryName", "N/A")
            orderID = requireArguments().getString("orderId", "N/A")
            productPrice = requireArguments().getDouble("countryPrice", -1.0)
        }

        totalCost = quantity * productPrice
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.product_details_fragment, container, false)
        if (isProduct){
            root.sc_product_layout.visibility = ScrollView.VISIBLE
            root.rv_phone_product.visibility = RecyclerView.GONE
        }else{
            root.rv_phone_product.visibility = RecyclerView.VISIBLE
            root.sc_product_layout.visibility = ScrollView.GONE
            getOrderByOrderID()
        }
        Glide.with(requireActivity()).load(productImg).placeholder(R.drawable.logo).into(root.iv_product)
        root.tv_user_name.text = userName
        root.tv_product_title.text = productName
        root.tv_category_name.text = categoryName
        root.tv_product_price.text = productPrice.toString()
        root.tv_order_id.text = orderID
        root.btn_accept_request.setOnClickListener(this)

        return root
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.btn_accept_request->{
                updateToDeliver()
            }
        }
    }

    private fun updateToDeliver() {
        productDetailsService.updateToDeliver(id).enqueue(object :
                    Callback<UpdateToDeliverResponse> {
                override fun onResponse(
                        call: Call<UpdateToDeliverResponse>,
                        response: Response<UpdateToDeliverResponse>
                ) {
                    Log.e("Raaa", "responseCode: ${response.code()}  responseBody: ${response.body()}  responseError: ${response.errorBody()?.string()}")
                    when (response.code()) {
                        201 ->{
                            if (response.body() != null) {
                                Toast.makeText(requireContext(),"تم إعتماد الطلب.", Toast.LENGTH_LONG).show()
                            }
                        }
                        404 -> Toast.makeText(requireContext(),"لا يوجد طلب", Toast.LENGTH_LONG).show()
                        500 -> Toast.makeText(requireContext(),"الرجاء إختيار إختصار خدمة جديد", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<UpdateToDeliverResponse>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }

            })

    }

    private fun getOrderByOrderID(){
        productDetailsService.getOrderDetails(orderID).enqueue(object :
                Callback<OrderDetails> {
            override fun onResponse(
                    call: Call<OrderDetails>,
                    response: Response<OrderDetails>
            ) {
                Log.e("Raaa", "responseCode: ${response.code()}  responseBody: ${response.body()}  responseError: ${response.errorBody()?.string()}")
                when (response.code()) {
                    201 ->{
                        if (response.body() != null) {
                        }
                    }
                }
            }

            override fun onFailure(call: Call<OrderDetails>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}