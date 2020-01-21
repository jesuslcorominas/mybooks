package com.jesuslcorominas.mybooks.model

import com.jesuslcorominas.mybooks.domain.Book
import com.jesuslcorominas.mybooks.model.database.Book as DatabaseBook
import com.jesuslcorominas.mybooks.model.server.Book as RemoteBook

fun Book.toDatabaseBook(): DatabaseBook {
    return with(this) {
        com.jesuslcorominas.mybooks.model.database.Book(
            id,
            googleId,
            title,
            thumbnail,
            description,
            infoLink,
            favourite,
            collected,
            read
        )
    }
}

fun DatabaseBook.toBook(): Book {
    return with(this) {
        Book(
            id,
            googleId,
            title,
            thumbnail,
            description,
            infoLink,
            favourite,
            collected,
            read
        )
    }
}

fun RemoteBook.toBook(): Book {
    return with(this) {
        Book(
            0,
            id,
            volumeInfo.title,
            volumeInfo.imageLinks.thumbnail,
            volumeInfo.description,
            volumeInfo.infoLink,
            false,
            false,
            false
        )
    }
}