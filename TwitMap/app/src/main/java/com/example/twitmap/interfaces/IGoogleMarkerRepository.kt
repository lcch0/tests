package com.example.twitmap.interfaces

interface IGoogleMarkerRepository
{
    val markerList: MutableMap<Int, IGoogleMarkerData>
    var onAdded: ((Map<Int, IGoogleMarkerData>) -> Unit)?
    var onUpdated: ((Map<Int, IGoogleMarkerData>) -> Unit)?
    fun init(marker: IGoogleMarkerData, context: IViewContext)
    fun initAsync(marker: IGoogleMarkerData, context: IViewContext)
}