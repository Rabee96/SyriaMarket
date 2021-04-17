package com.example.syriamarket.customBindingAdapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.adapter.CategoriesAdapter
import com.example.syriamarket.adapter.HomeAdapter
import com.example.syriamarket.adapter.ProductsAdapter
import com.example.syriamarket.adapter.MyPurchasesAdapter
import com.example.syriamarket.pojo.HomeItemModel
import com.example.syriamarket.pojo.cats.DataX
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import com.example.syriamarket.pojo.myPurchasesModel.UserOrder
import com.example.syriamarket.pojo.products.DataXP


@BindingAdapter("rvAdapter")
fun rvAdapter(recyclerView: RecyclerView, categories: ArrayList<*>?) {
    if (!categories.isNullOrEmpty())
        recyclerView.adapter = CategoriesAdapter(recyclerView.context, categories as ArrayList<DataX>)
}

@BindingAdapter("rvPAdapter")
fun rvPAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = ProductsAdapter(recyclerView.context, products as ArrayList<DataXP>,0,false)
}

@BindingAdapter("rvHAdapter")
fun rvHAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = HomeAdapter(recyclerView.context, products as ArrayList<HomeItemModel>)
}

@BindingAdapter("rvMyPurchaseAdapter")
fun rvMyPurchaseAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = MyPurchasesAdapter(recyclerView.context, products as ArrayList<UserOrder>)
}