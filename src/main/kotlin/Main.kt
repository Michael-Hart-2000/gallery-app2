import controllers.ArtistAPI
import models.Artist
import utils.ScannerInput
import java.lang.System.exit
import mu.KotlinLogging
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

//private val artistAPI = ArtistAPI(XMLSerializer(File("artists.xml")))
private val artistAPI = ArtistAPI(JSONSerializer(File("artists.json")))
private val logger = KotlinLogging.logger {}



fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |          GALLERY APP           |
         > ----------------------------------
         > | ARTIST MENU                    |
         > |   1)  Add an artist            |
         > |   2)  List all artists         |
         > |   3)  Update an artist         |
         > |   4)  Delete an artist         |
         > |   5)  Archive an artist        |
         > |   6)  Search artist(by Name)   |
         > |   20) Save artists             |
         > |   21) Load artists             |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addArtist()
            2  -> listArtists()
            3  -> updateArtist()
            4  -> deleteArtist()
            5  -> archiveArtist()
            6  -> searchArtists()
            20  -> save()
            21  -> load()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addArtist(){
    //logger.info { "addArtist() function invoked" }
    val artistName = readNextLine("Enter the name of the artist: ")
    val artistAge = readNextInt("Enter the age of the artist: ")
    val artistCountry = readNextLine("Enter the country the artist is from: ")
    val artistMovement = readNextLine("Enter the art movement the artist is associated with: ")
    val artistPopularity = readNextInt("Enter the popularity of the artist (1-low, 2, 3, 4, 5-high): ")
    val isAdded = artistAPI.add(Artist(artistName, artistAge, artistCountry,artistMovement,artistPopularity, true))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listArtists() {
    if (artistAPI.numberOfArtists() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL artists        |
                  > |   2) View Living artists     |
                  > |   3) View Deceased artists   |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllArtists();
            2 -> listLivingArtists();
            3 -> listDeceasedArtists();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No artists stored");
    }
}

fun listAllArtists() {
    println(artistAPI.listAllArtists())
}

fun listDeceasedArtists() {
    println(artistAPI.listDeceasedArtists())
}

fun updateArtist() {
    //logger.info { "updateArtists() function invoked" }
    listArtists()
    if (artistAPI.numberOfArtists() > 0) {
        //only ask the user to choose the artist if artists exist
        val indexToUpdate = readNextInt("Enter the index of the artist to update: ")
        if (artistAPI.isValidIndex(indexToUpdate)) {
            val artistName = readNextLine("Enter the name of the artist: ")
            val artistAge = readNextInt("Enter the age of the artist: ")
            val artistCountry = readNextLine("Enter the country the artist is from: ")
            val artistMovement = readNextLine("Enter the movement the artist is associated with: ")
            val artistPopularity = readNextInt("Enter the popularity of the artist: ")

            //pass the index of the artist and the new artist details to artistAPI for updating and check for success.
            if (artistAPI.updateArtist(indexToUpdate, Artist(artistName, artistAge, artistCountry, artistMovement, artistPopularity, false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no artists for this index number")
        }
    }
}

fun deleteArtist(){
    //logger.info { "deleteArtists() function invoked" }
    listArtists()
    if (artistAPI.numberOfArtists() > 0) {
        //only ask the user to choose the artist to remove if artists exist
        val indexToDelete = readNextInt("Enter the index of the artist to delete: ")
        //pass the index of the artist to ArtistAPI for deleting and check for success.
        val artistToDelete = artistAPI.deleteArtist(indexToDelete)
        if (artistToDelete != null) {
            println("Delete Successful! Deleted artist: ${artistToDelete.artistName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun listLivingArtists() {
    println(artistAPI.listLivingArtists())
}

fun archiveArtist() {
    listLivingArtists()
    if (artistAPI.numberOfLivingArtists() > 0) {
        //only ask the user to choose the artist to archive if living artists exist
        val indexToArchive = readNextInt("Enter the index of the artist to archive: ")
        //pass the index of the artist to ArtistAPI for archiving and check for success.
        if (artistAPI.archiveArtist(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

fun save() {
    try {
        artistAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        artistAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun searchArtists() {
    val searchName = readNextLine("Enter the name to search by: ")
    val searchResults = artistAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println("No artists found")
    } else {
        println(searchResults)
    }
}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}