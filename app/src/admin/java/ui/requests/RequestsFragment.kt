package ui.requests

import adapter.AccountsPagerAdapter
import adapter.RefundPagerAdapter
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.example.syriamarket.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.admin.requests_fragment.view.*

class RequestsFragment : Fragment() {

    companion object {
        fun newInstance() = RequestsFragment()
    }

    private lateinit var viewModel: RequestsViewModel
    private lateinit var root: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        root = inflater.inflate(R.layout.requests_fragment, container, false)
        val viewPager = root.vp_requests as ViewPager
        viewPager.adapter = RefundPagerAdapter(childFragmentManager)
        val tabLayout = root.tl_requests as TabLayout
        tabLayout.setupWithViewPager(viewPager)
        return root
    }
}