package controllers

import models.Artist
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ArtistAPITest {

    private var PabloPicasso: Artist? = null
    private var VincentVanGough: Artist? = null
    private var LeonardoDaVinci: Artist? = null
    private var RembrandtVanRijn: Artist? = null
    private var SalvadorDali: Artist? = null
    private var populatedArtists: ArtistAPI? = ArtistAPI()
    private var emptyArtists: ArtistAPI? = ArtistAPI()

    @BeforeEach
    fun setup(){
        PabloPicasso = Artist("Pablo Picasso", 91, "Spain", artistMovement = "Cubism", artistPopularity = 5, false)
        VincentVanGough = Artist("Vincent Van Gough", 37, "Netherlands", artistMovement = "Post-Impressionism", artistPopularity = 5, false)
        LeonardoDaVinci = Artist("Leonardo Da Vinci", 67, "Italy", artistMovement = "Renaissance", artistPopularity = 5, false)
        RembrandtVanRijn = Artist("Rembrandt Van Rijn", 63, "Netherlands", artistMovement = "Baroque", artistPopularity = 5, false)
        SalvadorDali = Artist("Salvador Dali", 84, "Spain", artistMovement = "Surrealism", artistPopularity = 5, false)

        //adding 5 Artists to the artists api
        populatedArtists!!.add(PabloPicasso!!)
        populatedArtists!!.add(VincentVanGough!!)
        populatedArtists!!.add(LeonardoDaVinci!!)
        populatedArtists!!.add(RembrandtVanRijn!!)
        populatedArtists!!.add(SalvadorDali!!)
    }

    @AfterEach
    fun tearDown(){
        PabloPicasso = null
        VincentVanGough = null
        LeonardoDaVinci = null
        RembrandtVanRijn = null
        SalvadorDali = null
        populatedArtists = null
        emptyArtists = null
    }

    @Test
    fun `adding an Artist to a populated list adds to ArrayList`(){
        val newArtist = Artist("Study Lambdas", 1, "Spain", artistMovement = "Surrealism", artistPopularity = 5, false)
        assertEquals(5, populatedArtists!!.numberOfArtists())
        assertTrue(populatedArtists!!.add(newArtist))
        assertEquals(6, populatedArtists!!.numberOfArtists())
        assertEquals(newArtist, populatedArtists!!.findArtist(populatedArtists!!.numberOfArtists() - 1))
    }

    @Test
    fun `adding an Artist to an empty list adds to ArrayList`(){
        val newArtist = Artist("Study Lambdas", 1, "Spain", artistMovement = "Surrealism", artistPopularity = 5, false)
        assertEquals(0, emptyArtists!!.numberOfArtists())
        assertTrue(emptyArtists!!.add(newArtist))
        assertEquals(1, emptyArtists!!.numberOfArtists())
        assertEquals(newArtist, emptyArtists!!.findArtist(emptyArtists!!.numberOfArtists() - 1))
    }
}