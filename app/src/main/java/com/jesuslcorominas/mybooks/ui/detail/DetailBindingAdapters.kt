package com.jesuslcorominas.mybooks.ui.detail

import androidx.databinding.BindingAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jesuslcorominas.mybooks.R

@BindingAdapter("favourite")
fun FloatingActionButton.setFavourite(favourite: Boolean?) {
    val icon = if (favourite == true) R.drawable.ic_favourite_on else R.drawable.ic_favourite_off
    setImageDrawable(context.getDrawable(icon))
}

@BindingAdapter("collected")
fun FloatingActionButton.setCollected(collected: Boolean?) {
    val icon = if (collected == true) R.drawable.ic_collected_on else R.drawable.ic_collected_off
    setImageDrawable(context.getDrawable(icon))
}

@BindingAdapter("read")
fun FloatingActionButton.setRead(read: Boolean?) {
    val icon = if (read == true) R.drawable.ic_read_on else R.drawable.ic_read_off
    setImageDrawable(context.getDrawable(icon))
}