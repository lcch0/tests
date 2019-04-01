package com.example.twitmap.interfaces

import android.content.Context

interface IViewContext
{
    fun getContext(): Context
    fun showMarkerInfo(markerData: IGoogleMarkerData)
}