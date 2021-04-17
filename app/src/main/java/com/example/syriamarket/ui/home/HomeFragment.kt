package com.example.syriamarket.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.syriamarket.adapter.HomeViewPagerAdapter
import com.example.syriamarket.databinding.HomeFragmentBinding
import com.google.android.material.tabs.TabLayout

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.userBalance()
        viewModel.responseCats
        viewPager = binding.pager
        tabLayout = binding.tabDots
        tabLayout.setupWithViewPager(viewPager,true)
        val imgs = ArrayList<String>()
        imgs.add("https://www.whoa.in/download/garena-free-fire-mobile-royale-battle-game-hd-wallpapers--2--garena-free-fire")
        imgs.add("https://www.whoa.in/download/garena-free-fire-mobile-royale-battle-game-hd-wallpapers--3-garena-free-fire")
        imgs.add("http://freefiremobile-a.akamaihd.net/ffwebsite/images/wallpaper/img59.jpg")
        viewPager.adapter = HomeViewPagerAdapter(requireContext(),imgs)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}