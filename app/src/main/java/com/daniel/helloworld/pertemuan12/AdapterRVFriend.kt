package com.daniel.helloworld.pertemuan12

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R
import com.daniel.helloworld.pertemuan10.RvFriendAdapter
import com.daniel.helloworld.pertemuan12.database.Friend

class AdapterRVFriend(
    private val context: Context,
    private val onItemClick: (position: Int, data: Friend) -> Unit
) : RecyclerView.Adapter<RvFriendAdapter.Companion.FriendViewHolder>() {

    private var listItem = emptyList<Friend>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RvFriendAdapter.Companion.FriendViewHolder {
        return RvFriendAdapter.Companion.FriendViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_friend, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(
        holder: RvFriendAdapter.Companion.FriendViewHolder,
        position: Int
    ) {
        val currentItem = listItem[position]

        holder.tvName.text = currentItem.name
        holder.tvSchool.text = currentItem.school

        holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
    }

    fun setData(list: List<Friend>) {
        this.listItem = list
        notifyDataSetChanged()
    }

}