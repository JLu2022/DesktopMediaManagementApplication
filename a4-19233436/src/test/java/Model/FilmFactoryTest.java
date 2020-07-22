package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of FilmFactory.
 */
public class FilmFactoryTest extends TestCase {

    /**
     * Scenario: This class tests the abstract class MediaFactory and the concrete class FilmFactory.
     * testGetFilmFactory() tests whether get the singleton of FilmFactory successfully. If the filmFactory
     *      is not null, the singleton is successfully gotten.
     * testCreateMedia() create a Film with the filmFactory and compare the property value gotten from the created
     *      Film instance with the value. If the values are the same, it means the Media instance is created successfully.
     */
    Film film;
    Genre genre;
    Person person;
    FilmFactory filmFactory;
    @BeforeClass
    public void setUp(){
        //get the singleton of the FilmFactory
         filmFactory =FilmFactory.getFilmFactory();

        /*create the elements to construct a new Film*/
        List<Genre> genreList = new ArrayList<>();
        List<Person> people = new ArrayList<>();

        genre = new Genre();
        genre.setGid(1);
        genre.setGenre("testGenre");

        person = new Person();
        person.setPid(1);
        person.setName("testPerson");

        genreList.add(genre);
        people.add(person);

        //create the instance of Film
        film = filmFactory.createMedia(1,"testTitle","testDescription",1000,
                genreList,person,people);
    }
    @Test
    public void testGetFilmFactory(){
        Assert.assertNotNull(filmFactory);//should not be null if successfully get
    }
    @Test
    public void testCreateMedia() {
        //test the filmFactory's create method to see if there is any disorder of the params
        Assert.assertEquals(film.getFid(),1);
        Assert.assertEquals(film.getTitle(),"testTitle");
        Assert.assertEquals(film.getDescription(),"testDescription");
        Assert.assertEquals(film.getYear(),1000);
        Assert.assertTrue(film.getGenre().contains(genre));
        Assert.assertEquals(film.getGenre().size(),1);
        Assert.assertEquals(film.getDirector(),person);
        Assert.assertEquals(film.getCast().size(),1);
        Assert.assertTrue(film.getCast().contains(person));
    }
}//end class