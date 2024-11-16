package com.daniel.helloworld.pertemuan10

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R

class RvFriendAdapter(
    private val context: Context,
    private val onItemClick : (position: Int, data: FriendData) -> Unit
) : RecyclerView.Adapter<RvFriendAdapter.Companion.FriendViewHolder>() {

    private var listItem = emptyList<FriendData>()

    companion object {
        class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvName : TextView = view.findViewById(R.id.tv_name)
            val tvSchool : TextView = view.findViewById(R.id.tv_school)
            val ivPhoto : ImageView = view.findViewById(R.id.iv_photo)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        return FriendViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val currentItem = listItem[position]

        holder.tvName.text = currentItem.name
        holder.tvSchool.text = currentItem.school
        holder.ivPhoto.setImageDrawable(currentItem.photo)

        holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
    }

    fun setData(list: List<FriendData>) {
        this.listItem = list
        notifyDataSetChanged()
    }

}