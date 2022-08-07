package com.techand.shopus.ui.products

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.techand.shopus.R
import com.techand.shopus.data.network.Resource
import com.techand.shopus.databinding.FragmentHomeBinding
import com.techand.shopus.ui.HomeViewModel
import com.techand.shopus.util.handleApiError
import com.techand.shopus.util.showBack
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var imageAdapter: ProductAdapter
    private var cartItem: String? = "0"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().showBack(false, getString(R.string.app_name))
        setUpRv()
        viewModel.getCartTotal()
    }


    private fun setUpRv() {
        imageAdapter = ProductAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(requireActivity(), 2)
            setHasFixedSize(true)
            adapter = imageAdapter
        }
        viewModel.products.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        imageAdapter.images = it.data
                    }
                }
                is Resource.Error -> handleApiError(it)
                else -> {}
            }
        }
        viewModel.cartTotal.observe(viewLifecycleOwner) {
            cartItem = it?.toString()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.getItem(1).title =cartItem
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        super.onCreateOptionsMenu(menu,inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id. item_total -> {
                val direction =
                    ProductFragmentDirections.actionHomeFragmentToCartFragment()
                findNavController().navigate(direction)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}