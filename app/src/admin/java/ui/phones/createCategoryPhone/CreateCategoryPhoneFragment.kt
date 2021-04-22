package ui.phones.createCategoryPhone

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.R

class CreateCategoryPhoneFragment : Fragment() {

    companion object {
        fun newInstance() = CreateCategoryPhoneFragment()
    }

    private lateinit var viewModel: CreateCategoryPhoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_category_phone_fragment, container, false)
    }
}