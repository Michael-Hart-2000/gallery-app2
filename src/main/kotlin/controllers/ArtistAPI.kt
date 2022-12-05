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

    fun numberOfArtists(): Int {
        return artists.size
    }

    fun findArtist(index: Int): Artist? {
        return if (isValidListIndex(index, artists)) {
            artists[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

}