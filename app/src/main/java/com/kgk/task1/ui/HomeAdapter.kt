package com.kgk.task1.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kgk.task1.R
import com.kgk.task1.databinding.RowHomeBinding
import kotlinx.android.synthetic.main.row_home.view.*

class HomeAdapter(private val context: Context, private val dataList: List<ListData>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: RowHomeBinding) : RecyclerView.ViewHolder(itemView.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.row_home, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        Glide.with(context).load(data.avatar).placeholder(R.mipmap.ic_launcher_round)
            .into(holder.itemView.image)
        holder.itemView.name.text = data.name
        holder.itemView.author.text = data.author
        holder.itemView.description.text = data.description
        holder.itemView.lang.text = data.language
        holder.itemView.star.text = data.stars
        holder.itemView.fork.text = data.forks
        if (data.showHide) {
            holder.itemView.showHide.visibility = View.VISIBLE
        } else {
            holder.itemView.showHide.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            data.showHide = !data.showHide
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}