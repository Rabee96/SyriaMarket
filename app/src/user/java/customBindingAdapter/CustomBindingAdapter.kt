package customBindingAdapter


import adapter.PhoneCategoriesAdapter
import adapter.ProductsAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.syriamarket.adapter.*
import com.example.syriamarket.pojo.HomeItemModel
import com.example.syriamarket.pojo.cats.DataX
import com.example.syriamarket.pojo.country.DataXC
import com.example.syriamarket.pojo.myPurchasesModel.UserOrder
import com.example.syriamarket.pojo.phoneCategories.DataXPh
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

@BindingAdapter("rvCountriesAdapter")
fun rvCountriesAdapter(recyclerView: RecyclerView, products: ArrayList<*>?) {
    if (!products.isNullOrEmpty())
        recyclerView.adapter = CountriesAdapter(recyclerView.context, products as ArrayList<DataXC>)
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

