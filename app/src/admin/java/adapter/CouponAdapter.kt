package adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.adapter.helper.BaseViewHolder
import com.example.syriamarket.pojo.getAllCoupons.DataXCo
import kotlinx.android.synthetic.admin.coupon_card_t2.view.*
import ui.accounts.createCoupon.CreateCouponViewModel

class CouponAdapter(
        private var context: Context,
        private var data: ArrayList<DataXCo>,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    companion object {
        private val TYPE_LABEL = 0
        private val TYPE_DATA = 1
    }

    init {
        if (data[0].status){
            data.add(0,DataXCo("",0,"",0,true))
            data[0].status = false
        }
    }

    class MyViewHolder2(itemView: View) : BaseViewHolder<DataXCo>(itemView) {
        val couponNumber: TextView = itemView.tv_coupon_number
        val couponBalance: TextView = itemView.tv_coupon_balance
        val couponStatus: TextView = itemView.tv_coupon_status
        val btnDeleteCoupon: ImageView = itemView.iv_btn_coupon_delete
        override fun bind(context: Context, item: DataXCo, fromHome: Boolean) {
            btnDeleteCoupon.setOnClickListener {
                AlertDialog.Builder(context)
                                       .setTitle("تأكيد عملية الحذف")
                                       .setMessage("هل تريد حذف هذا الكوبون؟") // Specifying a listener allows you to take an action before dismissing the dialog.
                                       // The dialog is automatically dismissed when a dialog button is clicked.
                                       .setPositiveButton("حذف") { _, _ ->
                                           CreateCouponViewModel().deleteCoupon(context,item._id)
                                       }
                                       .setNegativeButton("إلغاء", null)
                                       .setIcon(android.R.drawable.ic_dialog_alert)
                                       .show()
            }
            couponNumber.text = item.coponNumber.toString()
            couponBalance.text = item.price.toString()
            if (item.status){
                couponStatus.text = "فعال"
                couponStatus.setTextColor(ColorStateList.valueOf(Color.rgb(45,194,0)))
            }else{
                couponStatus.text = "مستخدم"
                couponStatus.setTextColor(ColorStateList.valueOf(Color.RED))
            }
        }

    }

    class MyViewHolder1(itemView: View) : BaseViewHolder<DataXCo>(itemView) {
        override fun bind(context: Context, item: DataXCo, fromHome: Boolean) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_LABEL -> {
                val itemView = LayoutInflater.from(context)
                        .inflate(R.layout.coupon_card_t1, parent, false)
                MyViewHolder1(itemView)
            }
            TYPE_DATA -> {
                val itemView = LayoutInflater.from(context)
                        .inflate(R.layout.coupon_card_t2, parent, false)
                MyViewHolder2(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }    }

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
}