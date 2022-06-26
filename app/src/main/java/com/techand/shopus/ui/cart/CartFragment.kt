package com.techand.shopus.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.techand.shopus.R
import com.techand.shopus.databinding.FragmentCartBinding
import com.techand.shopus.ui.HomeViewModel
import com.techand.shopus.util.showBack
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CartFragment : Fragment(R.layout.fragment_cart) {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var imageAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showBack(true,"My Cart")
        setUpRv()
        setClick()
    }

    private fun setUpRv() {
        imageAdapter = CartAdapter()
        viewModel.getTotalMoney()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            setHasFixedSize(true)
            adapter = imageAdapter
        }
        viewModel.cartLiveData.observe(viewLifecycleOwner) {
            imageAdapter.cartItems = it
        }
        viewModel.totalAmount.observe(viewLifecycleOwner) {
            val totalText = it?.toString() ?: "0"
            binding.totalMoney.text = "$$totalText"
        }
    }

    private fun setClick() {
        imageAdapter.onClickListener = object : CartAdapter.OnClickListener {
            override fun onAddClick(productId: Int) {
                viewModel.addToCart(productId)
            }

            override fun onDeleteClick(productId: Int) {
                viewModel.deleteProduct(productId)
            }

            override fun onSubClick(productId: Int, quantity: Int) {
                if (quantity != 1)
                    viewModel.subCart(productId)
                else
                    viewModel.deleteProduct(productId)
            }
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}