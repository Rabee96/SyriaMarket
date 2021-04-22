package com.example.syriamarket.adapter

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
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.pojo.country.DataXC
import kotlinx.android.synthetic.main.categories_fragment_card_t1.view.*
import kotlinx.android.synthetic.main.whatsapp_products_card_t1.view.*
import java.util.*

class CountriesAdapter (
    private var context: Context,
    private var data: ArrayList<DataXC>,
): RecyclerView.Adapter<CountriesAdapter.MyViewHolder>() {
    class MyViewHolder (itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val countryName : TextView = itemView.tv_country_name
        val icon : ImageView = itemView.iv_country_flag
        val countryCode : TextView  = itemView.tv_country_code
        val countryPrice : TextView  = itemView.tv_country_price
        val countryCard: ConstraintLayout = itemView.country_card

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.whatsapp_products_card_t1,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.countryName.text = data[position].name
        Glide.with(context).load(data[position].flag).placeholder(R.drawable.logo).into(holder.icon)
        holder.countryCode.text = ("+"+data[position].countryCode)
        holder.countryPrice.text = data[position].price.toString()
        holder.countryCard.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("fromPage",AppConstants.WhatsAppProductPage)
            bundle.putString("countryID",data[position]._id)
            bundle.putString("flag",data[position].flag)
            bundle.putString("countryName",data[position].name)
            bundle.putString("countryCode",data[position].countryCode)
            bundle.putDouble("countryPrice",data[position].price)
            it.findNavController().navigate(R.id.action_nav_whatsappProductsFragment_to_productPageFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}