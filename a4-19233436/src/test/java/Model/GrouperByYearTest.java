package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;


/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of GrouperByYear class.
 */
public class GrouperByYearTest extends TestCase {
    /**
     * Scenario: Unit test and integration test (Integration test of Container,GrouperByYear,YearSet,FilmFactory
     * Film,MediaSet)
     * Read the test.json file into the container, and get the mediaSet whose data is
     * sorted then and the yearSet according to which the mediaSet's data is sorted.
     * Also in this test class a new Film instance is created by the FilmFactory singleton, so this class also tests
     * the FilmFactories 'getFilmFactory' method. This new Film instance was made in the year '1000', which should be
     * the first element after being added to the YearSet.
     * As the GrouperByYear implementing singleton pattern, the getGrouperByYear() method
     * is tested by testGetGrouperByYear(). If the reference of Grouper in this case is not null,
     * it means the getGrouperByYear() works successfully.
     * The sort() method is the method from Interface Grouper which returns TreeMap<Comparable,TreeSet<Media>>
     * In testSort() method, after sorting the mediaSet, the first Key of the TreeMap should be the Integer(year)
     * instance 1000. If the first Key is the same as 1000, it means the sort() method
     * works successfully.
     * The getSortedMedia() method is the method from InterfaceGrouper as well. In the testGetSortedMedia() method
     * similar as the testSort() method. If the First Key which is a  Comparable instance is equal to 1000, it means the
     * method worked successfully.
     * As the getSortedMedia() returns TreeMap<Comparable, TreeMap<String, String>> which can be used directly by
     * View part.
     */
    Container container;
    Grouper grouper;
    YearSet yearSet;
    FilmFactory filmFactory;
    Film film;
    MediaSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        Model model = Model.getModel();
        model.init("test.json");
        container =model.getContainer();
        //get the grouper singleton
        grouper = GrouperByYear.getGrouperByYear();
        //generate the genreSet
        yearSet = YearSet.getYearSet(container);
        filmFactory = FilmFactory.getFilmFactory();
        film = filmFactory.createMedia(12,"Test","testDesc",1000,new ArrayList<Genre>(),new Person(),new ArrayList<Person>());
        mediaSet = MediaSet.getMediaSet(container);
    }
    @Test
    public void testGetGrouperByYear() {
        //should not be null if get the singleton successfully
        Assert.assertNotNull(grouper);
    }
    @Test
    public void testSort() {
        //add the new year to yearSet
        yearSet.addNewData(film.getYear());
        //add the film into the mediaSet
        mediaSet.addNewData(film);
        //if sorted successfully the first key should be 1000 the year of the reference 'film'
        Assert.assertEquals(grouper.sort(mediaSet,yearSet).firstKey(),1000);
    }
    @Test
    public void testGetSortedMedia() {
        //add the new year to yearSet
        yearSet.addNewData(film.getYear());
        //add the film into the mediaSet
        mediaSet.addNewData(film);
        //if sorted successfully the first key should be 1000 the year of the reference 'film'
        Assert.assertEquals(grouper.getSortedMedia(mediaSet,yearSet).firstKey(),1000);
    }
}//end class