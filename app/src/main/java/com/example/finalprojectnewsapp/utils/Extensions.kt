package com.example.finalprojectnewsapp.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


@SuppressLint("SimpleDateFormat")
fun String.toDateConverter(timer:String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val outputFormat = SimpleDateFormat("yyyy-MM-dd")
    val date = inputFormat.parse(timer)
    return outputFormat.format(date)
}