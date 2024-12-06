package com.daniel.helloworld.mytest.mahasiswa.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R
import com.daniel.helloworld.mytest.mahasiswa.data.model.Product
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.adapter.RvMhsAdapter

class RvProductAdapter(
    private val context: Context,
) : RecyclerView.Adapter<RvMhsAdapter.Companion.MhsViewHolder>() {

    private var listItem = emptyList<Product>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RvMhsAdapter.Companion.MhsViewHolder {
        return RvMhsAdapter.Companion.MhsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_mahasiswa, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: RvMhsAdapter.Companion.MhsViewHolder, position: Int) {
        try {
            val currentItem = listItem[position]

            holder.tvName.text = currentItem.title
            holder.tvSchool.text = currentItem.description
//            holder.ivPhoto.setImageDrawable(currentItem.photo)
            /*if (currentItem.photo.isNotEmpty()) {
                val photoBitmap = TambahMahasiswaActivity().stringToBitmap(currentItem.photo)
                photoBitmap?.let { bitmap ->
                    holder.ivPhoto.setImageBitmap(bitmap)
                }
            }*/


//            holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setData(list: List<Product>) {
        this.listItem = list
        notifyDataSetChanged()
    }

    fun getList(): List<Product> {
        return listItem
    }
}