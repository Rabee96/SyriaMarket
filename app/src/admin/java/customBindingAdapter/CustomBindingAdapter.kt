package customBindingAdapter

import adapter.*
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.pojo.allUsers.DataXX
import com.example.syriamarket.pojo.cats.DataX
import com.example.syriamarket.pojo.country.DataXC
import com.example.syriamarket.pojo.getAllCountryPhonesResponse.DataXACP
import com.example.syriamarket.pojo.getAllCoupons.DataXCo
import com.example.syriamarket.pojo.phoneCategories.DataXPh
import com.example.syriamarket.pojo.products.DataXP
import com.example.syriamarket.pojo.refundResponse.DataXR

@BindingAdapter("rvAdapter")
fun rvAdapter(recyclerView: RecyclerView, categories: ArrayList<*>?) {
    if (!categories.isNullOrEmpty())
        recyclerView.adapter = CategoriesAdapterAdmin(recyclerView.context, categories as ArrayList<DataX>)
}

@BindingAdapter("rvAllUsersAdapter")
fun rvAllUsersAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = AllUsersAdapter(recyclerView.context, products as ArrayList<DataXX>)
}

@BindingAdapter("rvCouponsAdapter")
fun rvCouponsAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = CouponAdapter(recyclerView.context, products as ArrayList<DataXCo>)
}

@BindingAdapter("rvPAdapter")
fun rvPAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = ProductsAdapter(recyclerView.context, products as ArrayList<DataXP>,0,false)
}

@BindingAdapter("rvReAdapter")
fun rvReAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = RefundsAdapter(recyclerView.context, products as ArrayList<DataXR>)
}

@BindingAdapter("rvPhAdapter")
fun rvPhAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = PhoneCategoriesAdapter(recyclerView.context, products as ArrayList<DataXPh>)
}

@BindingAdapter("rvCouAdapter")
fun rvCouAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = CountriesCreateAdapter(recyclerView.context, products as ArrayList<DataXC>)
}

@BindingAdapter("rvPhoneCountryAdapter")
fun rvPhoneCountryAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = PhoneNumbersAdapter(recyclerView.context, products as ArrayList<DataXACP>)
}