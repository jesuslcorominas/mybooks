package com.jesuslcorominas.mybooks.utils

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import timber.log.Timber

fun withDrawable(@DrawableRes id: Int, @ColorRes tint: Int? = null, tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) =
    object : TypeSafeMatcher<View>() {

        override fun describeTo(description: Description) {
            description.appendText("ImageView with drawable same as drawable with id $id")
            tint?.let { description.appendText(", tint color id: $tint, mode: $tintMode") }
        }

        override fun matchesSafely(view: View): Boolean {
            val context = view.context
            val tintColor = tint?.toColor(context)
            val expectedBitmap = context.getDrawable(id)?.tinted(tintColor, tintMode)?.toBitmap()

            if (view is ImageView) {
                return view.drawable.toBitmap().sameAs(expectedBitmap)
            } else {
                Timber.e("No es un ImageView")
                return false
            }
        }
    }

private fun Drawable.tinted(@ColorInt tintColor: Int? = null, tintMode: PorterDuff.Mode = PorterDuff.Mode.SRC_IN) =
    apply {
        setTintList(tintColor?.toColorStateList())
        setTintMode(tintMode)
    }

private fun Int.toColorStateList() = ColorStateList.valueOf(this)


private fun Int.toColor(context: Context) = ContextCompat.getColor(context, this)
