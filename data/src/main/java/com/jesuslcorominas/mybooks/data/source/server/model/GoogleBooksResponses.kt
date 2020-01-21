package com.jesuslcorominas.mybooks.data.source.server.model

data class ListBooksDto(
    val kind: String = "",
    val totalItems: Int = -1,
    val items: List<Book> = ArrayList()
)

data class Book(val id: String = "", val volumeInfo: VolumeInfo = VolumeInfo())

data class VolumeInfo(
    val title: String = "",
    val authors: List<String> = ArrayList(),
    val imageLinks: ImageLinks = ImageLinks(),
    val description: String = "",
    val publisher: String = "",
    val publisherDate: String = "",
    val previewLink: String = "",
    val infoLink: String = ""
)

data class ImageLinks(val thumbnail: String = "")