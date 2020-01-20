package com.example.gitproject.util

import android.graphics.Bitmap
import java.lang.ref.SoftReference
import java.util.*

class MemoryCache {
    private val cache =
        Collections.synchronizedMap(
            HashMap<String, SoftReference<Bitmap>?>()
        )

    operator fun get(id: String?): Bitmap? {
        if (!cache.containsKey(id)) return null
        val ref = cache[id]
        return ref!!.get()
    }

    fun put(id: String, bitmap: Bitmap) {
        cache[id] = SoftReference(bitmap)
    }

    fun clear() {
        cache.clear()
    }
}