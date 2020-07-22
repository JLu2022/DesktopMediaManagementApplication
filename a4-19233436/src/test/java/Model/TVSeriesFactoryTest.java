package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: Test class of TVSeries
 */
public class TVSeriesFactoryTest extends TestCase {
    /**
     * Scenario: This class tests the concrete class TVSeriesFactory.
     * testGetTVSeriesFactory() tests whether get the singleton of TVSeriesFactory successfully. If the filmFactory
     *      is not null, the singleton is successfully gotten.
     * testCreateMedia() create a TVSeries with the TVSeriesFactory and compare the property value gotten from the created
     *      TVSeries instance with the value. If the values are the same, it means the Media instance is created successfully.
     */
    TVSeries tvSeries;
    Genre genre;
    Person creator;
    TVSeriesFactory tvSeriesFactory;
    //create a new tvSeries to test the createTVSeries method.
    @BeforeClass
    public void setUp(){
        tvSeriesFactory =TVSeriesFactory.getTvSeriesFactory();
        List<Genre> genreList = new ArrayList<>();
        List<Person> people = new ArrayList<>();

        genre = new Genre();
        genre.setGid(1);
        genre.setGenre("testGenre");

        creator = new Person();
        creator.setPid(1);
        creator.setName("testPerson");

        genreList.add(genre);
        people.add(creator);

        tvSeries = tvSeriesFactory.createMedia(1,"testTitle","testDescription",1000,
                genreList,creator,people);
    }
    @Test
    public void testGetTVSeriesFactory() {
        Assert.assertNotNull(tvSeriesFactory);
    }
    @Test
    public void testCreateMedia() {
        Assert.assertEquals(tvSeries.getTid(),1);
        Assert.assertEquals(tvSeries.getTitle(),"testTitle");
        Assert.assertEquals(tvSeries.getDescription(),"testDescription");
        Assert.assertEquals(tvSeries.getYear(),1000);
        Assert.assertTrue(tvSeries.getGenre().contains(genre));
        Assert.assertEquals(tvSeries.getGenre().size(),1);
        Assert.assertEquals(tvSeries.getCreator(),creator);
        Assert.assertEquals(tvSeries.getCast().size(),1);
        Assert.assertTrue(tvSeries.getCast().contains(creator));
    }
}