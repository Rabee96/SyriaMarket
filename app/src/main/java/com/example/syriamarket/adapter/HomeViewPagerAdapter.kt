package com.example.syriamarket.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.syriamarket.R
import kotlinx.android.synthetic.main.fragment_home_pager_card.view.*

class HomeViewPagerAdapter(private val context: Context, private val imageList: ArrayList<String>): PagerAdapter(){
    private lateinit var ImgList:List<Int>
    lateinit var layoutInflater: LayoutInflater


    override fun getCount(): Int {
        return imageList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.fragment_home_pager_card,container,false)
        val img = view.iv_pager
        Glide.with(context).load(imageList[position]).placeholder(R.drawable.logo).into(img)
        container.addView(view,0)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}