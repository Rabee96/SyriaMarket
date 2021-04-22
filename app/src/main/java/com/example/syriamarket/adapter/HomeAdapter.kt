package com.example.syriamarket.adapter

import adapter.ProductsAdapter
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.pojo.HomeItemModel
import kotlinx.android.synthetic.main.fragment_home_category_card_t1.view.*
import java.util.*

class HomeAdapter(
        private var context: Context,
        private var data: ArrayList<HomeItemModel>,
): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val btnSeeMore : TextView = itemView.tv_btn_see_more
        val homeCategory : TextView = itemView.tv_home_category
        val homeProductsList : RecyclerView = itemView.rv_home_products_list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.fragment_home_category_card_t1, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.btnSeeMore.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("catID",data[position].homeCatsID)
            it.findNavController().navigate(R.id.action_nav_home_to_allProductsFragment,bundle)
        }
        holder.homeCategory.text = data[position].homeCatsName
        holder.homeProductsList.adapter = ProductsAdapter(context,data[position].homeProducts,1,true)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}