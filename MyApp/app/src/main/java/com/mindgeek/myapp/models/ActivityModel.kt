package com.mindgeek.myapp.models

import android.content.Intent
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable

class ActivityModel
{
    val key: String = "MainActivityModel_Key"//a readonly key for bundles
    var password: Int = 0
    var secureModeEnabled: Boolean = false

    fun loadFromBundle(bundle: Bundle?)
    {
        val parcel = bundle?.getParcelable<ParcelablePart>(key)
        password = parcel?.password ?: 0
        secureModeEnabled = parcel?.secureModeEnabled ?: false
    }

    fun saveToBundle(bundle: Bundle?)
    {
        val parcel = getParcelableObject()
        bundle?.putParcelable(key, parcel)
    }

    fun getSecurityState(): SecurityState
    {
        return when(secureModeEnabled)
        {
            true -> if(password < 1) SecurityState.SecurityOnPassNotSet else SecurityState.SecurityOnPassSet
            else -> SecurityState.SecurityOff
        }
    }

    fun getParcelableObject() : Parcelable
    {
        val p = ParcelablePart()
        p.password = password
        p.secureModeEnabled = secureModeEnabled
        return p
    }

    private class ParcelablePart() : Parcelable
    {
        var password: Int = 0
        var secureModeEnabled = false

        constructor(parcel: Parcel) : this()
        {
            password = parcel.readInt()
            secureModeEnabled = parcel.readInt() > 0
        }

        override fun writeToParcel(parcel: Parcel, flags: Int)
        {
            parcel.writeInt(password)
            parcel.writeInt(if(secureModeEnabled) 1 else 0)
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