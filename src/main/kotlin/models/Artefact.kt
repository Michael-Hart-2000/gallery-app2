package models

data class Artefact(
    var artefactId: Int = 0,
    var artefactName: String,
    var artefactType: String,
    var artefactCost: String,
    var artefactYearMade: String,
    var artefactPopularity: Int,
    var isArtefactSold: Boolean = false
) {

    override fun toString() =
        if (isArtefactSold)
            "${artefactId}Id: $artefactName (Complete)"
        else
            "${artefactId}Id: $artefactName (TODO)"


}
