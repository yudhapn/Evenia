package com.gemastik.evenia.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gemastik.evenia.model.Vacancy
import com.pertamina.spbucare.network.NetworkState

@BindingAdapter("vacanciesData")
fun RecyclerView.bindRvVacancy(data: List<Vacancy>?) {
    val adapter = adapter as VacancyAdapter
    adapter.submitList(data)
}

@BindingAdapter("vacanciesProviderData")
fun RecyclerView.bindRvVacancyProvider(data: List<Vacancy>?) {
    val adapter = adapter as ProviderVacancyAdapter
    adapter.submitList(data)
}

@BindingAdapter("card_foreground")
fun CardView.setForeground(image: Int) {
    foreground = context.getDrawable(image)
}


@BindingAdapter("layout_background")
fun ConstraintLayout.setBackground(image: Int) {
    background = context.getDrawable(image)
}

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String) {
    setColorFilter(Color.argb(165, 0, 0, 0))
    Glide.with(context).load(url).into(this)
}

@BindingAdapter("setCamelCase")
fun TextView.setCamelCase(value: String?) {
    text = value?.trim()?.capitalizeWords()
}

@BindingAdapter("progressVisibility")
fun View.progressVisibility(networkState: NetworkState?) {
    visibility = when (networkState) {
        NetworkState.SUCCESS -> View.GONE
        NetworkState.RUNNING -> View.VISIBLE
        else -> View.GONE
    }
}

private fun String.capitalizeWords(): String = split(" ").joinToString(" ") { it.capitalize() }
