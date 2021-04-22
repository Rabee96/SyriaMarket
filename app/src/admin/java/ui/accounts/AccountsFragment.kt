package ui.accounts

import adapter.AccountsPagerAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.syriamarket.R
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.admin.accounts_fragment.view.*


class AccountsFragment : Fragment() {

    companion object {
        fun newInstance() = AccountsFragment()
    }

    private lateinit var viewModel: AccountsViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.accounts_fragment, container, false)
        val viewPager = root.vp_accounts as ViewPager
        viewPager.adapter = AccountsPagerAdapter(childFragmentManager)
        val tabLayout = root.tl_accounts as TabLayout
        tabLayout.setupWithViewPager(viewPager)
        return root
    }
}