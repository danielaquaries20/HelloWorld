package com.daniel.helloworld.pertemuan12

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.daniel.helloworld.R
import com.daniel.helloworld.pertemuan10.RvFriendAdapter
import com.daniel.helloworld.pertemuan12.data.DataProduct

class AdapterRVFriend(
    private val context: Context,
//    private val onItemClick: (position: Int, data: Friend) -> Unit
) : RecyclerView.Adapter<RvFriendAdapter.Companion.FriendViewHolder>() {

    private var listItem = emptyList<DataProduct>()
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

        holder.tvName.text = currentItem.title
        holder.tvSchool.text = currentItem.description
        /*val photoBtm = AddFriendActivity().stringToBitmap(currentItem.photo)
        photoBtm?.let {
            holder.ivPhoto.setImageBitmap(it)
        }*/

//        holder.itemView.setOnClickListener { onItemClick(position, currentItem) }
    }

    fun setData(list: List<DataProduct>) {
        this.listItem = list
        notifyDataSetChanged()
    }

}