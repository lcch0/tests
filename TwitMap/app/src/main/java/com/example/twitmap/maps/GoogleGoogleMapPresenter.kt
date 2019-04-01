package com.example.twitmap.maps

import com.example.twitmap.interfaces.IGoogleMapPresenter
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.example.twitmap.interfaces.IGoogleMarkerRepository
import com.example.twitmap.interfaces.IViewContext
import com.example.twitmap.twitters.TwitterGoogleMarkerRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleGoogleMapPresenter(override var context: IViewContext) : IGoogleMapPresenter
{
    private var _markerRepo = mutableListOf<IGoogleMarkerRepository>()
    override val geoMarkerRepositories: List<IGoogleMarkerRepository>
        get() = _markerRepo



    init
    {
        _markerRepo.add(TwitterGoogleMarkerRepository())//i may add a factory here, or use some IoC, but let's be simple, it's not the complicated case where we need it

        _markerRepo[0].onUpdated = { updateMarkers(it) }
        _markerRepo[0].onAdded = { addMarkers(it) }
    }

    //risky cast, but this is a test proj, eh?
    private val twitterRepo = _markerRepo[0] as TwitterGoogleMarkerRepository
    override var googleMap: GoogleMap? = null

    override fun init(marker: IGoogleMarkerData)
    {
        twitterRepo.initAsync(marker, context)

        addMarker(marker)
        val camera = CameraUpdateFactory.newLatLngZoom(marker.center, 15f)
        googleMap?.moveCamera(camera)

        addMarkers(twitterRepo.markerList)

        googleMap?.setOnMarkerClickListener {  m -> onMarkerClick(m)  }
    }

    override fun initAsync(marker: IGoogleMarkerData)
    {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            init(marker)
        }
    }

    override fun moveToSquare(marker: IGoogleMarkerData)
    {
        val bounds = marker.calculateBounds(5000f)
        val camera = CameraUpdateFactory.newLatLngBounds(bounds, 0)
        googleMap?.animateCamera(camera)
    }

    override fun addMarker(marker: IGoogleMarkerData)
    {
        val mo = createMarkerOptions(marker)
        val hash = marker.hashCode()
        val m = googleMap?.addMarker(mo)

        if(m != null)
        {
            marker.markerOptions = mo
            marker.marker = m
            twitterRepo.markerList[hash] = marker
        }
    }

    override fun updateMarker(marker: IGoogleMarkerData)
    {
        val mo = createMarkerOptions(marker)
        val hash = marker.hashCode()
        if(!twitterRepo.markerList.containsKey(hash))
        {
            val m = googleMap?.addMarker(mo)
            if(m != null)
            {
                marker.markerOptions = mo
                marker.marker = m
                twitterRepo.markerList[hash] = marker
            }
        }
    }

    private fun createMarkerOptions(mapData: IGoogleMarkerData) =
        MarkerOptions()
            .position(mapData.center)
            .title("${mapData.userName}\n${mapData.description}")

    private fun updateMarkers(map: Map<Int, IGoogleMarkerData>)
    {
        for (m in map)
        {
            updateMarker(m.value)
        }
    }

    private fun addMarkers(map: Map<Int, IGoogleMarkerData>)
    {
        for (m in map)
        {
            addMarker(m.value)
        }
    }

    private fun onMarkerClick(marker: Marker?): Boolean
    {
        if(marker == null)
            return false

        val hash = marker.position.hashCode()
        val found = twitterRepo.markerList[hash] ?: return false

        context.showMarkerInfo(found)

        return true
    }
}