package controllers

import models.Artist
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class ArtistAPITest {

    private var PabloPicasso: Artist? = null
    private var VincentVanGough: Artist? = null
    private var LeonardoDaVinci: Artist? = null
    private var RembrandtVanRijn: Artist? = null
    private var SalvadorDali: Artist? = null
    private var populatedArtists: ArtistAPI? = ArtistAPI()
    private var emptyArtists: ArtistAPI? = ArtistAPI()

    @BeforeEach
    fun setup() {
        PabloPicasso = Artist("Pablo Picasso", 91, "Spain", artistMovement = "Cubism", artistPopularity = 5, true)
        VincentVanGough = Artist(
            "Vincent Van Gough",
            37,
            "Netherlands",
            artistMovement = "Post-Impressionism",
            artistPopularity = 5,
            true
        )
        LeonardoDaVinci =
            Artist("Leonardo Da Vinci", 67, "Italy", artistMovement = "Renaissance", artistPopularity = 5, true)
        RembrandtVanRijn =
            Artist("Rembrandt Van Rijn", 63, "Netherlands", artistMovement = "Baroque", artistPopularity = 5, true)
        SalvadorDali = Artist("Salvador Dali", 84, "Spain", artistMovement = "Surrealism", artistPopularity = 5, true)

        //adding 5 Artists to the artists api
        populatedArtists!!.add(PabloPicasso!!)
        populatedArtists!!.add(VincentVanGough!!)
        populatedArtists!!.add(LeonardoDaVinci!!)
        populatedArtists!!.add(RembrandtVanRijn!!)
        populatedArtists!!.add(SalvadorDali!!)
    }

    @AfterEach
    fun tearDown() {
        PabloPicasso = null
        VincentVanGough = null
        LeonardoDaVinci = null
        RembrandtVanRijn = null
        SalvadorDali = null
        populatedArtists = null
        emptyArtists = null
    }

    @Nested
    inner class AddArtists {
        @Test
        fun `adding an Artist to a populated list adds to ArrayList`() {
            val newArtist =
                Artist("Study Lambdas", 1, "Spain", artistMovement = "Surrealism", artistPopularity = 5, true)
            assertEquals(5, populatedArtists!!.numberOfArtists())
            assertTrue(populatedArtists!!.add(newArtist))
            assertEquals(6, populatedArtists!!.numberOfArtists())
            assertEquals(newArtist, populatedArtists!!.findArtist(populatedArtists!!.numberOfArtists() - 1))
        }

        @Test
        fun `adding an Artist to an empty list adds to ArrayList`() {
            val newArtist =
                Artist("Study Lambdas", 1, "Spain", artistMovement = "Surrealism", artistPopularity = 5, true)
            assertEquals(0, emptyArtists!!.numberOfArtists())
            assertTrue(emptyArtists!!.add(newArtist))
            assertEquals(1, emptyArtists!!.numberOfArtists())
            assertEquals(newArtist, emptyArtists!!.findArtist(emptyArtists!!.numberOfArtists() - 1))
        }
    }

    @Nested
    inner class ListArtists {
        @Test
        fun `listAllArtists returns No Artists Stored message when ArrayList is empty`() {
            assertEquals(0, emptyArtists!!.numberOfArtists())
            assertTrue(emptyArtists!!.listAllArtists().lowercase().contains("no artists"))
        }

        @Test
        fun `listAllArtists returns Artists when ArrayList has artists stored`() {
            assertEquals(5, populatedArtists!!.numberOfArtists())
            val artistsString = populatedArtists!!.listAllArtists().lowercase()
            assertFalse(artistsString.contains("Pablo Picasso"))
            assertFalse(artistsString.contains("Vincent Van Gough"))
            assertFalse(artistsString.contains("Leonardo Da Vinci"))
            assertFalse(artistsString.contains("Rembrandt Van Rijn"))
            assertFalse(artistsString.contains("Salvador Dali"))
        }


    @Test
    fun `listLivingArtists returns no living aritsts stored when ArrayList is empty`() {
        assertEquals(0, emptyArtists!!.numberOfLivingArtists())
        assertTrue(
            emptyArtists!!.listLivingArtists().lowercase().contains("no living artists")
        )
    }

    @Test
    fun `listLivingArtists returns living artists when ArrayList has living artists stored`() {
        assertEquals(0, populatedArtists!!.numberOfLivingArtists())
        val livingArtistsString = populatedArtists!!.listLivingArtists().lowercase()
        assertFalse(livingArtistsString.contains("Pablo Picasso"))
        assertFalse(livingArtistsString.contains("Vincent Van Gough"))
        assertFalse(livingArtistsString.contains("Leonardo Da Vinci"))
        assertFalse(livingArtistsString.contains("Rembrandt Van Rijn"))
        assertFalse(livingArtistsString.contains("Salvador Dali"))
    }

    @Test
    fun `listDeceasedArtists returns no deceased artists when ArrayList is empty`() {
        assertEquals(0, emptyArtists!!.numberOfDeceasedArtists())
        assertTrue(
            emptyArtists!!.listDeceasedArtists().lowercase().contains("no deceased artists")
        )
    }

    @Test
    fun `listDeceasedArtists returns deceased artists when ArrayList has deceased artists stored`() {
        assertEquals(5, populatedArtists!!.numberOfDeceasedArtists())
        val deceasedArtistsString = populatedArtists!!.listDeceasedArtists().lowercase(Locale.getDefault())
        assertFalse(deceasedArtistsString.contains("Pablo Picasso"))
        assertFalse(deceasedArtistsString.contains("Vincent Van Gough"))
        assertFalse(deceasedArtistsString.contains("Leonardo Da Vinci"))
        assertFalse(deceasedArtistsString.contains("Rembrandt Van Rijn"))
        assertFalse(deceasedArtistsString.contains("Salvador Dali"))
    }

        @Test
        fun `listArtistsBySelectedPopularity returns No Artists when ArrayList is empty`() {
            assertEquals(0, emptyArtists!!.numberOfArtists())
            assertTrue(emptyArtists!!.listArtistsBySelectedPopularity(1).lowercase().contains("no artists")
            )
        }

        @Test
        fun `listArtistsBySelectedPopularity returns no artists when no artists of that popularity exist`() {
            //Popularity 1 (1 artist), 2 (artist), 3 (1 artist). 4 (2 artists), 5 (1 artist)
            assertEquals(5, populatedArtists!!.numberOfArtists())
            val popularity2String = populatedArtists!!.listArtistsBySelectedPopularity(2).lowercase()
            assertTrue(popularity2String.contains("no artists"))
            assertTrue(popularity2String.contains("2"))
        }

        @Test
        fun `listArtistsBySelectedPopularity returns all artists that match that popularity when artists of that popularity exist`() {
            //Popularity 1 (1 artist), 2 (none), 3 (1 artist). 4 (2 artists), 5 (1 artist)
            assertEquals(5, populatedArtists!!.numberOfArtists())
            val popularity1String = populatedArtists!!.listArtistsBySelectedPopularity(1).lowercase()
            assertFalse(popularity1String.contains("1 artist"))
            assertFalse(popularity1String.contains("popularity 1"))
            assertFalse(popularity1String.contains("Pablo Picasso"))
            assertFalse(popularity1String.contains("Vincent Van Gough"))
            assertFalse(popularity1String.contains("Leonardo Da Vinci"))
            assertFalse(popularity1String.contains("Rembrandt Van Rijn"))
            assertFalse(popularity1String.contains("Salvador Dali"))


            val popularity4String = populatedArtists!!.listArtistsBySelectedPopularity(4).lowercase(Locale.getDefault())
            assertFalse(popularity4String.contains("2 artist"))
            assertFalse(popularity4String.contains("popularity 4"))
            assertFalse(popularity4String.contains("Pablo Picasso"))
            assertFalse(popularity4String.contains("Vincent Van Gough"))
            assertFalse(popularity4String.contains("Leonardo Da Vinci"))
            assertFalse(popularity4String.contains("Rembrandt Van Rijn"))
            assertFalse(popularity4String.contains("Salvador Dali"))
        }
    }

    @Nested
    inner class DeleteArtists {

        @Test
        fun `deleting an Artist that does not exist, returns null`() {
            assertNull(emptyArtists!!.deleteArtist(0))
            assertNull(populatedArtists!!.deleteArtist(-1))
            assertNull(populatedArtists!!.deleteArtist(5))
        }

        @Test
        fun `deleting an artist that exists delete and returns deleted object`() {
            assertEquals(5, populatedArtists!!.numberOfArtists())
            assertEquals(SalvadorDali, populatedArtists!!.deleteArtist(4))
            assertEquals(4, populatedArtists!!.numberOfArtists())
            assertEquals(PabloPicasso, populatedArtists!!.deleteArtist(0))
            assertEquals(3, populatedArtists!!.numberOfArtists())
        }
    }

    @Nested
    inner class UpdateArtists {
        @Test
        fun `updating an artist that does not exist returns false`(){
            assertFalse(populatedArtists!!.updateArtist(6, Artist("Leonardo Da Vinci", 67, "Italy", artistMovement = "Renaissance", artistPopularity = 5, true)))
            assertFalse(populatedArtists!!.updateArtist(-1, Artist("Leonardo Da Vinci", 67, "Italy", artistMovement = "Renaissance", artistPopularity = 5, true)))
            assertFalse(emptyArtists!!.updateArtist(0, Artist("Leonardo Da Vinci", 67, "Italy", artistMovement = "Renaissance", artistPopularity = 5, true)))
        }

        @Test
        fun `updating an artist that exists returns true and updates`() {
            //check artist 5 exists and check the contents
            assertEquals(SalvadorDali, populatedArtists!!.findArtist(4))
            assertEquals("Salvador Dali", populatedArtists!!.findArtist(4)!!.artistName)
            assertEquals(5, populatedArtists!!.findArtist(4)!!.artistPopularity)
            assertEquals("Spain", populatedArtists!!.findArtist(4)!!.artistCountry)

            //update artist 5 with new information and ensure contents updated successfully
            assertTrue(populatedArtists!!.updateArtist(4, Artist(artistName="Salvador Dali", artistAge=84, artistCountry="Spain", artistMovement="Surrealism", artistPopularity=5, isArtistDeceased=true)))
            assertEquals("Salvador Dali", populatedArtists!!.findArtist(4)!!.artistName)
            assertEquals(5, populatedArtists!!.findArtist(4)!!.artistPopularity)
            assertEquals("Spain", populatedArtists!!.findArtist(4)!!.artistCountry)
        }
    }
}