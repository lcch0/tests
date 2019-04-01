package com.example.twitmap.interfaces

import com.google.android.gms.maps.GoogleMap

interface IGoogleMapPresenter
{
    var googleMap: GoogleMap?
    val geoMarkerRepositories: List<IGoogleMarkerRepository>
    var context: IViewContext

    fun init(marker: IGoogleMarkerData)
    fun moveToSquare(marker: IGoogleMarkerData)
    fun addMarker(marker: IGoogleMarkerData)
    fun updateMarker(marker: IGoogleMarkerData)
    fun initAsync(marker: IGoogleMarkerData)
}