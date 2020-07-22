package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: The test class of GenreSet.
 */
public class GenreSetTest extends TestCase {
    /**
     * Scenario: This test class tests the abstract class SoloIDDataSet and the concrete class GenreSet
     * It firstly reads in the test.json file to the container and get the singleton of GenreSet by passing
     * in container as param.
     * testGetGenreSet() is the method tests the getGenreSet() method, if the genreSet is not null, it means the
     *      singleton is gotten successfully.
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable.Add the two Genre instances into the genreSet
     *      genre1 is a new Genre object while genre2 is a Genre object has the same Genre Name in the catalogue of genreSet.
     *      The catalogue of genreSet should contain genre1 if it is added successfully.
     *      While genre2, even though is added as well, but the genreSet will not be refreshed
     * testInitializeID() tests the abstract method initializeID() and abstract method getLastId() in SoloIDDataSet class
     * and the concrete implementation in the GenreSet class.
     *       if the id is initialized successfully the last id should be 2.
     */
    //the fields
    Container container;
    Genre genre1;
    Genre genre2;
    GenreSet genreSet;

    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);

        //get the GenreCatalogue
        genreSet= GenreSet.getGenreSet(container);

        //create new Genre objects
        genre1 = new Genre();
        genre1.setGenre("testGenre1");

        genre2 = new Genre();
        genre2.setGenre("ASci-Fi");
    }

    @Test
    public void testGetGenreSet() {
        Assert.assertNotNull(genreSet);
    }//end method

    @Test
    public void testAddNewData() {
        //add the two Genre instances into the genreSet genre1 is a new Genre object while genre2 is a Genre
        //object has the same Genre Name in the catalogue of genreSet.
        genreSet.addNewData(genre1);
        genreSet.addNewData(genre2);

        //the catalogue of genreSet should contain genre1 if it is added successfully.
        Assert.assertTrue(genreSet.getCatalogue().contains(genre1));
        //while genre2, even though is added as well, but the genreSet will not be refreshed
        Assert.assertEquals(genreSet.getCatalogue().size(),3);
    }//end method
    @Test
    public void testInitializeID() {
        //if the id is initialized successfully the last id should be 2
        Assert.assertEquals(genreSet.getLastId(),(Integer) 2);
    }//end method
}//end class