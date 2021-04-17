package com.example.syriamarket.adapter

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.adapter.helper.BaseViewHolder
import com.example.syriamarket.base.App.Companion.getUserBalance
import com.example.syriamarket.pojo.cats.Categories
import com.example.syriamarket.pojo.products.DataXP
import com.example.syriamarket.pojo.products.Products
import kotlinx.android.synthetic.main.product_card_t1.view.*
import kotlinx.android.synthetic.main.product_card_t2.view.*
import java.util.*


class ProductsAdapter(
        private val context: Context,
        private val data: ArrayList<DataXP>,
        private val type: Int,private val fromHome: Boolean): RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private val TYPE_CATEGORY = 0
        private val TYPE_HOME = 1
    }

    class MyViewHolder2(itemView: View)  : BaseViewHolder<DataXP>(itemView){
        val productQuantity : TextView = itemView.tv_product_quantity_card
        val productCard : ConstraintLayout = itemView.cl_card_product
        val productPrice : TextView = itemView.tv_product_price_card
        override fun bind(context: Context,item: DataXP,fromHome: Boolean) {
            productCard.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("productID",item._id)
                bundle.putString("title",item.title)
                bundle.putInt("price",item.priceA)
                it.findNavController().navigate(R.id.action_allProductsFragment_to_productPageFragment,bundle)
                /*AlertDialog.Builder(context)
                        .setTitle("تأكيد عملية الشراء")
                        .setMessage("شراء ${item.title} مقابل ${item.priceA}") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("شراء") { _, _ ->
                            Toast.makeText(context,"جاري مراجعى الطلب",Toast.LENGTH_LONG).show()
                        }
                        .setNegativeButton("إلغاء", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()*/
            }
            productPrice.text = ("السعر : " + item.priceA.toString() + " نقطة")
            productQuantity.text = item.title
        }

    }

    class MyViewHolder1(itemView: View)  : BaseViewHolder<DataXP>(itemView){
        val productQuantity : TextView = itemView.tv_home_product_quantity
        val productCard : ConstraintLayout = itemView.cl_home_card_product
        val productPrice : TextView = itemView.tv_home_product_price_card
        override fun bind(context: Context,item: DataXP,fromHome: Boolean) {
            productCard.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("productID",item._id)
                bundle.putString("title",item.title)
                bundle.putInt("price",item.priceA)
                if (fromHome)
                    it.findNavController().navigate(R.id.action_nav_home_to_productPageFragment,bundle)
                else
                    it.findNavController().navigate(R.id.action_allProductsFragment_to_productPageFragment,bundle)
                /*AlertDialog.Builder(context)
                        .setTitle("تأكيد عملية الشراء")
                        .setMessage("شراء ${item.title} مقابل ${item.priceA}") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("شراء") { _, _ ->
                            Toast.makeText(context,"جاري مراجعى الطلب",Toast.LENGTH_LONG).show()
                        }
                        .setNegativeButton("إلغاء", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()*/
            }
            productPrice.text = ("السعر : " + item.priceA.toString() + " نقطة")
            productQuantity.text = item.title
        }

    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_CATEGORY -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.product_card_t2, parent, false)
                MyViewHolder2(itemView)
            }
            TYPE_HOME -> {
                val itemView = LayoutInflater.from(context).inflate(R.layout.product_card_t1, parent, false)
                MyViewHolder1(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = data[position]
        when (holder) {
            is MyViewHolder1 -> {
                holder.bind(context,element,fromHome)
            }
            is MyViewHolder2 -> {
                holder.bind(context,element,fromHome)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun getItemViewType(position: Int): Int {
        return when (type) {
            0 -> TYPE_CATEGORY
            1 -> TYPE_HOME
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }
}