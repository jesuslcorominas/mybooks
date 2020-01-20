package com.jesuslcorominas.mybooks.data.source

interface LocationDatasource {
    suspend fun findLastRegion(): String?
}
