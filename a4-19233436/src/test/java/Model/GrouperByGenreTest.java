package Model;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.IOException;

/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test Class of GrouperByGenreTest.
 */
public class GrouperByGenreTest extends TestCase {
    /**
     * Scenario: Unit test and integration test (Integration test of Container,GrouperByGenre,GenreSet
     * Film,MediaSet)
     * Read the test.json file into the container, and get the mediaSet whose data is
     * sorted then and the genreSet according to which the mediaSet's data is sorted.
     * Then create a Genre instance 'genreTest' whose genre (name) is the same as the alphabetically biggest
     * Genre instance from the JSON file.
     * As the GrouperByGenreTest implementing singleton pattern, the getGrouperByGenre() method
     * is tested by testGetGrouperByGenre(). If the reference of Grouper in this case is not null,
     * it means the getGrouperByGenre() works successfully.
     * The sort() method is the method from Interface Grouper which returns TreeMap<Comparable,TreeSet<Media>>
     * In testSort() method, after sorting the mediaSet, the first Key of the TreeMap should be the Genre instance
     * whose genre (name) is "ASci-Fi". If the Key and the genreTest are the same, it means the sort() method
     * works successfully. This is also a test of the Genre class' equals() and hashcode() methods,
     * where only if two Genre have the same genre('name'),the two instances are equal to each other.
     *
     * The getSortedMedia() method is the method from InterfaceGrouper as well. In the testGetSortedMedia() method
     * similar as the testSort() method, if the first Key is the genre ( name ) of the genreTest, it means the method
     * worked successfully.
     * As the getSortedMedia() returns TreeMap<Comparable, TreeMap<String, String>> which can be used directly by
     * View part.
     */
    Grouper grouper;
    GenreSet genreSet;
    Container container;
    Genre genreTest;
    MediaSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in the file
        Model model = Model.getModel();
        model.init("test.json");
        container =model.getContainer();
        mediaSet = MediaSet.getMediaSet(container);
        //get the grouper singleton
        grouper = GrouperByGenre.getGrouperByGenre();
        //generate the genreSet
        genreSet = GenreSet.getGenreSet(container);

        //create a new Genre instance
        genreTest =new Genre();
        genreTest.setGenre("ASci-Fi");
    }

    @Test
    public void testGetGrouperByGenre() {
        Assert.assertNotNull(grouper);//succeed if the grouper reference is not null
    }

    @Test
    public void testSort() {
        //the new genre object whose genreName is " ASci-Fi" should be the first key in the sorted catalogue
        Assert.assertEquals(grouper.sort(mediaSet,genreSet).firstKey(), genreTest);
    }
    @Test
    public void testGetSortedMedia(){
        //similar as the testSort() method.
        Assert.assertEquals(grouper.getSortedMedia(mediaSet,genreSet).firstKey(), genreTest.getGenre());
    }
}//end class