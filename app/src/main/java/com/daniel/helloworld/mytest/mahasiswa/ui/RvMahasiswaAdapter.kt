package com.daniel.helloworld.mytest.mahasiswa.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R
import com.daniel.helloworld.mytest.mahasiswa.data.Mahasiswa
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.adapter.RvMhsAdapter

class RvMahasiswaAdapter(
    private val context: Context,
    private val onItemClick: (position: Int, data: Mahasiswa) -> Unit
) : RecyclerView.Adapter<RvMhsAdapter.Companion.MhsViewHolder>() {

    private var listItem = emptyList<Mahasiswa>()

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

            holder.tvName.text = currentItem.nama
            holder.tvSchool.text = currentItem.nim
//            holder.ivPhoto.setImageDrawable(currentItem.photo)
            if (currentItem.photo.isNotEmpty()) {
                val photoBitmap = TambahMahasiswaActivity().stringToBitmap(currentItem.photo)
                photoBitmap?.let { bitmap ->
                    holder.ivPhoto.setImageBitmap(bitmap)
                }
            }


            holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setData(list: List<Mahasiswa>) {
        this.listItem = list
        notifyDataSetChanged()
    }

    fun getList(): List<Mahasiswa> {
        return listItem
    }

}