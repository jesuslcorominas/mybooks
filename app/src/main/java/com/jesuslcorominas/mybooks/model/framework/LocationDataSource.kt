package com.jesuslcorominas.mybooks.model.framework

//class PlayServicesLocationDataSources(application: Application) : LocationDatasource {
//    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
//
//    override suspend fun findLastRegion(): String? {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    @SuppressLint("MissingPermission")
//    override suspend fun findLastLocation(): Location? =
//        suspendCancellableCoroutine { continuation ->
//            fusedLocationClient.lastLocation
//                .addOnCompleteListener {
//                    continuation.resume(it.result.toRegion())
//                }
//        }
//
//    private fun Location?.toRegion(): String? {
//        val addresses = this?.let {
//            geocoder.getFromLocation(latitude, longitude, 1)
//        }
//        return addresses?.firstOrNull()?.countryCode
//    }
//}
