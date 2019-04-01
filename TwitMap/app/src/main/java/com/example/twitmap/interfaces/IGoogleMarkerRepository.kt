package com.example.twitmap.interfaces

interface IGoogleMarkerRepository
{
    val markerList: MutableMap<Int, IGoogleMarkerData>
    var onAdded: ((IGoogleMarkerRepository) -> Unit)?
    var onUpdated: ((IGoogleMarkerRepository) -> Unit)?
    fun init(marker: IGoogleMarkerData, context: IViewContext)
    fun initAsync(marker: IGoogleMarkerData, context: IViewContext)

    fun update(marker: IGoogleMarkerData, context: IViewContext)
    fun updateAsync(marker: IGoogleMarkerData, context: IViewContext)
}