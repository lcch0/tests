package com.example.twitmap.maps

import com.example.twitmap.interfaces.IGoogleMapPresenter
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.example.twitmap.interfaces.IGoogleMarkerRepository
import com.example.twitmap.interfaces.IViewContext
import com.example.twitmap.twitters.TwitterGoogleMarkerRepository
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GoogleGoogleMapPresenter(override var context: IViewContext) : IGoogleMapPresenter
{
    private var currentPosition: LatLng = GoogleMarkerData.defaultLocation
    private var _markerRepo = mutableListOf<IGoogleMarkerRepository>()

    init
    {
        _markerRepo.add(TwitterGoogleMarkerRepository())//i may add a factory here, or use some IoC, but let's be simple, it's not the complicated case where we need it

        _markerRepo[0].onUpdated = { updateMarkers(twitterRepo) }
        _markerRepo[0].onAdded = { addMarkers(twitterRepo) }
    }


    override val geoMarkerRepositories: List<IGoogleMarkerRepository>
        get() = _markerRepo

    //risky cast, but this is a test proj, eh?
    private val twitterRepo = _markerRepo[0] as TwitterGoogleMarkerRepository
    override var googleMap: GoogleMap? = null

    override fun init(marker: IGoogleMarkerData)
    {
        currentPosition = LatLng(marker.position.latitude, marker.position.longitude)
        twitterRepo.initAsync(marker, context)

        val camera = CameraUpdateFactory.newLatLngZoom(marker.position, 15f)
        googleMap?.moveCamera(camera)
        googleMap?.setOnMarkerClickListener {  m -> onMarkerClick(m)  }
    }

    override fun initAsync(marker: IGoogleMarkerData)
    {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            init(marker)
        }
    }

    override fun update()
    {
        val position = googleMap?.cameraPosition?:return

        val marker = GoogleMarkerData(position.target)
        twitterRepo.update(marker, context)

        googleMap?.setOnMarkerClickListener {  m -> onMarkerClick(m)  }
    }

    override fun updateAsync()
    {
        val uiScope = CoroutineScope(Dispatchers.Main)
        uiScope.launch {
            update()
        }
    }

    override fun moveToSquare(marker: IGoogleMarkerData)
    {
        val bounds = marker.calculateBounds(5000f)
        val camera = CameraUpdateFactory.newLatLngBounds(bounds, 0)
        googleMap?.animateCamera(camera)
    }

    private fun createMarkerOptions(mapData: IGoogleMarkerData) =
        MarkerOptions()
            .position(mapData.position)
            .title("${mapData.userName}\n${mapData.description}")

    private fun updateMarkers(map: TwitterGoogleMarkerRepository)
    {
        for (m in map.markerList)
        {
            //add non existing markers
            if(m.value.marker == null)
            {
                val mo = createMarkerOptions(m.value)
                val marker = googleMap?.addMarker(mo)

                if(marker != null)
                {
                    m.value.markerOptions = mo
                    m.value.marker = marker
                }
            }
            else//update existing
            {
                m.value.marker?.remove()
                val mo = createMarkerOptions(m.value)
                val marker = googleMap?.addMarker(mo)
                if(marker != null)
                {
                    m.value.markerOptions = mo
                    m.value.marker = marker
                }
            }
        }
    }

    private fun addMarkers(repository: IGoogleMarkerRepository)
    {
        for (m in repository.markerList)
        {
            val mo = createMarkerOptions(m.value)
            val marker = googleMap?.addMarker(mo)

            if(marker != null)
            {
                m.value.markerOptions = mo
                m.value.marker = marker
            }
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