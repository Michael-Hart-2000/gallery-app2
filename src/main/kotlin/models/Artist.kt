package models

data class Artist(
    var artistName: String,
    var artistAge: Int,
    var artistCountry: String,
    var artistMovement: String,
    var artistPopularity: Int,
    var isArtistDeceased: Boolean){
}