package adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.adapter.helper.BaseViewHolder
import com.example.syriamarket.pojo.allUsers.DataXX
import com.example.syriamarket.pojo.products.DataXP
import com.example.syriamarket.pojo.refundResponse.*
import kotlinx.android.synthetic.admin.refund_card_t1.view.*
import kotlinx.android.synthetic.admin.refund_card_t2.view.*
import kotlinx.android.synthetic.main.product_card_t1.view.*
import kotlinx.android.synthetic.main.product_card_t2.view.*
import java.util.ArrayList

class RefundsAdapter(
    private var context: Context,
    private var data: ArrayList<DataXR>,
): RecyclerView.Adapter<BaseViewHolder<*>>()  {


    init {
        if (data[0].totalPrice != -1){
            data.add(0, DataXR("","", isDelivered = false, isPaid = false, orderId = "", phones = listOf(Phone("","","")), product = Product("", Category("","",""),0,0,0,"","",), refund = false, totalPrice = 0, user = User("","",0,"","","","","",)))
            data[0].totalPrice = -1
        }

    }

    class MyViewHolder2(itemView: View)  : BaseViewHolder<DataXR>(itemView){
        val productName : TextView = itemView.tv_refund_product_name
        val userName : TextView = itemView.tv_tv_refund_user_name
        val productPrice : TextView = itemView.tv_refund_product_price
        val btnSeeUserDetails : ImageView = itemView.iv_btn_refund_user_details
        val btnSeeProductDetails : ImageView = itemView.iv_btn_refund_product_details

        override fun bind(context: Context, item: DataXR, fromHome: Boolean) {
            userName.text = item.user.name
            productPrice.text = item.totalPrice.toString()
            btnSeeUserDetails.setOnClickListener {
                val bundleU = Bundle()
                bundleU.putString("userID",item.user._id)
                bundleU.putString("userName",item.user.name)
                bundleU.putString("userEmail",item.user.email)
                bundleU.putString("userRank",item.user.userType)
                bundleU.putString("userRole",item.user.role)
                bundleU.putInt("userBalance",item.user.balance)
                bundleU.putString("userAddress",item.user.address)
                bundleU.putString("userPhone",item.user.phone)
                it.findNavController().navigate(R.id.action_nav_requests_to_userProfileFragment,bundleU)
            }
            if (item.product != null){
                productName.text = item.product.title
                btnSeeProductDetails.setOnClickListener {
                    val bundleP = Bundle()
                    bundleP.putBoolean("isProduct",true)
                    bundleP.putString("userName",item.user.name)
                    bundleP.putString("id", item._id)
                    bundleP.putString("productID", item.product._id)
                    bundleP.putString("productTitle", item.product.title)
                    bundleP.putString("categoryName", item.product.category.name)
                    bundleP.putString("productImg", item.product.url)
                    bundleP.putInt("price", item.totalPrice)
                    bundleP.putString("orderId", item.orderId)
                    it.findNavController().navigate(R.id.action_nav_requests_to_productDetailsFragment,bundleP)
                }
            }else{
                productName.text = "رقم هاتف"
            }

        }

    }

    class MyViewHolder1(itemView: View)  : BaseViewHolder<DataXR>(itemView){

        override fun bind(context: Context, item: DataXR, fromHome: Boolean) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*>  {
        return when (viewType) {
            TYPE_LABEL -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.refund_card_t1, parent, false)
                MyViewHolder1(itemView)
            }
            TYPE_DATA -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.refund_card_t2, parent, false)
                MyViewHolder2(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = data[position]
        when (holder) {
            is MyViewHolder1 -> {
                holder.bind(context, element,false)
            }
            is MyViewHolder2 -> {
                holder.bind(context, element,false)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_LABEL
            else -> TYPE_DATA
        }
    }

    companion object {
        private val TYPE_LABEL = 0
        private val TYPE_DATA = 1
    }

}