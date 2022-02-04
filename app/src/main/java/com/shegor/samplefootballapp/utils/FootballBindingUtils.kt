package com.shegor.samplefootballapp.utils

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.shegor.samplefootballapp.R

@BindingAdapter("leagueName")
fun setLeagueName(textView: TextView, leagueName: String?) {
    leagueName?.let {
        textView.text = leagueName
    }
}

@BindingAdapter("countryName")
fun setCountryName(textView: TextView, countryName: String?) {
    countryName?.let {
        textView.text = countryName
    }
}

@BindingAdapter("date")
fun setFormattedDate(textView: TextView, date: String?) {
    date?.let {
        textView.text = DateUtils.apiDateStringToUiDateString(date)
    }
}

@BindingAdapter(value = ["hometeamScore", "awayteamScore"])
fun setFormattedScore(textView: TextView, hometeamScore: String?, awayteamScore: String?) {
    if (hometeamScore != null && awayteamScore != null)
        textView.text = "$hometeamScore : $awayteamScore"

}


@BindingAdapter("imageUrl")
fun setImage(imageView: ImageView, url: String?) {

    url?.let {

        val imgUri = it.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .error(R.drawable.image_placeholder)
            .placeholder(ColorDrawable(Color.GRAY))
            .into(imageView)
        return@setImage
    }
    imageView.setImageResource(R.drawable.image_placeholder)
}