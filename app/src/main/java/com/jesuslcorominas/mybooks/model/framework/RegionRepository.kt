package com.jesuslcorominas.mybooks.model.framework

//class RegionRepository(application: Application) {
//
//    companion object {
//        private const val DEFAULT_REGION = "ES"
//    }
//
//    private val locationDataSource = PlayServicesLocationDataSource(application)
//    private val coarsePermissionChecker = AndroidPermissionChecker(application, ACCESS_COARSE_LOCATION)
//    private val geocoder = Geocoder(application)
//
//    suspend fun findLastRegion(): String = findLastLocation().toRegion()
//
//    private suspend fun findLastLocation(): Location? {
//        return if (coarsePermissionChecker.check()) locationDataSource.findLastLocation() else null
//    }
//
//    private fun Location?.toRegion(): String {
//        val addresses = this?.let {
//            try {
//                geocoder.getFromLocation(latitude, longitude, 1)
//            } catch (e: IOException) {
//                null
//            }
//
//        }
//        return addresses?.firstOrNull()?.countryCode ?: DEFAULT_REGION
//    }
//}