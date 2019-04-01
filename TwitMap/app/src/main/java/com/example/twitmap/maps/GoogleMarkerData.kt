package com.example.twitmap.maps

import android.os.Parcelable
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class GoogleMarkerData(override var position: LatLng) : IGoogleMarkerData
{
    override var marker: Marker? = null
    override var description: String = "Default location"
    override var markerOptions: MarkerOptions =
        MarkerOptions().position(position).title(description)

    override var userName: String = ""
    override var hashTags: List<String> = listOf()

    override var radiusMeters: Float = 5000f

    override fun hashCode(): Int
    {
        //the hash should be better, but this one will work too
        return markerOptions.position.hashCode()
    }

    override fun calculateBounds(radiusM: Float): LatLngBounds? {
            TODO("not implemented")
        //here i'll take position, convert it to UTM, calc a rect, then convert its top left and bottom right
        //corners to LatLngBounds
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
        //i don't want to make 'this' parcelable, so let's move it to a simple class outside
        val serializable = ParcelableMarker()
        serializable.set(this)

        return serializable
    }

    companion object
    {
        val defaultLocation = LatLng(45.5017, -73.5673)//montreal
    }
}