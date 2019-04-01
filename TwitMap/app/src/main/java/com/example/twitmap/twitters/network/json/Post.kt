package com.example.twitmap.twitters.network.json

data class Post(
    val hashTags: List<String>,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val username: String
)