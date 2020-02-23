package com.jesuslcorominas.mybooks.model.database

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index(value = ["googleId"], unique = true)))
data class Book(
    @PrimaryKey(autoGenerate = true)
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