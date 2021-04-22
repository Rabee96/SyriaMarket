package adapter

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
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.pojo.cats.DataX
import kotlinx.android.synthetic.main.categories_fragment_card_t1.view.*
import java.util.*

class CategoriesAdapterAdmin(
    private var context: Context,
    private var data: ArrayList<DataX>,
): RecyclerView.Adapter<CategoriesAdapterAdmin.MyViewHolder>() {
    class MyViewHolder (itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val cat : TextView = itemView.tv_category
        val icon : ImageView = itemView.iv_category
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
        Glide.with(context).load(data[position].photo).placeholder(R.drawable.logo).into(holder.icon)
        holder.btnSeeCat.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("catID",data[position]._id)
            it.findNavController().navigate(R.id.action_nav_products_manager_to_createProductFragment,bundle)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}