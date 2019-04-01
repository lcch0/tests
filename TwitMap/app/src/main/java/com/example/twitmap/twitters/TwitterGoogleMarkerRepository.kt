package com.example.twitmap.twitters

import com.example.twitmap.interfaces.IGoogleMarkerData
import com.example.twitmap.interfaces.IGoogleMarkerRepository
import com.example.twitmap.interfaces.IViewContext
import com.example.twitmap.maps.GoogleMarkerData
import com.example.twitmap.twitters.network.TwitterQuery
import com.example.twitmap.twitters.network.json.TwitterData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TwitterGoogleMarkerRepository : IGoogleMarkerRepository
{
    override var onUpdated: ((IGoogleMarkerRepository) -> Unit)? = null
    override var onAdded: ((IGoogleMarkerRepository) -> Unit)? = null
    private var _markers = mutableMapOf<Int, IGoogleMarkerData>()
    override val markerList: MutableMap<Int, IGoogleMarkerData>
        get() = _markers

    override fun initAsync(marker: IGoogleMarkerData, context: IViewContext)
    {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch { init(marker, context)}
    }

    override fun init(marker: IGoogleMarkerData, context: IViewContext)
    {
        val query = TwitterQuery()
        query.onSuccess = {d -> initMarkers(d)}

        query.requestTweetsAround(marker, context)
    }

    override fun update(marker: IGoogleMarkerData, context: IViewContext)
    {
        val query = TwitterQuery()
        query.onSuccess = {d -> updateMarkers(d)}

        query.requestTweetsAround(marker, context)
    }

    override fun updateAsync(marker: IGoogleMarkerData, context: IViewContext)
    {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch { update(marker, context)}
    }

    private fun initMarkers(data: TwitterData)
    {
        _markers.clear()

        for (post in data.entry.posts)
        {
            val newMarker = GoogleMarkerData(LatLng(post.latitude, post.longitude))
            newMarker.description = post.title
            newMarker.userName = post.username
            newMarker.hashTags = post.hashTags
            _markers[newMarker.hashCode()] = newMarker
        }

        onAdded?.invoke(this)
    }

    private fun updateMarkers(data: TwitterData)
    {
        for (post in data.entry.posts)
        {
            val latLong = LatLng(post.latitude, post.longitude)

            val hash = latLong.hashCode()
            var found = _markers[hash]

            if(found == null)
            {
                found = GoogleMarkerData(latLong)
            }

            found.description = post.title
            found.userName = post.username
            found.hashTags = post.hashTags
            _markers[hash] = found
        }

        onUpdated?.invoke(this)
    }
}