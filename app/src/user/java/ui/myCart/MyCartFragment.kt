package ui.myCart

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.R

class MyCartFragment : Fragment() {

    companion object {
        fun newInstance() = MyCartFragment()
    }

    private lateinit var viewModel: MyCartViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.my_cart_fragment, container, false)
    }
}