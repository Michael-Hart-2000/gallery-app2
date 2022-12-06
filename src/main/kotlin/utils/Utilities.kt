package utils

import models.Artefact
import models.Artist

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(artistsToFormat: List<Artist>): String =
        artistsToFormat
            .joinToString(separator = "\n") { artist -> "$artist" }

    @JvmStatic
    fun formatSetString(artefactsToFormat: Set<Artefact>): String =
        artefactsToFormat
            .joinToString(separator = "\n") { artefact -> "\t$artefact" }
}
