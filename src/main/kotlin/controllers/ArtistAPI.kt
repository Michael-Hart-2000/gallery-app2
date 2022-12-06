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

    fun listLivingArtists(): String {
        return if (numberOfLivingArtists() == 0) {
            "No living artists stored"
        } else {
            var listOfLivingArtists = ""
            for (artist in artists) {
                if (!artist.isArtistDeceased) {
                    listOfLivingArtists += "${artists.indexOf(artist)}: $artist \n"
                }
            }
            listOfLivingArtists
        }
    }

    fun listDeceasedArtists(): String {
        return if (numberOfDeceasedArtists() == 0) {
            "No deceased artists stored"
        } else {
            var listOfDeceasedArtists = ""
            for (artist in artists) {
                if (artist.isArtistDeceased) {
                    listOfDeceasedArtists += "${artists.indexOf(artist)}: $artist \n"
                }
            }
            listOfDeceasedArtists
        }
    }

    fun numberOfDeceasedArtists(): Int {
        //helper method to determine how many deceased artists there are
        var counter = 0
        for (artist in artists) {
            if (artist.isArtistDeceased) {
                counter++
            }
        }
        return counter
    }

    fun numberOfLivingArtists(): Int {
        //helper method to determine how many living artists there are
        var counter = 0
        for (artist in artists) {
            if (!artist.isArtistDeceased) {
                counter++
            }
        }
        return counter
    }

    fun listArtistsBySelectedPopularity(popularity: Int): String {
        return if (artists.isEmpty()) {
            "No artists stored"
        } else {
            var listOfArtists = ""
            for (i in artists.indices) {
                if (artists[i].artistPopularity == popularity) {
                    listOfArtists +=
                        """$i: ${artists[i]}
                        """.trimIndent()
                }
            }
            if (listOfArtists.equals("")) {
                "No artists with popularity: $popularity"
            } else {
                "${numberOfArtistsByPopularity(popularity)} artists with popularity $popularity: $listOfArtists"
            }
        }
    }

    fun numberOfArtistsByPopularity(popularity: Int): Int {
        var counter = 0
        for (artist in artists) {
            if (artist.artistPopularity == popularity) {
                counter++
            }
        }
        return counter
    }

    fun deleteArtist(indexToDelete: Int): Artist? {
        return if (isValidListIndex(indexToDelete, artists)) {
            artists.removeAt(indexToDelete)
        } else null
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