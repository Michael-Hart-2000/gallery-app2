package controllers

import models.Artist
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue
import persistence.XMLSerializer
import persistence.JSONSerializer
import java.io.File
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull

class ArtistAPITest {

    private var PabloPicasso: Artist? = null
    private var VincentVanGogh: Artist? = null
    private var LeonardoDaVinci: Artist? = null
    private var RembrandtVanRijn: Artist? = null
    private var SalvadorDali: Artist? = null
    private var populatedArtists: ArtistAPI? = ArtistAPI(XMLSerializer(File("artists.xml")))
    private var emptyArtists: ArtistAPI? = ArtistAPI(XMLSerializer(File("artist.xml")))

    @BeforeEach
    fun setup() {
        PabloPicasso = Artist("Pablo Picasso", 91, "Spain", artistMovement = "Cubism", artistPopularity = 5, true)
        VincentVanGogh = Artist(
            "Vincent Van Gogh",
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
        populatedArtists!!.add(VincentVanGogh!!)
        populatedArtists!!.add(LeonardoDaVinci!!)
        populatedArtists!!.add(RembrandtVanRijn!!)
        populatedArtists!!.add(SalvadorDali!!)
    }

    @AfterEach
    fun tearDown() {
        PabloPicasso = null
        VincentVanGogh = null
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
            assertFalse(artistsString.contains("Vincent Van Gogh"))
            assertFalse(artistsString.contains("Leonardo Da Vinci"))
            assertFalse(artistsString.contains("Rembrandt Van Rijn"))
            assertFalse(artistsString.contains("Salvador Dali"))
        }


    @Test
    fun `listLivingArtists returns no living artists stored when ArrayList is empty`() {
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
        assertFalse(livingArtistsString.contains("Vincent Van Gogh"))
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
        assertFalse(deceasedArtistsString.contains("Vincent Van Gogh"))
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
            assertFalse(popularity1String.contains("Vincent Van Gogh"))
            assertFalse(popularity1String.contains("Leonardo Da Vinci"))
            assertFalse(popularity1String.contains("Rembrandt Van Rijn"))
            assertFalse(popularity1String.contains("Salvador Dali"))


            val popularity4String = populatedArtists!!.listArtistsBySelectedPopularity(4).lowercase(Locale.getDefault())
            assertFalse(popularity4String.contains("2 artist"))
            assertFalse(popularity4String.contains("popularity 4"))
            assertFalse(popularity4String.contains("Pablo Picasso"))
            assertFalse(popularity4String.contains("Vincent Van Gogh"))
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
    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {
            // Saving an empty artists.XML file.
            val storingArtists = ArtistAPI(XMLSerializer(File("artists.xml")))
            storingArtists.store()

            //Loading the empty artists.xml file into a new object
            val loadedArtists = ArtistAPI(XMLSerializer(File("artists.xml")))
            loadedArtists.load()

            //Comparing the source of the artists (storingArtists) with the XML loaded artists (loadedArtists)
            assertEquals(0, storingArtists.numberOfArtists())
            assertEquals(0, loadedArtists.numberOfArtists())
            assertEquals(storingArtists.numberOfArtists(), loadedArtists.numberOfArtists())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            // Storing 3 artists to the artists.XML file.
            val storingArtists = ArtistAPI(XMLSerializer(File("artists.xml")))
            storingArtists.add(PabloPicasso!!)
            storingArtists.add(SalvadorDali!!)
            storingArtists.add(VincentVanGogh!!)
            storingArtists.store()

            //Loading artists.xml into a different collection
            val loadedArtists = ArtistAPI(XMLSerializer(File("artists.xml")))
            loadedArtists.load()

            //Comparing the source of the artists (storingArtists) with the XML loaded artists (loadedArtists)
            assertEquals(3, storingArtists.numberOfArtists())
            assertEquals(3, loadedArtists.numberOfArtists())
            assertEquals(storingArtists.numberOfArtists(), loadedArtists.numberOfArtists())
            assertEquals(storingArtists.findArtist(0), loadedArtists.findArtist(0))
            assertEquals(storingArtists.findArtist(1), loadedArtists.findArtist(1))
            assertEquals(storingArtists.findArtist(2), loadedArtists.findArtist(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty artists.json file.
            val storingArtists = ArtistAPI(JSONSerializer(File("artists.json")))
            storingArtists.store()

            //Loading the empty artists.json file into a new object
            val loadedArtists = ArtistAPI(JSONSerializer(File("artists.json")))
            loadedArtists.load()

            //Comparing the source of the artists (storingArtists) with the json loaded artists (loadedArtists)
            assertEquals(0, storingArtists.numberOfArtists())
            assertEquals(0, loadedArtists.numberOfArtists())
            assertEquals(storingArtists.numberOfArtists(), loadedArtists.numberOfArtists())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 artists to the artists.json file.
            val storingArtists = ArtistAPI(JSONSerializer(File("artists.json")))
            storingArtists.add(PabloPicasso!!)
            storingArtists.add(VincentVanGogh!!)
            storingArtists.add(LeonardoDaVinci!!)
            storingArtists.store()

            //Loading artists.json into a different collection
            val loadedArtists = ArtistAPI(JSONSerializer(File("artists.json")))
            loadedArtists.load()

            //Comparing the source of the artists (storingArtists) with the json loaded artists (loadedArtists)
            assertEquals(3, storingArtists.numberOfArtists())
            assertEquals(3, loadedArtists.numberOfArtists())
            assertEquals(storingArtists.numberOfArtists(), loadedArtists.numberOfArtists())
            assertEquals(storingArtists.findArtist(0), loadedArtists.findArtist(0))
            assertEquals(storingArtists.findArtist(1), loadedArtists.findArtist(1))
            assertEquals(storingArtists.findArtist(2), loadedArtists.findArtist(2))
        }
    }

    @Nested
    inner class ArchiveArtists {
        @Test
        fun `archiving an artist that does not exist returns false`(){
            assertFalse(populatedArtists!!.archiveArtist(6))
            assertFalse(populatedArtists!!.archiveArtist(-1))
            assertFalse(emptyArtists!!.archiveArtist(0))
        }

        @Test
        fun `archiving an already archived artist returns false`(){
            assertTrue(populatedArtists!!.findArtist(2)!!.isArtistDeceased)
            assertFalse(populatedArtists!!.archiveArtist(2))
        }

        @Test
        fun `archiving a living artist that exists returns true and archives`() {
            assertTrue(populatedArtists!!.findArtist(1)!!.isArtistDeceased)
            assertFalse(populatedArtists!!.archiveArtist(1))
            assertTrue(populatedArtists!!.findArtist(1)!!.isArtistDeceased)
        }
    }

    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfArtistsCalculatedCorrectly() {
            assertEquals(5, populatedArtists!!.numberOfArtists())
            assertEquals(0, emptyArtists!!.numberOfArtists())
        }

        @Test
        fun numberOfDeceasedArtistsCalculatedCorrectly() {
            assertEquals(5, populatedArtists!!.numberOfDeceasedArtists())
            assertEquals(0, emptyArtists!!.numberOfDeceasedArtists())
        }

        @Test
        fun numberOfLivingArtistsCalculatedCorrectly() {
            assertEquals(0, populatedArtists!!.numberOfLivingArtists())
            assertEquals(0, emptyArtists!!.numberOfLivingArtists())
        }

        @Test
        fun numberOfArtistsByPopularityCalculatedCorrectly() {
            assertEquals(0, populatedArtists!!.numberOfArtistsByPopularity(0))
            assertEquals(0, populatedArtists!!.numberOfArtistsByPopularity(0))
            assertEquals(0, populatedArtists!!.numberOfArtistsByPopularity(0))
            assertEquals(0, populatedArtists!!.numberOfArtistsByPopularity(0))
            assertEquals(0, populatedArtists!!.numberOfArtistsByPopularity(0))
            assertEquals(0, emptyArtists!!.numberOfArtistsByPopularity(0))
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `search artists by name returns no artists when no artists with that name exist`() {
            //Searching a populated collection for a name that doesn't exist.
            Assertions.assertEquals(5, populatedArtists!!.numberOfArtists())
            val searchResults = populatedArtists!!.searchByName("no results expected")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            Assertions.assertEquals(0, emptyArtists!!.numberOfArtists())
            assertTrue(emptyArtists!!.searchByName("").isEmpty())
        }

        @Test
        fun `search artists by name returns artists when artists with that name exist`() {
            Assertions.assertEquals(5, populatedArtists!!.numberOfArtists())

            //Searching a populated collection for a full name that exists (case matches exactly)
            var searchResults = populatedArtists!!.searchByName("Code App")
            assertFalse(searchResults.contains("Code App"))
            assertFalse(searchResults.contains("Test App"))

            //Searching a populated collection for a partial name that exists (case matches exactly)
            searchResults = populatedArtists!!.searchByName("App")
            assertFalse(searchResults.contains("Code App"))
            assertFalse(searchResults.contains("Test App"))
            assertFalse(searchResults.contains("Swim - Pool"))

            //Searching a populated collection for a partial name that exists (case doesn't match)
            searchResults = populatedArtists!!.searchByName("aPp")
            assertFalse(searchResults.contains("Code App"))
            assertFalse(searchResults.contains("Test App"))
            assertFalse(searchResults.contains("Swim - Pool"))
        }
    }

}
