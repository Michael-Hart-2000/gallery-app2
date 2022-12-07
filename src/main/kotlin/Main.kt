import controllers.ArtistAPI
//import controllers.ArtefactsAPI
import models.Artefact
import models.Artist
import mu.KotlinLogging
import persistence.JSONSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextChar
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

// private val artistAPI = ArtistAPI(XMLSerializer(File("artists.xml")))
private val artistAPI = ArtistAPI(JSONSerializer(File("artists.json")))
private val logger = KotlinLogging.logger {}

val yellow = "\u001B[33m"
const val red = "\u001b[31m"
val green = "\u001B[32m"

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu(): Int {
    return ScannerInput.readNextInt(
        yellow + """ 
         > ------------------------------------------------------------------
         > |                          GALLERY APP                           |
         > ------------------------------------------------------------------
         > | ARTIST MENU                                                    |
         > |   1)  Add an artist                                            |
         > |   2)  List all artists                                         |
         > |   3)  Update an artist                                         |
         > |   4)  Delete an artist                                         |
         > |   5)  Archive an artist                                        |
         > ------------------------------------------------------------------
         > | ARTEFACT MENU                                                  |
         > |   6)  Add artefact to an artist                                |
         > |   7)  Update artefact contents on an artist                    |
         > |   8)  Delete artefact from an artist                           |
         > |   9)  Mark artefact as sold/unsold                             |
         > ------------------------------------------------------------------
         > | SEARCH MENU FOR ARTISTS                                        |
         > |   10) Search artist(by Name)                                   |
         > |   11) ...                                                      |
         > |   12) ...                                                      |
         > |   13) ...                                                      |
         > |   14) ...                                                      |
         > ------------------------------------------------------------------
         > | SEARCH MENU FOR ARTEFACTS                                      |
         > |   15) Search for all artefacts (by artefact description)       |
         > |   16) List TODO Items                                          |
         > |   17) List All Artefacts                                       |
         > |   18) Search for artefacts (by sold/unsold)                    |
         > |   19) ...                                                      |
         > ------------------------------------------------------------------
         > |   20) Save artists                                             |
         > |   21) Load artists                                             |
         > ------------------------------------------------------------------
         > |   0) Exit                                                      |
         > ------------------------------------------------------------------
         > ==>> """.trimMargin(">")
    )
}

fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> addArtist()
            2 -> listArtists()
            3 -> updateArtist()
            4 -> deleteArtist()
            5 -> archiveArtist()
            6 -> addArtefactToArtist()
            7 -> updateArtefactContentsInArtist()
            8 -> deleteAnArtefact()
            9 -> markArtefactStatus()
            10 -> searchArtists()
            15 -> searchArtefacts()
            16 -> listToDoArtefacts()
            0 -> exitApp()
            20 -> save()
            21 -> load()
            0 -> exitApp()
            else -> println(red + "Invalid option entered: $option")
        }
    } while (true)
}

fun addArtist() {
    // logger.info { "addArtist() function invoked" }
    val artistName = readNextLine(green + "Enter the name of the artist: ")
    val artistAge = readNextInt(green + "Enter the age of the artist: ")
    val artistCountry = readNextLine(green + "Enter the country the artist is from: ")
    val artistMovement = readNextLine(green + "Enter the art movement the artist is associated with: ")
    val artistPopularity = readNextInt(green + "Enter the popularity of the artist (1-low, 2, 3, 4, 5-high): ")
    val isAdded = artistAPI.add(Artist(artistName, artistAge, artistCountry, artistMovement, artistPopularity, false))

    if (isAdded) {
        println(green + "Added Successfully")
    } else {
        println(red + "Add Failed")
    }
}

fun listArtists() {
    if (artistAPI.numberOfArtists() > 0) {
        val option = readNextInt(
            yellow +
                """
                  > --------------------------------
                  > |   1) View ALL artists        |
                  > |   2) View Living artists     |
                  > |   3) View Deceased artists   |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllArtists()
            2 -> listLivingArtists()
            3 -> listDeceasedArtists()
            else -> println(red + "Invalid option entered: " + option)
        }
    } else {
        println(red + "Option Invalid - No artists stored")
    }
}

fun listAllArtists() {
    println(artistAPI.listAllArtists())
}

/*fun listArtefacts() {
    if (ArtefactsAPI.numberOfArtefacts() > 0) {
        val option = readNextInt(
            yellow +
                    """
                  > --------------------------------
                  > |   1) View ALL artefacts      |
                  > |   2) ...                     |
                  > |   3) ...                     |
                  > --------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllArtefacts()
            //2 ->
            //3 ->
            else -> println(red + "Invalid option entered: " + option)
        }
    } else {
        println(red + "Option Invalid - No artefacts stored")
    }
}

fun listAllArtefacts() {
    println(artistAPI.listAllArtefacts())
}
 */

fun listDeceasedArtists() {
    println(artistAPI.listDeceasedArtists())
}

fun updateArtist() {
    // logger.info { "updateArtists() function invoked" }
    listArtists()
    if (artistAPI.numberOfArtists() > 0) {
        // only ask the user to choose the artist if artists exist
        val indexToUpdate = readNextInt(green + "Enter the index of the artist to update: ")
        if (artistAPI.isValidIndex(indexToUpdate)) {
            val artistName = readNextLine(green + "Enter the name of the artist: ")
            val artistAge = readNextInt(green + "Enter the age of the artist: ")
            val artistCountry = readNextLine(green + "Enter the country the artist is from: ")
            val artistMovement = readNextLine(green + "Enter the movement the artist is associated with: ")
            val artistPopularity = readNextInt(green + "Enter the popularity of the artist: ")

            // pass the index of the artist and the new artist details to artistAPI for updating and check for success.
            if (artistAPI.updateArtist(indexToUpdate, Artist(artistName, artistAge, artistCountry, artistMovement, artistPopularity, false))) {
                println(green + "Update Successful")
            } else {
                println(red + "Update Failed")
            }
        } else {
            println(red + "There are no artists for this index number")
        }
    }
}

fun deleteArtist() {
    // logger.info { "deleteArtists() function invoked" }
    listArtists()
    if (artistAPI.numberOfArtists() > 0) {
        // only ask the user to choose the artist to remove if artists exist
        val indexToDelete = readNextInt(green + "Enter the index of the artist to delete: ")
        // pass the index of the artist to ArtistAPI for deleting and check for success.
        val artistToDelete = artistAPI.deleteArtist(indexToDelete)
        if (artistToDelete != null) {
            println(green + "Delete Successful! Deleted artist: ${artistToDelete.artistName}")
        } else {
            println(red + "Delete NOT Successful")
        }
    }
}

fun listLivingArtists() {
    println(artistAPI.listLivingArtists())
}

fun archiveArtist() {
    listLivingArtists()
    if (artistAPI.numberOfLivingArtists() > 0) {
        // only ask the user to choose the artist to archive if living artists exist
        val indexToArchive = readNextInt("Enter the index of the artist to archive: ")
        // pass the index of the artist to ArtistAPI for archiving and check for success.
        if (artistAPI.archiveArtist(indexToArchive)) {
            println(green + "Archive Successful!")
        } else {
            println(red + "Archive NOT Successful")
        }
    }
}

fun save() {
    try {
        artistAPI.store()
    } catch (e: Exception) {
        System.err.println(red + "Error writing to file: $e")
    }
}

fun load() {
    try {
        artistAPI.load()
    } catch (e: Exception) {
        System.err.println(red + "Error reading from file: $e")
    }
}

fun searchArtists() {
    val searchName = readNextLine(green + "Enter the name to search by: ")
    val searchResults = artistAPI.searchByName(searchName)
    if (searchResults.isEmpty()) {
        println(red + "No artists found")
    } else {
        println(searchResults)
    }
}

private fun askUserToChooseLivingArtist(): Artist? {
    listLivingArtists()
    if (artistAPI.numberOfLivingArtists() > 0) {
        val artist = artistAPI.findArtist(readNextInt("\nEnter the id of the artist: "))
        if (artist != null) {
            if (artist.isArtistDeceased) {
                println("Artist is NOT Living, they are Deceased")
            } else {
                return artist // chosen artist is Living
            }
        } else {
            println("Artist id is not valid")
        }
    }
    return null // selected artist is not Living
}

private fun addArtefactToArtist() {
    val artist: Artist? = askUserToChooseLivingArtist()
    if (artist != null) {
        if (artist.addArtefact(Artefact(artefactId = readNextInt("\t Artefact Id: "), artefactName = readNextLine("\t Artefact Name: "), artefactType = readNextLine("\t Artefact Type: "), artefactCost = readNextLine("\t Artefact Cost:"), artefactYearMade = readNextLine("\t Artefact Year Made: "), artefactPopularity = readNextInt("\t Artefact Popularity: "))))
            println("Add Successful!")
        else println("Add NOT Successful")
    }
}

private fun askUserToChooseArtefact(artist: Artist): Artefact? {
    if (artist.numberOfArtefacts() > 0) {
        print(artist.listArtefacts())
        return artist.findOne(readNextInt("\nEnter the id of the artefact: "))
    } else {
        println("No artefacts for chosen artist")
        return null
    }
}

fun updateArtefactContentsInArtist() {
    val artist: Artist? = askUserToChooseLivingArtist()
    if (artist != null) {
        val artefact: Artefact? = askUserToChooseArtefact(artist)
        if (artefact != null) {
            val newName = readNextLine("Enter new Name: ")
            val newType = readNextLine("Enter new Type: ")
            val newCost = readNextLine("Enter new Cost: ")
            val newYearMade = readNextLine("Enter new Year Made: ")
            val newPopularity = readNextInt("Enter new Popularity: ")
            if (artist.update(artefact.artefactId, Artefact(artefactName = newName, artefactType = newType, artefactCost = newCost, artefactYearMade = newYearMade, artefactPopularity = newPopularity))) {
                println("Artefact contents updated")
            } else {
                println("Artefact contents NOT updated")
            }
        } else {
            println("Invalid Artefact Id")
        }
    }
}

fun deleteAnArtefact() {
    val artist: Artist? = askUserToChooseLivingArtist()
    if (artist != null) {
        val artefact: Artefact? = askUserToChooseArtefact(artist)
        if (artefact != null) {
            val isDeleted = artist.delete(artefact.artefactId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

fun markArtefactStatus() {
    val artist: Artist? = askUserToChooseLivingArtist()
    if (artist != null) {
        val artefact: Artefact? = askUserToChooseArtefact(artist)
        if (artist != null) {
            var changeStatus = 'X'
            if (artefact != null) {
                if (artefact.isArtefactSold) {
                    changeStatus = readNextChar("The item is currently sold...do you want to mark it as unsold?")
                    if ((changeStatus == 'Y') || (changeStatus == 'y'))
                        artefact.isArtefactSold = false
                } else {
                    changeStatus = readNextChar("The item is currently unsold...do you want to mark it as Sold?")
                    if ((changeStatus == 'Y') || (changeStatus == 'y'))
                        artefact.isArtefactSold = true
                }
            }
        }
    }
}

fun searchArtefacts() {
    val searchContents = readNextLine("Enter the artefact contents to search by: ")
    val searchResults = artistAPI.searchArtefactByContents(searchContents)
    if (searchResults.isEmpty()) {
        println("No artefacts found")
    } else {
        println(searchResults)
    }
}

fun listToDoArtefacts() {
    if (artistAPI.numberOfToDoItems() > 0) {
        println("Total Sold items: ${artistAPI.numberOfToDoItems()}")
    }
    println(artistAPI.listTodoItems())
}

fun exitApp() {
    println(red + "Exiting...bye")
}
