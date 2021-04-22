package ui.productPage

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.getUserBalance
import com.example.syriamarket.base.App.Companion.pubgApi
import com.example.syriamarket.base.App.Companion.setUserBalance
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.interfaces.PubgInterface
import com.example.syriamarket.pojo.buyNumberModel.BuyNumberModel
import com.example.syriamarket.pojo.buyProduct.BuyModelBody
import com.example.syriamarket.pojo.buyProduct.BuyProductModel
import kotlinx.android.synthetic.user.product_page_fragment.view.*
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
    private var fromPage = -1
    private var countryID = "N/A"
    private var productImg = "N/A"
    private var productName = "N/A"
    private var productCode = "N/A"
    private var pubGFile = AppConstants.PUBG_FILE
    private var game = AppConstants.PUBG_GAME
    private var productPrice = 0.0
    private var quantity = 1
    private var totalCost = 0.0

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fromPage = requireArguments().getInt("fromPage", -1)
        when (fromPage) {
            1 -> {
                game = requireArguments().getString("game", AppConstants.PUBG_GAME)
                pubGFile = requireArguments().getString("file", AppConstants.PUBG_FILE)
                productID = requireArguments().getString("productID", "N/A")
                productImg = requireArguments().getString("fromPage", "N/A")
                productName = requireArguments().getString("title", "N/A")
                productCode = requireArguments().getString("fromPage", "N/A")
                productPrice = requireArguments().getInt("price", -1).toDouble()
            }
            2 -> {
                countryID = requireArguments().getString("countryID", "N/A")
                productImg = requireArguments().getString("flag", "N/A")
                productName = requireArguments().getString("countryName", "N/A")
                productCode = requireArguments().getString("countryCode", "N/A")
                productPrice = requireArguments().getDouble("countryPrice", -1.0)
            }
            3 -> {

            }

        }
        totalCost = quantity * productPrice
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val service: PubgInterface = pubgApi.create(PubgInterface::class.java)
        root = inflater.inflate(R.layout.product_page_fragment, container, false)
        if (fromPage == 2) {
            root.tv_player_name.visibility = TextView.GONE
            root.tv_card_balance.visibility = TextView.GONE
            root.textView5.visibility = TextView.GONE
            root.et_player_id.visibility = EditText.GONE
            root.btn_check_player_id.visibility = Button.GONE
            //===============================================
            root.textView11.visibility = TextView.VISIBLE
            root.textView13.visibility = TextView.VISIBLE
            root.tv_total_cost.visibility = TextView.VISIBLE
            root.iv_btn_remove.visibility = ImageView.VISIBLE
            root.iv_btn_add.visibility = ImageView.VISIBLE
            root.et_product_quantity.visibility = TextView.VISIBLE
        }
        root.btn_check_player_id.setOnClickListener {
            showProgressBar()
            response = service.getPubgPlayerName(pubGFile, game, root.et_player_id.text.toString())
            Log.e("Rabee", "FILE ; $pubGFile and game : $game")
            response.enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    if (response.code() == 200) {
                        hideProgressBar()
                        root.tv_player_name.text = response.body()
                    }
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "الرجاء التحقق من الـ ID", Toast.LENGTH_LONG)
                        .show()
                    Log.e("Rabee", "Error :" + t.message + "Cause" + t.cause)
                }
            })
        }
        root.tv_card_price.text = productPrice.toString()
        root.tv_product_page_title.text = productName
        root.tv_card_balance.text = requireArguments().getString("title", "N/A")
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProductPageViewModel::class.java)
        root.btn_buy_product.setOnClickListener {
            if (getUserBalance().toDouble() - totalCost >= 0.0)
                when (fromPage) {
                    1 -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("تأكيد عملية الشراء")
                            .setMessage("شراء ${root.tv_card_balance.text} مقابل ${root.tv_card_price.text}") // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("شراء") { _, _ ->
                                buyNormalProduct()
                            }
                            .setNegativeButton("إلغاء", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    2 -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("تأكيد عملية الشراء")
                            .setMessage("شراء ${root.et_product_quantity.text} مقابل ${root.tv_total_cost.text}") // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("شراء") { _, _ ->
                                buyWhatsAppProduct()
                            }
                            .setNegativeButton("إلغاء", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                    3 -> {
                        AlertDialog.Builder(requireContext())
                            .setTitle("تأكيد عملية الشراء")
                            .setMessage("شراء ${root.tv_card_balance.text} مقابل ${root.tv_card_price.text}") // Specifying a listener allows you to take an action before dismissing the dialog.
                            // The dialog is automatically dismissed when a dialog button is clicked.
                            .setPositiveButton("شراء") { _, _ ->
                                buySMSHUB()
                            }
                            .setNegativeButton("إلغاء", null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show()
                    }
                }
            else
                Toast.makeText(requireContext(), "لايوجد رصيد كافي.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        Glide.with(requireActivity()).load(productImg).placeholder(R.drawable.logo)
            .into(root.iv_product)
        root.iv_btn_add.setOnClickListener {
            if (getUserBalance().toInt() - totalCost > 0) {
                quantity++
                totalCost = quantity * productPrice
                root.et_product_quantity.setText(quantity.toString())
                root.tv_total_cost.text = totalCost.toString()
            } else
                Toast.makeText(requireContext(), "لايوجد رصيد كافي.", Toast.LENGTH_LONG).show()
        }
        root.et_product_quantity.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                if (event.action == KeyEvent.ACTION_DOWN) {
                    when (keyCode) {
                        KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                            if (!root.et_product_quantity.text.isNullOrBlank()) {
                                if (getUserBalance().toInt() - totalCost > 0) {
                                    quantity = root.et_product_quantity.text.toString().toInt()
                                    totalCost = quantity * productPrice
                                    root.tv_total_cost.text = totalCost.toString()
                                } else
                                    Toast.makeText(
                                        requireContext(),
                                        "لايوجد رصيد كافي.",
                                        Toast.LENGTH_LONG
                                    ).show()
                            }
                            return true
                        }
                        else -> {
                        }
                    }
                }
                return false
            }
        })
        root.iv_btn_remove.setOnClickListener {
            if (quantity > 1) {
                quantity--
                totalCost = quantity * productPrice
                root.et_product_quantity.setText(quantity.toString())
                root.tv_total_cost.text = totalCost.toString()
            } else
                Toast.makeText(requireContext(), "لا يمكن طلب كمية أقل من 1.", Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun buyWhatsAppProduct() {
        if (getUserBalance().toDouble() - totalCost >= 0) {
            val productsService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
            val responseProducts = productsService.buyNumber(
                BuyNumberModel(
                    "W",
                    countryID,
                    quantity
                )
            )
            responseProducts.enqueue(object : Callback<BuyNumberModel> {
                override fun onResponse(
                    call: Call<BuyNumberModel>,
                    response: Response<BuyNumberModel>
                ) {
                    Log.e("RabeeBuy", "Response Message" + response.body().toString())
                    Log.e("RabeeBuy", "Response Code" + response.code().toString())
                    Log.e("RabeeBuy", "Error Message" + response.errorBody()?.string())

                    if (response.code() == 201) {
                        if (response.body() != null) {
                            Toast.makeText(context, "جاري مراجعة الطلب.", Toast.LENGTH_LONG).show()
                            val newBalance = getUserBalance().toDouble() - totalCost
                            setUserBalance(newBalance.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<BuyNumberModel>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }

            })
        } else {
            Toast.makeText(context, "لا يوجد رصيد كافي.", Toast.LENGTH_LONG).show()
        }
    }

    private fun buyNormalProduct() {
        if (getUserBalance().toDouble() - root.tv_card_price.text.toString().toDouble() >= 0.0) {
            Log.e("Rabee", "productID $productID")
            val productsService: ApiInterface = App.signedIn(token).create(ApiInterface::class.java)
            val responseProducts = productsService.buyProduct(BuyModelBody(productID))
            responseProducts.enqueue(object : Callback<BuyProductModel> {
                override fun onResponse(
                    call: Call<BuyProductModel>,
                    response: Response<BuyProductModel>
                ) {
                    Log.e("RabeeBuyNumber", response.body().toString())
                    if (response.code() == 201) {
                        if (response.body() != null) {
                            val responseBought = response.body() as BuyProductModel
                            Toast.makeText(context, "جاري مراجعة الطلب.", Toast.LENGTH_LONG).show()
                            val newBalance =
                                getUserBalance().toInt() - (response.body() as BuyProductModel).createdOrder.totalPrice
                            setUserBalance(newBalance.toString())
                            val bundle = Bundle()
                            bundle.putString("orderId", responseBought.createdOrder.orderId)
                            bundle.putString("createdAt", responseBought.createdOrder.createdAt)
                            bundle.putBoolean(
                                "isDelivered",
                                responseBought.createdOrder.isDelivered
                            )
                            bundle.putString(
                                "price",
                                responseBought.createdOrder.totalPrice.toString()
                            )
                            bundle.putString("productID", responseBought.createdOrder._id)
                            findNavController().navigate(
                                R.id.action_productPageFragment_to_purchasedProductReviewFragment,
                                bundle
                            )
                        }
                    }
                }

                override fun onFailure(call: Call<BuyProductModel>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }

            })
        } else {
            Toast.makeText(context, "لا يوجد رصيد كافي.", Toast.LENGTH_LONG).show()
        }
    }

    fun buySMSHUB() {

    }

    fun showProgressBar() {
        root.ll_pb_queue.visibility = LinearLayout.VISIBLE
    }

    fun hideProgressBar() {
        root.ll_pb_queue.visibility = LinearLayout.GONE
    }
}