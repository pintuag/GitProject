package com.example.gitproject.util

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import com.example.gitproject.R
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ImageLoader(context: Context?) {
    var memoryCache =
        MemoryCache()
    var fileCache: FileCache = FileCache(context!!)
    private val imageViews =
        Collections.synchronizedMap(WeakHashMap<ImageView, String>())
    var executorService: ExecutorService = Executors.newFixedThreadPool(5)
    var stub_id = R.drawable.placeholder
    fun DisplayImage(
        url: String,
        loader: Int,
        imageView: ImageView
    ) {
        stub_id = loader
        imageViews[imageView] = url
        val bitmap = memoryCache[url]
        if (bitmap != null) imageView.setImageBitmap(bitmap) else {
            queuePhoto(url, imageView)
            imageView.setImageResource(loader)
        }
    }

    private fun queuePhoto(url: String, imageView: ImageView) {
        val p = PhotoToLoad(url, imageView)
        hello(p)
    }

    private fun getBitmap(url: String): Bitmap? {
        val f = fileCache.getFile(url)
        //from SD cache
        val b = decodeFile(f)
        return b
            ?: try {
                var bitmap: Bitmap? = null
                val imageUrl = URL(url)
                val conn =
                    imageUrl.openConnection() as HttpURLConnection
                conn.connectTimeout = 30000
                conn.readTimeout = 30000
                conn.instanceFollowRedirects = true
                val `is` = conn.inputStream
                val os: OutputStream = FileOutputStream(f)
                Utils.copyStream(`is`, os)
                os.close()
                bitmap = decodeFile(f)
                bitmap
            } catch (ex: Exception) {
                ex.printStackTrace()
                null
            }
        //from web
    }

    //decodes image and scales it to reduce memory consumption
    private fun decodeFile(f: File): Bitmap? {
        try { //decode image size
            val o = BitmapFactory.Options()
            o.inJustDecodeBounds = true
            BitmapFactory.decodeStream(FileInputStream(f), null, o)
            //Find the correct scale value. It should be the power of 2.
            val REQUIRED_SIZE = 70
            var width_tmp = o.outWidth
            var height_tmp = o.outHeight
            var scale = 1
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE) break
                width_tmp /= 2
                height_tmp /= 2
                scale *= 2
            }
            //decode with inSampleSize
            val o2 = BitmapFactory.Options()
            o2.inSampleSize = scale
            return BitmapFactory.decodeStream(FileInputStream(f), null, o2)
        } catch (e: FileNotFoundException) {
        }
        return null
    }

    //Task for the queue
    inner class PhotoToLoad(var url: String, var imageView: ImageView)

    @SuppressLint("CheckResult")
    fun hello(photoToLoad: PhotoToLoad) {
        Log.e("HelloCalled", "In")
        Observable.just(photoToLoad)
            .map {
                Log.e("INMApObserver", "In")
                if (imageViewReused(photoToLoad)) return@map null
                val bmp = getBitmap(photoToLoad.url)
                memoryCache.put(photoToLoad.url, bmp!!)
                return@map bmp
            }.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if (imageViewReused(photoToLoad)) return@subscribe
                    if (it != null) {
                        Log.e("InSubscribeObserver", "IFFFF")
                        photoToLoad.imageView.setImageBitmap(it)
                    } else {
                        Log.e("InSubscribeObserver", "ELSE")
                        photoToLoad.imageView.setImageResource(
                            stub_id
                        )
                    }
                }, {
                    Log.e("InErrorStack", "ELSE")
                    it.printStackTrace()
                }, {}
            )
    }


    fun imageViewReused(photoToLoad: PhotoToLoad): Boolean {
        val tag = imageViews[photoToLoad.imageView]
        return tag == null || tag != photoToLoad.url
    }


    fun clearCache() {
        memoryCache.clear()
        fileCache.clear()
    }

}