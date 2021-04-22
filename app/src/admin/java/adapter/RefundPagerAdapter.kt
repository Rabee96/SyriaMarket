package adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ui.accounts.allAccounts.AllAccountsFragment
import ui.accounts.createAccount.CreateAccountFragment
import ui.accounts.createCoupon.CreateCouponFragment
import ui.refund.RefundFragment

class RefundPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val title = listOf("في الإنتظار","المكتمل","المرجع")

    override fun getCount(): Int {
        return title.size
    }

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> RefundFragment(0)
            1 -> RefundFragment(1)
            2 -> RefundFragment(2)
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return title[position]
    }
}