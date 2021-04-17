package com.example.syriamarket.ui.myPurchases

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.adapter.MyPurchasesAdapter
import com.example.syriamarket.databinding.MyPurchasesFragmentBinding
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import com.google.android.material.tabs.TabLayout

class MyPurchasesFragment : Fragment() {

    companion object {
        fun newInstance() = MyPurchasesFragment()
    }

    private lateinit var viewModel: MyPurchasesViewModel
    private var _binding: MyPurchasesFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter : MyPurchasesAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MyPurchasesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(MyPurchasesViewModel::class.java)
        binding.myPurchasesViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseOrders
        viewModel.balance
        binding.tlProductStatus.setTabTextColors(Color.GRAY,Color.RED)
        binding.tlProductStatus.setSelectedTabIndicatorColor(Color.RED)
        val ordersObserver = Observer<MyPurchases> { orders ->
            // Update the UI, in this case, a TextView.
            adapter = MyPurchasesAdapter(requireContext(), orders.userOrders)
            binding.recyclerView.adapter =  adapter
            adapter?.filter?.filter("قيد المراجعة")
        }
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.orders.observe(viewLifecycleOwner, ordersObserver)

        binding.tlProductStatus.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                adapter?.filter?.filter(tab!!.text)
                if (tab!!.text == "مكتمل"){
                    binding.tlProductStatus.setTabTextColors(Color.GRAY,Color.GREEN)
                    binding.tlProductStatus.setSelectedTabIndicatorColor(Color.GREEN)
                }else{
                    binding.tlProductStatus.setTabTextColors(Color.GRAY,Color.RED)
                    binding.tlProductStatus.setSelectedTabIndicatorColor(Color.RED)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MyPurchasesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}