package com.example.twitmap

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import com.example.twitmap.maps.ParcelableMarker

import kotlinx.android.synthetic.main.activity_tweet_info.*

class TweetInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_info)
        setSupportActionBar(toolbar)

        var data =
            intent.getParcelableExtra<ParcelableMarker>(MarkerDataKey)

        //here i use my data to show it to the user

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    companion object {
        const val MarkerDataKey = "MarkerDataKey"
    }

}
