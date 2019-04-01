package com.example.twitmap.maps

import android.os.Parcelable
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMarkerData(override var center: LatLng) : IGoogleMarkerData
{
    override var marker: Marker? = null
    override var description: String = "Default location"
    override var markerOptions: MarkerOptions =
        MarkerOptions().position(center).title(description)

    override var userName: String = ""
    override var hashTags: List<String> = listOf()

    override var radiusMeters: Float = 5000f

    override fun hashCode(): Int
    {
        //the hash should be better, but this one will work too
        return markerOptions.position.hashCode()
    }

    override fun calculateBounds(radiusM: Float): LatLngBounds? {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GoogleMarkerData

        return hashCode() == other.hashCode()
    }

    override fun getAsParcelable(): Parcelable
    {
        val serializable = ParcelableMarker()
        serializable.set(this)

        return serializable
    }

    companion object
    {
        val defaultLocation = LatLng(45.5017, -73.5673)
    }
}