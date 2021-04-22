package com.example.syriamarket.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import com.example.syriamarket.pojo.country.DataXC
import kotlinx.android.synthetic.user.country_card_t1.view.*
import java.util.*

class CountriesCreateAdapter (
        private val context: Context,
        private val data: ArrayList<DataXC>,
): RecyclerView.Adapter<CountriesCreateAdapter.MyViewHolder>() {
    class MyViewHolder (itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val countryName : TextView = itemView.tv_country_name_admin
        val countryCode : TextView = itemView.tv_country_code_admin
        val countryFlag : ImageView = itemView.iv_flag
        val countryCard : CardView = itemView.country_card_admin
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.country_card_t1,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.countryName.text = data[position].name
            holder.countryCode.text = ("+"+data[position].countryCode)
            Glide.with(context).load(data[position].flag).placeholder(R.drawable.logo).into(holder.countryFlag)
            holder.countryCard.setOnClickListener {
                val bundle2 = Bundle()
                bundle2.putString("countryID",data[position]._id)
                it.findNavController().navigate(R.id.action_phoneCountriesFragment_to_nav_whatsappProductsFragment,bundle2)
            }

    }

    override fun getItemCount(): Int {
        return data.size
    }
}