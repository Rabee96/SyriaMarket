package com.example.syriamarket.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import com.example.syriamarket.pojo.myPurchasesModel.UserOrder
import kotlinx.android.synthetic.main.my_purchases_card_t1.view.*
import java.util.*

class MyPurchasesAdapter(
        private val context: Context,
        private val data: ArrayList<UserOrder>,
) : RecyclerView.Adapter<MyPurchasesAdapter.MyViewHolder>(), Filterable{

    var dataFilteredCopyList = ArrayList<UserOrder>()

    init {
        dataFilteredCopyList = data
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val purchaseDate: TextView = itemView.tv_product_date_of_purchase
        val purchaseStatus: View = itemView.v_item_status
        val purchaseOrderNumber: TextView = itemView.tv_order_number
        val purchasePrice: TextView = itemView.tv_product_price
        val btnSeeDetails: CardView = itemView.cv_purchase_card
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.my_purchases_card_t1, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.purchaseDate.text = dataFilteredCopyList[position].createdAt
        holder.purchaseOrderNumber.text = dataFilteredCopyList[position].orderId
        holder.purchasePrice.text = dataFilteredCopyList[position].totalPrice.toString()
        if (dataFilteredCopyList[position].isDelivered)
            holder.purchaseStatus.backgroundTintList = ColorStateList.valueOf(Color.GREEN)
        else
            holder.purchaseStatus.backgroundTintList = ColorStateList.valueOf(Color.RED)
        holder.btnSeeDetails.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("productID", dataFilteredCopyList[position]._id)
            bundle.putString("createdAt", dataFilteredCopyList[position].createdAt)
            bundle.putBoolean("isDelivered", dataFilteredCopyList[position].isDelivered)
            bundle.putString("price", dataFilteredCopyList[position].totalPrice.toString())
            bundle.putString("orderId", dataFilteredCopyList[position].orderId)
            it.findNavController().navigate(R.id.action_myPurchasesFragment_to_purchasedProductReviewFragment, bundle)
        }

    }

    override fun getItemCount(): Int {
        return dataFilteredCopyList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var isCompleted = ""
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    dataFilteredCopyList = data
                } else {
                    val resultList = ArrayList<UserOrder>()
                    for (row in data) {
                        isCompleted = if (row.isDelivered)
                            "مكتمل"
                        else
                            "قيد المراجعة"
                        if (isCompleted == charSearch) {
                            resultList.add(row)
                        }
                    }
                    dataFilteredCopyList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = dataFilteredCopyList
                return filterResults
            }
            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilteredCopyList = results?.values as ArrayList<UserOrder>
                notifyDataSetChanged()
            }

        }
    }
}


