package models

import utils.Utilities

data class Artist(
    var artistName: String,
    var artistAge: Int,
    var artistCountry: String,
    var artistMovement: String,
    var artistPopularity: Int,
    var isArtistDeceased: Boolean,
    var artefacts : MutableSet<Artefact> = mutableSetOf()) {

    private var lastArtefactId = 0
    private fun getArtefactId() = lastArtefactId++

    fun addArtefact(artefact: Artefact): Boolean {
        artefact.artefactId = getArtefactId()
        return artefacts.add(artefact)
    }

    fun numberOfArtefacts() = artefacts.size

    fun findOne(id: Int): Artefact?{
        return artefacts.find{ artefact -> artefact.artefactId == id }
    }

    fun delete(id: Int): Boolean {
        return artefacts.removeIf { artefact -> artefact.artefactId == id}
    }

    fun update(id: Int, newArtefact : Artefact): Boolean {
        val foundArtefact = findOne(id)

        //if the object exists, use the details passed in the newArtefact parameter to
        //update the found object in the Set
        if (foundArtefact != null){
            foundArtefact.artefactId = newArtefact.artefactId
            foundArtefact.isArtefactSold = newArtefact.isArtefactSold
            return true
        }

        //if the object was not found, return false, indicating that the update was not successful
        return false
    }

    fun listArtefacts() =
        if (artefacts.isEmpty())  "\tNO ARTEFACTS ADDED"
        else  Utilities.formatSetString(artefacts)

    override fun toString(): String {
        val archived = if (isArtistDeceased) 'Y' else 'N'
        return "$artistName: $artistName, Popularity($artistPopularity), Country($artistCountry), Deceased($archived) \n${listArtefacts()}"
    }

    fun checkArtistCompletionStatus(): Boolean {
        if (artistName.isNotEmpty()) {
            for (artefacts in artefacts) {
                if (!artefacts.isArtefactSold) {
                    return false
                }
            }
        }
        return true //an artist with empty artefacts can be archived, or all artefacts are complete
    }

}