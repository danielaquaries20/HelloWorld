package com.daniel.helloworld.mytest.mahasiswa.ui.detail.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R
import com.daniel.helloworld.mytest.mahasiswa.ui.detail.model.Mhs

class RvMhsAdapter(
    private val context: Context,
    private val onItemClick: (position: Int, data: Mhs) -> Unit
) : RecyclerView.Adapter<RvMhsAdapter.Companion.MhsViewHolder>() {

    private var listItem = emptyList<Mhs>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MhsViewHolder {
        return MhsViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_mahasiswa, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: MhsViewHolder, position: Int) {
        try {
            val currentItem = listItem[position]
            Log.d("Rv_Check", "Chexk 1: $currentItem")

            holder.tvName.text = currentItem.name
            holder.tvSchool.text = currentItem.school
            holder.ivPhoto.setImageDrawable(currentItem.photo)

            holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
        } catch (e: Exception) {
            Log.d("Rv_Check", "Chexk 2")
            e.printStackTrace()
        }

    }

    fun setData(list: List<Mhs>) {
        this.listItem = list
        Log.d("listData 2", "${listItem}")
        notifyDataSetChanged()
    }

    fun getList(): List<Mhs> {
        return listItem
    }


    companion object {

        class MhsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val ivPhoto: ImageView = view.findViewById(R.id.iv_photo)
            val tvName: TextView = view.findViewById(R.id.tv_name)
            val tvSchool: TextView = view.findViewById(R.id.tv_school)

        }

    }
}