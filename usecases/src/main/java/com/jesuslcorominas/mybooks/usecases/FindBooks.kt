package com.jesuslcorominas.mybooks.usecases

import com.jesuslcorominas.mybooks.data.repository.BooksRepository
import com.jesuslcorominas.mybooks.data.repository.RegionRepository

class FindBooks(val booksRepository: BooksRepository, val regionRepository: RegionRepository) {
    suspend fun invoke(query: String) =
        booksRepository.findBooks(query, regionRepository.findLastRegion())
}