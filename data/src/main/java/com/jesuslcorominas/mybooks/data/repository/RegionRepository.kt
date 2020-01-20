package com.jesuslcorominas.mybooks.data.repository

import com.jesuslcorominas.mybooks.data.repository.PermissionChecker.Permission.COARSE_LOCATION
import com.jesuslcorominas.mybooks.data.source.LocationDatasource

class RegionRepository(
    private val locationDataSource: LocationDatasource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_REGION = "ES"
    }

    suspend fun findLastRegion(): String {
        return if (permissionChecker.check(COARSE_LOCATION)) {
            locationDataSource.findLastRegion() ?: DEFAULT_REGION
        } else {
            DEFAULT_REGION
        }
    }
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}