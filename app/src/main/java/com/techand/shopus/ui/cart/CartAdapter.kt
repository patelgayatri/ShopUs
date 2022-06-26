package com.techand.shopus.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.techand.shopus.data.local.MyCart
import com.techand.shopus.databinding.RawCartBinding

class CartAdapter : RecyclerView.Adapter<CartAdapter.ImageViewHolder>() {

    lateinit var onClickListener: OnClickListener

    inner class ImageViewHolder(val binding: RawCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    private val DiffUtilCallBack = object : DiffUtil.ItemCallback<MyCart>() {
        override fun areItemsTheSame(oldItem: MyCart, newItem: MyCart): Boolean {
            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: MyCart, newItem: MyCart): Boolean {
            return oldItem.quantity == newItem.quantity && oldItem.id == newItem.id
        }

    }
    private val differ = AsyncListDiffer(this, DiffUtilCallBack)
    var cartItems: List<MyCart>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            RawCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentProduct = cartItems[position]

        holder.binding.apply {
            cartName.text = currentProduct.name
            cartQuantity.text = currentProduct.quantity.toString()
            cartPrice.text = "$${currentProduct.price}/kg"
            cartTotalPrice.text ="$${currentProduct.price * currentProduct.quantity}"
            //  homeName.text = currentProduct.name
            cartImageview.load(currentProduct.path) {
                crossfade(true)
                crossfade(1000)
            }
            addBtn.setOnClickListener {
                onClickListener.onAddClick(currentProduct.id)
            }
            deleteBtn.setOnClickListener {
                onClickListener.onDeleteClick(currentProduct.id)
            }
            subBtn.setOnClickListener {
                onClickListener.onSubClick(currentProduct.id, currentProduct.quantity)
            }
        }

    }

    override fun getItemCount(): Int {
        return cartItems.size
    }

    interface OnClickListener {
        fun onAddClick(productId: Int)
        fun onDeleteClick(productId: Int)
        fun onSubClick(productId: Int, quantity: Int)
    }

}

