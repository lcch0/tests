package com.example.twitmap

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.twitmap.interfaces.IGoogleMapPresenter
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.example.twitmap.interfaces.IViewContext
import com.example.twitmap.maps.GoogleGoogleMapPresenter
import com.example.twitmap.maps.GoogleMarkerData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, IViewContext
{
    override fun getContext(): Context = this

    private lateinit var googleMapPresenter: IGoogleMapPresenter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        googleMapPresenter = GoogleGoogleMapPresenter(this)
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(gMap: GoogleMap)
    {
        googleMapPresenter.googleMap = gMap

        val mapParams = GoogleMarkerData(GoogleMarkerData.defaultLocation)
        googleMapPresenter.initAsync(mapParams)
        //tod add move listeners
    }

    override fun showMarkerInfo(markerData: IGoogleMarkerData)
    {
        TweetInfoFragment.showDialog(supportFragmentManager, markerData)
        {
            val intent = Intent(this, TweetInfoActivity::class.java)
            intent.putExtra(TweetInfoActivity.MarkerDataKey, it.getAsParcelable())
            startActivity(intent)
        }
    }
}
