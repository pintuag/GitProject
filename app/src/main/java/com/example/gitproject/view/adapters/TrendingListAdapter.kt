package com.example.gitproject.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.gitproject.R
import com.example.gitproject.models.dataModel.TrendingListModel
import kotlinx.android.synthetic.main.repo_recyclerview_item.view.*

class TrendingListAdapter : RecyclerView.Adapter<TrendingListAdapter.RecyclerViewHolder>() {

    lateinit var context: Context
    lateinit var itemClickListener: ItemClickListener
    var trendingList = emptyList<TrendingListModel>()


    fun setTrendingListData(
        context: Context,
        trendingList: List<TrendingListModel>,
        itemClickListener: ItemClickListener
    ) {
        this.context = context
        this.itemClickListener = itemClickListener
        this.trendingList = trendingList
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.repo_recyclerview_item, parent, false)
        return RecyclerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return trendingList.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {

        val trendingListModel = trendingList[position]
        holder.apply {

            username.text = trendingListModel.username
            repoName.text = trendingListModel.name

            Glide.with(context)
                .load(trendingListModel.avatar)
                .thumbnail(0.8f)
                .into(userProfile)


            itemView.setOnClickListener {
                itemClickListener.itemClick(position)
            }
        }

    }


    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val username = view.username
        val repoName = view.repoName
        val userProfile = view.userProfile

    }


    interface ItemClickListener {

        fun itemClick(position: Int)
    }

}