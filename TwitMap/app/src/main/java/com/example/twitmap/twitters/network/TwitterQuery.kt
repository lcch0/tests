package com.example.twitmap.twitters.network

import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.twitmap.interfaces.IGoogleMarkerData
import com.example.twitmap.interfaces.IViewContext
import com.example.twitmap.twitters.network.json.TwitterData
import com.google.gson.Gson

/*
* unfortunately i don't have neither twitter nor twitter dev account,
* and honestly, i don't want to create them and share my personal data to the world
* So this is kind of emulation
* */
class TwitterQuery
{
    //a server that allows to read json files from the github repository
    private val twurl = "https://my-json-server.typicode.com/lcch0/tests/"

    var onSuccess: ((TwitterData) -> Unit)? = null

    fun requestTweetsAround(
        marker: IGoogleMarkerData,
        context: IViewContext
    )
    {
        val queue = Volley.newRequestQueue(context.getContext())

        val stringReq = StringRequest(
            Request.Method.GET, buildSearchQuery(marker),
            Response.Listener<String> {
                try
                {
                    val data =
                        Gson().fromJson(it, TwitterData::class.java)

                    onSuccess?.invoke(data)
                }
                catch (e: Exception)
                {
                    Log.e("Twit request failed", e.toString())
                }
            },
            Response.ErrorListener {
                Log.e("Twit request failed", it?.networkResponse.toString())
            })
        queue.add(stringReq)
    }

    private fun buildSearchQuery(marker: IGoogleMarkerData): String
    {
        //well, suppose here i use marker to generate a query to TW,
        // using my latlong value and radius

        return "$twurl/db "
    }
}