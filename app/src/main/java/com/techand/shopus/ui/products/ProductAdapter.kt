package com.techand.shopus.ui.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.techand.shopus.data.models.Product
import com.techand.shopus.databinding.ImageLayoutBinding

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ImageViewHolder>() {

    lateinit var onClickListener: OnClickListener

    inner class ImageViewHolder(val binding: ImageLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private val DiffUtilCallBack = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.path == newItem.path && oldItem.path == newItem.path
        }

    }
    private val differ = AsyncListDiffer(this, DiffUtilCallBack)
    var images: List<Product>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ImageLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentProduct = images[position]

        holder.binding.apply {
            homeName.text = currentProduct.name
            homePrice.text = "$${currentProduct.price}"
            //  homeName.text = currentProduct.name
            homeImageview.load(currentProduct.path) {
                crossfade(true)
                crossfade(1000)
            }
//            addToCart.setOnClickListener {
//                onClickListener.onAddToCartClick(currentProduct)
//                editLayout.visibility = View.VISIBLE
//                addToCart.visibility = View.GONE
//            }


        }

        holder.itemView.setOnClickListener { mView ->
            val direction =
                ProductFragmentDirections.actionHomeFragmentToDetailFragment(currentProduct)
            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    interface OnClickListener {
        fun onClick(productData: Product)
        fun onDeleteClick(productData: Product){}
        fun onAddClick(productId: Int) {}
        fun onSubClick(productId: Int) {}
        fun onAddToCartClick(productData: Product) {}
    }

}

