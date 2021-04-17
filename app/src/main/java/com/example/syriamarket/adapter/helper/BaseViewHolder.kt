package com.example.syriamarket.adapter.helper

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(context: Context,item: T,fromHome: Boolean)
}