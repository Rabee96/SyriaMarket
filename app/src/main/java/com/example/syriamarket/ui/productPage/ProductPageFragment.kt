package com.example.syriamarket.ui.productPage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.getUserBalance
import com.example.syriamarket.interfaces.PubgInterface
import com.example.syriamarket.base.App.Companion.pubgApi
import com.example.syriamarket.base.App.Companion.setUserBalance
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.buyProduct.BuyModelBody
import com.example.syriamarket.pojo.buyProduct.BuyProductModel
import kotlinx.android.synthetic.main.product_page_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductPageFragment : Fragment() {

    companion object {
        fun newInstance() = ProductPageFragment()
    }

    private lateinit var viewModel: ProductPageViewModel
    private lateinit var root: View
    private lateinit var response: Call<String>
    private var productID = "N/A"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val service: PubgInterface = pubgApi.create(PubgInterface::class.java)
        root = inflater.inflate(R.layout.product_page_fragment, container, false)
        root.btn_check_player_id.setOnClickListener {
            response = service.getPubgPlayerName(AppConstants.PUBG_FILE,AppConstants.PUBG_GAME, root.et_player_id.text.toString())
            response.enqueue(object :Callback<String>{
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == 200) {
                        root.tv_player_name.text = response.body()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("Rabee", "Error :" + t.message + "Cause" + t.cause)
                }
            })
        }
        root.tv_card_price.text = requireArguments().getInt("price",-1).toString()
        root.tv_product_page_title.text = requireArguments().getString("title","N/A")
        productID = requireArguments().getString("productID","N/A")
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)
        root.btn_buy_product.setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setTitle("تأكيد عملية الشراء")
                    .setMessage("شراء ${root.tv_card_balance.text} مقابل ${root.tv_card_price.text}") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("شراء") { _, _ ->
                        if (getUserBalance().toInt() - root.tv_card_price.text.toString().toInt() >= 0){
                            Log.e("Rabee","productID $productID")
                            val productsService: ApiInterface = App.signedIn(App.token).create(ApiInterface::class.java)
                            val responseProducts = productsService.buyProduct(BuyModelBody(productID))
                            responseProducts.enqueue(object :Callback<BuyProductModel> {
                                override fun onResponse(call: Call<BuyProductModel>, response: Response<BuyProductModel>) {
                                    Log.e("RabeeBuy", response.body().toString())
                                    if (response.code() == 201) {
                                        if (response.body() != null){
                                            Toast.makeText(context,"جاري مراجعة الطلب.", Toast.LENGTH_LONG).show()
                                            val newBalance = getUserBalance().toInt() - (response.body() as BuyProductModel).createdOrder.totalPrice
                                            setUserBalance(newBalance.toString())
                                        }
                                    }
                                }

                                override fun onFailure(call: Call<BuyProductModel>, t: Throwable) {
                                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                                }

                            })
                        }else{
                            Toast.makeText(context,"لا يوجد رصيد كافي.", Toast.LENGTH_LONG).show()
                        }

                    }
                    .setNegativeButton("إلغاء", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
        }
    }


}