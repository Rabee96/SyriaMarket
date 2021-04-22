package adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import com.example.syriamarket.pojo.phoneCategories.DataXPh
import kotlinx.android.synthetic.main.categories_fragment_card_t1.view.*
import ui.accounts.createCoupon.CreateCouponViewModel
import java.util.*

class PhoneCategoriesAdapter (
    private var context: Context,
    private var data: ArrayList<DataXPh>,
): RecyclerView.Adapter<PhoneCategoriesAdapter.MyViewHolder>(){
    class MyViewHolder (itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val cat : TextView = itemView.tv_category
        val icon : ImageView = itemView.iv_category
        val btnDelete : ImageView = itemView.iv_btn_delete
        val btnSeeCat: CardView = itemView.card_btn_cat

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.categories_fragment_card_t1,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cat.text = data[position].name
        Glide.with(context).load(data[position].iconUrl).placeholder(R.drawable.logo).into(holder.icon)
        holder.btnSeeCat.setOnClickListener {
            if (data[position].tag != "tg" && data[position].tag != "go" && data[position].tag != "fb" && data[position].tag != "im"){
                val bundle = Bundle()
                val sp = context.getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sp.edit()
                editor.putString("cPhone",data[position]._id)
                Log.e("Rabee","data[position]._id ${data[position]._id}")
                editor.apply()
                bundle.putString("catPhoneID",data[position]._id)
                bundle.putString("catPhoneName",data[position].name)
                bundle.putString("catPhoneCountryTag",data[position].countryTag)
                bundle.putString("catPhoneTag",data[position].tag)
                bundle.putString("catPhonePrice",data[position].price.toString())
                bundle.putString("catPhoneDate",data[position].createdAt)
                bundle.putString("catPhoneImg",data[position].iconUrl)
                it.findNavController().navigate(R.id.action_nav_phonesAndCodesFragment_to_createCountryFragment,bundle)
            }
        }
        holder.btnSeeCat.setOnLongClickListener {
            /*AlertDialog.Builder(context)
                    .setTitle("تأكيد عملية الحذف")
                    .setMessage("هل تريد حذف هذا التصنيف؟") // Specifying a listener allows you to take an action before dismissing the dialog.
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("حذف") { _, _ ->
                        deletePhoneCat()
                    }
                    .setNegativeButton("إلغاء", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()*/
            false
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private fun deletePhoneCat(){

    }
}