package controllers

import models.Artist

class ArtistAPI {
    private var artists = ArrayList<Artist>()

    fun add(artist: Artist): Boolean {
        return artists.add(artist)
    }

    fun listAllArtists(): String {
        return if (artists.isEmpty()) {
            "No artists stored"
        } else {
            var listOfArtists = ""
            for (i in artists.indices) {
                listOfArtists += "${i}: ${artists[i]} \n"
            }
            listOfArtists
        }
    }

}