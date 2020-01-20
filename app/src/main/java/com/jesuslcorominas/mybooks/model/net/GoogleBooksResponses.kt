package com.jesuslcorominas.mybooks.model.net

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class ListBooksDto(
    val kind: String = "",
    val totalItems: Int = -1,
    val items: List<BookItem> = ArrayList()
)

@Parcelize
data class BookItem(val id: String = "", val volumeInfo: VolumeInfo = VolumeInfo()) : Parcelable

@Parcelize
data class VolumeInfo(
    val title: String = "",
    val authors: List<String> = ArrayList(),
    val imageLinks: ImageLinks = ImageLinks(),
    val description: String = "",
    val publisher: String = "",
    val publisherDate: String = "",
    val previewLink: String = "",
    val infoLink: String = ""
) : Parcelable

@Parcelize
data class ImageLinks(val thumbnail: String = "") : Parcelable


