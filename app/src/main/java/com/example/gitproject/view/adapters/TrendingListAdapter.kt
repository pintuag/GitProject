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

    //Find out maximum memory available to application
    //1024 is used because LruCache constructor takes int in kilobytes
    val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

    // Use 1/4th of the available memory for this memory cache.
    // Use 1/4th of the available memory for this memory cache.
    val cacheSize = maxMemory / 4
    private val mLruCache = object : LruCache<String?, Bitmap>(cacheSize) {
        override fun sizeOf(
            key: String?,
            bitmap: Bitmap
        ): Int { // The cache size will be measured in kilobytes
            return bitmap.byteCount / 1024
        }
    }


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


    interface ItemClickListener {

        fun itemClick(trendingListModel: TrendingListModel)
    }

    inner class BitmapWorker : AsyncTask<String, Void, Bitmap>() {


        override fun doInBackground(vararg params: String?): Bitmap {
            val bitmap: Bitmap = getScaledImage(params[0])!!
            addBitmapToMemoryCache(params[0].toString(), bitmap)
            return bitmap
        }

    }

    private fun getScaledImage(imagePath: String?): Bitmap? {
        var bitmap: Bitmap? = null
        val imageUri = Uri.parse(imagePath)
        try {
            val options = BitmapFactory.Options()
            /*
*
             * inSampleSize flag if set to a value > 1,
             * requests the decoder to sub-sample the original image,
             * returning a smaller image to save memory.
             * This is a much faster operation as decoder just reads
             * every n-th pixel from given image, and thus
             * providing a smaller scaled image.
             * 'n' is the value set in inSampleSize
             * which would be a power of 2 which is downside
             * of this technique.

*/          options.inSampleSize = 4
            options.inScaled = true
            val inputStream: InputStream =
                context.getContentResolver().openInputStream(imageUri)!!
            bitmap = BitmapFactory.decodeStream(inputStream, null, options)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return bitmap
    }

    fun addBitmapToMemoryCache(key: String?, bitmap: Bitmap?) {
        if (getBitmapFromMemCache(key) == null) {
            mLruCache.put(key, bitmap)
        }
    }

    fun getBitmapFromMemCache(key: String?): Bitmap? {
        return mLruCache[key]
    }


}