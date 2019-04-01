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

    override fun onMapReady(gMap: GoogleMap)
    {
        googleMapPresenter.googleMap = gMap

        val mapParams = GoogleMarkerData(GoogleMarkerData.defaultLocation)
        googleMapPresenter.initAsync(mapParams)

        //todo add move listeners to google map
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
