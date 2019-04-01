package com.example.twitmap.interfaces

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

interface IGoogleMarkerData
{
    var position: LatLng
    var description: String
    var markerOptions: MarkerOptions
    var marker: Marker?
    var radiusMeters: Float
    var userName: String
    var hashTags: List<String>

    fun calculateBounds(radiusM: Float): LatLngBounds?
    fun getAsParcelable(): Parcelable
}