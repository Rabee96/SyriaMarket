package adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.adapter.helper.BaseViewHolder
import com.example.syriamarket.pojo.allUsers.DataXX
import kotlinx.android.synthetic.admin.all_accounts_card_t2.view.*

class AllUsersAdapter(
    private val context: Context,
    private val data: ArrayList<DataXX>,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    companion object {
        private val TYPE_LABEL = 0
        private val TYPE_DATA = 1
    }

    var first = true
    init {
        if (data[0].balance != -1){
            data.add(0,DataXX("","",0,"","","","",""))
            data[0].balance = -1
        }

    }
    class MyViewHolder2(itemView: View) : BaseViewHolder<DataXX>(itemView) {
        val userName: TextView = itemView.tv_user_name
        val userRole: TextView = itemView.tv_user_role
        val userRank: TextView = itemView.tv_user_type
        val userBalance: TextView = itemView.tv_user_balance
        val btnSeeMore: ImageView = itemView.iv_user_details
        override fun bind(context: Context, item: DataXX, fromHome: Boolean) {
            btnSeeMore.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("userID", item._id)
                bundle.putString("userName", item.name)
                bundle.putString("userEmail", item.email)
                bundle.putString("userRole", item.role)
                bundle.putString("userRank", item.userType)
                bundle.putInt("userBalance", item.balance)
                bundle.putString("userAddress", item.address)
                bundle.putString("userPhone", item.phone)
                it.findNavController()
                    .navigate(R.id.action_nav_manage_accounts_to_userProfileFragment, bundle)
            }
            userName.text = item.name
            if (item.role == "user")
                userRole.text = "مستخدم"
            else
                userRole.text = "مدير"
            when(item.userType){
                "A" ->{
                    userRank.text = "برونزي"
                }
                "B"->{
                    userRank.text = "فضي"
                }
                "C"->{
                    userRank.text = "ذهبي"
                }
            }
            userBalance.text = item.balance.toString()
        }

    }

    class MyViewHolder1(itemView: View) : BaseViewHolder<DataXX>(itemView) {
        override fun bind(context: Context, item: DataXX, fromHome: Boolean) {
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return when (viewType) {
            TYPE_LABEL -> {
                val itemView = LayoutInflater.from(context)
                    .inflate(R.layout.all_accounts_card_t1, parent, false)
                MyViewHolder1(itemView)
            }
            TYPE_DATA -> {
                val itemView = LayoutInflater.from(context)
                    .inflate(R.layout.all_accounts_card_t2, parent, false)
                MyViewHolder2(itemView)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val element = data[position]
        when (holder) {
            is MyViewHolder1 -> {
                holder.bind(context, element,false)
            }
            is MyViewHolder2 -> {
                holder.bind(context, element,false)
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_LABEL
            else -> TYPE_DATA
        }
    }
}