package ui.smsHUB

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.R
import kotlinx.android.synthetic.user.sms_hub_fragment.view.*
import java.util.*
import kotlin.collections.ArrayList

class SMSHUBFragment : Fragment() {

    companion object {
        fun newInstance() = SMSHUBFragment()
    }

    private lateinit var viewModel: SMSHUBViewModel
    private lateinit var root:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        root = inflater.inflate(R.layout.sms_hub_fragment, container, false)
        ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            countries()
        ).also { adapter ->
            root.et_card_dialog_currency.setAdapter(adapter)
        }
        return root
    }

    private fun countries(): ArrayList<String> {
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        for (country in countries) {
            Log.w("Rabee", "country: $country")
        }
        Log.w("Rabee", "# countries found: " + countries.size)
    return countries
    }
}
