import controllers.ArtistAPI
import models.Artist
import utils.ScannerInput
import java.lang.System.exit
import mu.KotlinLogging
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

private val logger = KotlinLogging.logger {}
private val artistAPI = ArtistAPI()


fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |          GALLERY APP           |
         > ----------------------------------
         > | ARTIST MENU                    |
         > |   1) Add an artist             |
         > |   2) List all artists          |
         > |   3) Update an artist          |
         > |   4) Delete an artist          |
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

fun listArtists(){
    //logger.info { "listArtists() function invoked" }
    println(artistAPI.listAllArtists())
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
        //only ask the user to choose the artist to delete if artists exist
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

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}