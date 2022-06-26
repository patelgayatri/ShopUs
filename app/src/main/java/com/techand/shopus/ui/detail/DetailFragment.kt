package com.techand.shopus.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.techand.shopus.R
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.data.models.Product
import com.techand.shopus.databinding.FragmentDetailBinding
import com.techand.shopus.ui.HomeViewModel
import com.techand.shopus.util.showBack
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var item: Product
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item = args.item
        requireActivity().showBack(true,item.name)
        showDetail()
        cartAdd()
    }


    private fun showDetail() {
        viewModel.getQuantity(item.id)
        binding.apply {
            detailImageView.load(item.path) {
                crossfade(true)
                crossfade(1000)
            }
            detailName.text = item.name
            detailPrice.text = "$${item.price}"
        }
        viewModel.quantity.observe(viewLifecycleOwner, {
            it?.let { binding.quantity.text = it.toString() }
        })
    }

    private fun cartAdd() {
        binding.addBtn.setOnClickListener {
            if (binding.quantity.text.toString() == "0")
                viewModel.insetToCart(MyCart(item.id, item.name, item.path, item.price, 1))
            else
                viewModel.addToCart(item.id)
        }
        binding.subBtn.setOnClickListener {
            val qua = binding.quantity.text.toString()
            if (qua == "1") {
                viewModel.deleteProduct(item.id)
                binding.quantity.text = "0"
            } else if (qua == "0") { }
            else
                viewModel.subCart(item.id)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    

}