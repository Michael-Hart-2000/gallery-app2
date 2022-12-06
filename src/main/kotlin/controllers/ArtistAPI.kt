package controllers

import models.Artist
import persistence.Serializer

class ArtistAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var artists = ArrayList<Artist>()

    private fun formatListString(artistsToFormat : List<Artist>) :String =
        artistsToFormat
            .joinToString (separator = "\n") { artist ->
                artists.indexOf(artist).toString() + ": " + artist.toString()
            }

    fun add(artist: Artist): Boolean {
        return artists.add(artist)
    }

    fun listAllArtists(): String =
        if  (artists.isEmpty()) "No artists stored"
        else artists.joinToString (separator = "\n") { artist ->
            artists.indexOf(artist).toString() + ": " + artist.toString() }

    fun listLivingArtists(): String =
        if  (numberOfLivingArtists() == 0)  "No living artists stored"
        else formatListString(artists.filter { artist -> !artist.isArtistDeceased})

    fun listDeceasedArtists(): String =
        if  (numberOfDeceasedArtists() == 0) "No deceased artists stored"
        else formatListString(artists.filter { artist -> artist.isArtistDeceased})

    fun numberOfDeceasedArtists(): Int = artists.count { artist: Artist -> artist.isArtistDeceased }

    fun numberOfLivingArtists(): Int {
        return artists.stream()
            .filter{artist: Artist -> !artist.isArtistDeceased}
            .count()
            .toInt()
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

    fun numberOfArtistsByPopularity(priority: Int): Int = artists.count { p: Artist -> p.artistPopularity == priority }

    fun deleteArtist(indexToDelete: Int): Artist? {
        return if (isValidListIndex(indexToDelete, artists)) {
            artists.removeAt(indexToDelete)
        } else null
    }

    fun updateArtist(indexToUpdate: Int, artist: Artist?): Boolean {
        //find the artist object by the index number
        val foundArtist = findArtist(indexToUpdate)

        //if the artist exists, use the artist details passed as parameters to update the found artist in the ArrayList.
        if ((foundArtist != null) && (artist != null)) {
            foundArtist.artistName = artist.artistName
            foundArtist.artistPopularity = artist.artistPopularity
            foundArtist.artistAge = artist.artistAge
            foundArtist.artistCountry = artist.artistCountry
            foundArtist.artistMovement = artist.artistMovement
            foundArtist.isArtistDeceased = artist.isArtistDeceased

            return true
        }

        //if the artist was not found, return false, indicating that the update was not successful
        return false
    }

    fun archiveArtist(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val artistToArchive = artists[indexToArchive]
            if (!artistToArchive.isArtistDeceased) {
                artistToArchive.isArtistDeceased = true
                return true
            }
        }
        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, artists);
    }

    fun searchByName (searchString : String) =
        formatListString(
            artists.filter { artist -> artist.artistName.contains(searchString, ignoreCase = true) })

    @Throws(Exception::class)
    fun load() {
        artists = serializer.read() as ArrayList<Artist>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(artists)
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