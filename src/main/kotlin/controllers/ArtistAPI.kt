package controllers

import models.Artist
import persistence.Serializer

const val red = "\u001b[31m"

class ArtistAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var artists = ArrayList<Artist>()

    private fun formatListString(artistsToFormat: List<Artist>): String =
        artistsToFormat
            .joinToString(separator = "\n") { artist ->
                artists.indexOf(artist).toString() + ": " + artist.toString()
            }

    fun add(artist: Artist): Boolean {
        return artists.add(artist)
    }

    fun listAllArtists(): String =
        if (artists.isEmpty()) red + "No artists stored"
        else formatListString(artists)

    fun listLivingArtists(): String =
        if (numberOfLivingArtists() == 0)red + "No living artists stored"
        else formatListString(artists.filter { artist -> !artist.isArtistDeceased })

    fun listDeceasedArtists(): String =
        if (numberOfDeceasedArtists() == 0)red + "No deceased artists stored"
        else formatListString(artists.filter { artist -> artist.isArtistDeceased })

    fun numberOfDeceasedArtists(): Int = artists.count { artist: Artist -> artist.isArtistDeceased }

    fun numberOfLivingArtists(): Int = artists.count { artist: Artist -> !artist.isArtistDeceased }

    fun listArtistsBySelectedPopularity(popularity: Int): String =
        if (artists.isEmpty())red + "No artists stored"
        else {
            val listOfArtists = formatListString(artists.filter { artist -> artist.artistPopularity == popularity })
            if (listOfArtists == "")red + "No artists with popularity: $popularity"
            else "${numberOfArtistsByPopularity(popularity)} artists with popularity $popularity: $listOfArtists"
        }

    fun numberOfArtistsByPopularity(priority: Int): Int = artists.count { p: Artist -> p.artistPopularity == priority }

    fun deleteArtist(indexToDelete: Int): Artist? {
        return if (isValidListIndex(indexToDelete, artists)) {
            artists.removeAt(indexToDelete)
        } else null
    }

    fun updateArtist(indexToUpdate: Int, artist: Artist?): Boolean {
        // find the artist object by the index number
        val foundArtist = findArtist(indexToUpdate)

        // if the artist exists, use the artist details passed as parameters to update the found artist in the ArrayList.
        if ((foundArtist != null) && (artist != null)) {
            foundArtist.artistName = artist.artistName
            foundArtist.artistPopularity = artist.artistPopularity
            foundArtist.artistAge = artist.artistAge
            foundArtist.artistCountry = artist.artistCountry
            foundArtist.artistMovement = artist.artistMovement
            foundArtist.isArtistDeceased = artist.isArtistDeceased

            return true
        }

        // if the artist was not found, return false, indicating that the update was not successful
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

    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, artists)
    }

    fun searchByName(searchString: String) =
        formatListString(
            artists.filter { artist -> artist.artistName.contains(searchString, ignoreCase = true) }
        )

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

    // utility method to determine if an index is valid in a list.
    private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun searchArtefactByContents(searchString: String): String {
        return if (numberOfArtists() == 0) "No artists stored"
        else {
            var listOfArtists = ""
            for (artist in artists) {
                for (artefact in artist.artefacts) {
                    if (artefact.artefactName.contains(searchString, ignoreCase = true)) {
                        listOfArtists += "${artist.artistName}: ${artist.artistAge} \n\t${artefact}\n"
                    }
                }
            }
            if (listOfArtists == "") "No artefacts found for: $searchString"
            else listOfArtists
        }
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR ARTEFACTS
    // ----------------------------------------------
    fun listTodoItems(): String =
        if (numberOfArtists() == 0) "No artists stored"
        else {
            var listOfTodoItems = ""
            for (artist in artists) {
                for (artefact in artist.artefacts) {
                    if (!artefact.isArtefactSold) {
                        listOfTodoItems += artist.artistName + ": " + artefact.artefactName + "\n"
                    }
                }
            }
            listOfTodoItems
        }

    // ----------------------------------------------
    //  COUNTING METHODS FOR ARTEFACTS
    // ----------------------------------------------
    fun numberOfToDoItems(): Int {
        var numberOfToDoItems = 0
        for (artist in artists) {
            for (artefacts in artist.artefacts) {
                if (!artefacts.isArtefactSold) {
                    numberOfToDoItems++
                }
            }
        }
        return numberOfToDoItems
    }
}


