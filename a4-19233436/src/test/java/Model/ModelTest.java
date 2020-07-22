package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

/**
 * @author: Junlin Lu
 * @date: 20/04/2020
 * @version: 1.0.0
 * @description: The test class of Model
 */
public class ModelTest extends TestCase {

    /**
     * Scenario: Unit Test and Integration test
     * Get the Model singleton by the method getModel(), use init() method to initialize the
     * Model singleton to read the test.json. Get the container by the Model's getContainer() method, and
     * initialize the yearSet,genreSet,peopleSet,profileSet and mediaSet with the container.
     * Create the param Strings for the method addMedia() to add the data to the yearSet,genreSet,peopleSet,profileSet
     * and mediaSet.
     * Also this is the test class which do integration test for Model, MediaSet, YearSet, GenreSet, PeopleSet,
     * ProfileSet, Container, the MediaInfoExtractorByProfile and the MediaInfoExtractorByTitle ( these two are tested in
     * the methods testGetMediaInfoByProfile() and testGetMediaInfoByTitle())
     *
     * testGetModel() method tests the getModel() method. The singleton is successfully gotten
     * if model is not null.
     * testAddMedia() the so-called Media instance has been added in the setUp() method.
     *      if the Media is added successfully, the size of the mediaSet should be 3
     *      if the Media is added successfully, the size of the yearSet should be 3
     *      if the Media is added successfully, the size of the genreSet should be 5
     *      if the Media is added successfully, the size of the peopleSet should be 5
     * testGetProfile() method create a new Profile with the same name of an element in the profileSet
     *      get the profiles in the memory by the method getCatalogue(). If the new Profile instance which is
     *      the instance equal to some element of the profile catalogue, this method runs successfully because the
     *      profiles are gotten.
     * testGetListedMediaByYear() the first key should be 1992 if the media is listed by year
     * testGetListedMediaByGenre() the first key should be the same Genre instance created in this method
     *      if the media is listed by Genre.
     * testGetMediaInfoByProfile() get the media info from by profile, for the profile 'Mewtwo' the first element of the
     *      returned List should be the title of the media 'The Platform'
     * testGetMediaInfoByTitle() get the media info by title, for the title 'titleTest' the second element of the returned
     *      List should be the description 'descTest'.
     * testGetContainer() container should not be null if the method works
     */
    Model model;
    MediaSet mediaSet;
    YearSet yearSet;
    GenreSet genreSet;
    PeopleSet peopleSet;
    ProfileSet profileSet;
    Container container;
    String title = "titleTest";
    String description= "descTest";
    String yearString= "1992";
    String genreString= "genre1,genre2,genre3";
    String personString ="personTest";
    String castString ="cast1,cast2,cast3";
    boolean tvSeries = false;

    @BeforeClass
    public void setUp() throws IOException, InputFormatException {
        model= Model.getModel();
        model.init("test.json");
        container=model.getContainer();
        mediaSet=MediaSet.getMediaSet(container);
        yearSet=YearSet.getYearSet(container);
        genreSet = GenreSet.getGenreSet(container);
        peopleSet=PeopleSet.getPeopleSet(container);
        profileSet=ProfileSet.getProfileSet(container);
        model.addMedia(title,description,yearString,genreString,personString,castString, false);
    }
    @Test
    public void testGetModel() {
        Assert.assertNotNull(model);
    }
    @Test
    public void testAddMedia() {
        //if the Media is added successfully, the size of the mediaSet should be 3
        Assert.assertEquals(mediaSet.getCatalogue().size(),3);
        //if the Media is added successfully, the size of the yearSet should be 3
        Assert.assertEquals(yearSet.getCatalogue().size(),3);
        //if the Media is added successfully, the size of the genreSet should be 5
        Assert.assertEquals(genreSet.getCatalogue().size(),5);
        //if the Media is added successfully, the size of the peopleSet should be 5
        Assert.assertEquals(peopleSet.getCatalogue().size(),5);
    }
    @Test
    public void testGetProfile() {
        Profile profile = new Profile();
        profile.setName("Mewtwo");
        //the newly added Profile should be in the profileSet's catalogue
        Assert.assertTrue(profileSet.getCatalogue().contains(profile));
    }
    @Test
    public void testGetListedMediaByYear() {
        //the first key should be 1992 if the media is listed by year
        Assert.assertEquals(model.getListedMediaByYear().firstKey(),1992);
    }
    @Test
    public void testGetListedMediaByGenre() {
        Genre genre = new Genre();
        genre.setGenre("ASci-Fi");
        //the first key should be the same Genre instance created in this method
        // if the media is listed by Genre
        Assert.assertEquals(model.getListedMediaByGenre().firstKey(),genre);
    }
    @Test
    public void testGetMediaInfoByProfile() {
        //get the media info from by profile, for the profile 'Mewtwo' the first element of the
        //returned List should be the title of the media 'The Platform'
        Assert.assertEquals(model.getMediaInfoByProfile("Mewtwo").get(0),"The Platform");
    }
    @Test
    public void testGetMediaInfoByTitle() {
        //get the media info by title, for the title 'titleTest' the second element of the returned
        //List should be the description 'descTest'.
        Assert.assertEquals(model.getMediaInfoByTitle("titleTest").get(1),"descTest");
    }
    @Test
    public void testGetContainer() {
        //this should not be null if the method works
        Assert.assertNotNull(container);
    }
}//end class