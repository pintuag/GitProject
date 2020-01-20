package com.example.gitproject.util

import java.io.InputStream
import java.io.OutputStream

object Utils {
    fun CopyStream(inputStream: InputStream, os: OutputStream) {
        val bufferSize = 1024
        try {
            val bytes = ByteArray(bufferSize)
            while (true) {
                val count = inputStream.read(bytes, 0, bufferSize)
                if (count == -1) break
                os.write(bytes, 0, count)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}