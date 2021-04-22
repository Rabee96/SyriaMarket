package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.R
import com.example.syriamarket.pojo.getAllCountryPhonesResponse.DataXACP
import kotlinx.android.synthetic.admin.phone_product_card.view.*
import java.util.*

class PhoneNumbersAdapter (private var context: Context,private var data: ArrayList<DataXACP>): RecyclerView.Adapter<PhoneNumbersAdapter.MyViewHolder>(){
    class MyViewHolder (itemView: View)  :  RecyclerView.ViewHolder(itemView) {
        val phoneNumber : TextView = itemView.tv_phone_product_number
        val activationCode : TextView = itemView.tv_code_phone_product_number
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.phone_product_card,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.phoneNumber.text = data[position].phoneNumber
        holder.activationCode.text = data[position].codeNumber
    }

    override fun getItemCount(): Int {
        return data.size
    }
}