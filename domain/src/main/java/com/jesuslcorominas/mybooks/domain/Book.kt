package com.jesuslcorominas.mybooks.domain

data class Book(
    val id: Int,
    val googleId: String = "",
    val title: String = "",
    val thumbnail: String = "",
    val description: String = "",
    val infoLink: String = "",
    val favourite: Boolean = false,
    val collected: Boolean = false,
    val read: Boolean = false
)