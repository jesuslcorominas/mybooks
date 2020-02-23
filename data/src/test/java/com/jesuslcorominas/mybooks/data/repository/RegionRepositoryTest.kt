package com.jesuslcorominas.mybooks.data.repository

import com.jesuslcorominas.mybooks.data.repository.PermissionChecker.Permission.COARSE_LOCATION
import com.jesuslcorominas.mybooks.data.source.LocationDatasource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDatasource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default when coarse permission not granted`() {
        runBlocking {

            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findLastRegion()

            assertEquals(RegionRepository.DEFAULT_REGION, region)
        }
    }

    @Test
    fun `returns region from location data source when permission granted`() {
        runBlocking {

            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLastRegion()).thenReturn("ES")

            val region = regionRepository.findLastRegion()

            assertEquals("ES", region)
        }
    }
}