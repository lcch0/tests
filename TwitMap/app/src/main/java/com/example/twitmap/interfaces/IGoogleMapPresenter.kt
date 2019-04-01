package com.example.twitmap.interfaces

import com.google.android.gms.maps.GoogleMap

interface IGoogleMapPresenter
{
    var googleMap: GoogleMap?
    //imagine, we will use different sources for our markers
    val geoMarkerRepositories: List<IGoogleMarkerRepository>
    var context: IViewContext

    fun init(marker: IGoogleMarkerData)
    fun initAsync(marker: IGoogleMarkerData)
    fun moveToSquare(marker: IGoogleMarkerData)
    fun addMarker(marker: IGoogleMarkerData)
    fun updateMarker(marker: IGoogleMarkerData)
}