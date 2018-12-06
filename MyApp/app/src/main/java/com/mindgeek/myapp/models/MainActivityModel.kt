package com.mindgeek.myapp.models

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class MainActivityModel
{
    private val key = "MainActivityModel_Key"
    lateinit var password: String;

    fun getParcelableObject() : Parcelable
    {
        val p = ParcelablePart()
        p.password = password;
        return p
    }

    fun setParcelableObject(bundle: Bundle)
    {
        val parcel = bundle.getParcelable<ParcelablePart>(key)
        password = parcel.password
    }

    private class ParcelablePart() : Parcelable
    {
        lateinit var password: String

        constructor(parcel: Parcel) : this()
        {
            password = parcel.readString() ?: ""
        }

        override fun writeToParcel(parcel: Parcel, flags: Int)
        {
            parcel.writeString(password)
        }

        override fun describeContents(): Int
        {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ParcelablePart>
        {
            override fun createFromParcel(parcel: Parcel): ParcelablePart
            {
                return ParcelablePart(parcel)
            }

            override fun newArray(size: Int): Array<ParcelablePart?>
            {
                return arrayOfNulls(size)
            }
        }
    }
}