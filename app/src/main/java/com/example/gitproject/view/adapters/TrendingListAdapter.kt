package com.example.gitproject.view.adapters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.AsyncTask
import android.util.LruCache
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitproject.R
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.util.ImageLoader
import kotlinx.android.synthetic.main.repo_recyclerview_item.view.*
import java.io.FileNotFoundException
import java.io.InputStream

class TrendingListAdapter : RecyclerView.Adapter<TrendingListAdapter.RecyclerViewHolder>() {

    lateinit var context: Context
    lateinit var itemClickListener: ItemClickListener
    lateinit var imageLoader: ImageLoader
    var trendingList = emptyList<TrendingListModel>()

    fun setTrendingListData(
        context: Context,
        trendingList: List<TrendingListModel>,
        itemClickListener: ItemClickListener
    ) {
        this.context = context
        this.itemClickListener = itemClickListener
        this.trendingList = trendingList
        imageLoader = ImageLoader(context)
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

            imageLoader.DisplayImage(trendingListModel.avatar, R.drawable.placeholder, userProfile)

            itemView.setOnClickListener {
                itemClickListener.itemClick(trendingListModel)
            }
        }

    }


    inner class RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val username = view.username
        val repoName = view.repoName
        val userProfile = view.userProfile

    }


    /*
    * Item click listener
    * */
    interface ItemClickListener {

        fun itemClick(trendingListModel: TrendingListModel)
    }


}