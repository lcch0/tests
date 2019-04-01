package com.example.twitmap.maps

import android.os.Parcel
import android.os.Parcelable
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.google.android.gms.maps.model.LatLng

class ParcelableMarker() : Parcelable
{
    private lateinit var userName: String
    private lateinit var description: String
    private lateinit var center: LatLng
    private var radius: Float = 0f
    private lateinit var hashTags: List<String>

    fun set(marker: IGoogleMarkerData)
    {
        userName = marker.userName
        description = marker.description
        center = marker.position
        radius = marker.radiusMeters
        hashTags = marker.hashTags
    }

    fun get(): IGoogleMarkerData
    {
        val marker = GoogleMarkerData(center)
        marker.userName = userName
        marker.description = description
        marker.radiusMeters = radius
        marker.hashTags = hashTags

        return marker
    }

    constructor(parcel: Parcel) : this()
    {
        userName = parcel.readString()
        description = parcel.readString()
        center = parcel.readParcelable(LatLng::class.java.classLoader)
        radius = parcel.readFloat()
        val list = ArrayList<String>()
        parcel.readStringList(list)
        hashTags = list
    }

    override fun writeToParcel(parcel: Parcel, flags: Int)
    {
        parcel.writeString(userName)
        parcel.writeString(description)
        parcel.writeParcelable(center, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeFloat(radius)
        parcel.writeStringArray(hashTags.toTypedArray())
    }

    override fun describeContents(): Int
    {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ParcelableMarker>
    {
        override fun createFromParcel(parcel: Parcel): ParcelableMarker
        {
            return ParcelableMarker(parcel)
        }

        override fun newArray(size: Int): Array<ParcelableMarker?>
        {
            return arrayOfNulls(size)
        }
    }
}