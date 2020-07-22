package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.File;

/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of the Container.
 */
public class ContainerTest extends TestCase {
    static Container container;

    /**
     * The following are all the testing methods for getters, if the file is
     * read successfully, the setters are used to initialize the corresponding
     * objects, the getters can get the record in the constructed objects. In
     * the @Test methods I compared the record of the constructed objects
     * directly with the specific content in the test.json. If they match then
     * the read from test.json, setters and getters all worked.
     * @throws Exception
     */
    @BeforeClass
    public void setUp() throws Exception{
        //read in a test.json file and check the result of the read in objects
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
    }

    /**
     *The following are all the testing methods for getters, if the file is read successfully, the getters
     * can get the record in the test.json file.
     */
    @Test
    public void testGetPeople() {
        for(Person p:container.getPeople())
            Assert.assertEquals(p.getName(),"Galder Gaztelu-Urrutia");
    }
    @Test
    public void testGetFilms() {
        for(Film f:container.getFilms())
            Assert.assertEquals(f.getTitle(), "The Platform");

    }
    @Test
    public void testGetGenres() {
        for(Genre g:container.getGenres())
            Assert.assertEquals(g.getGenre(),"Horror");
    }
    @Test
    public void testGetProfiles() {
        for(Profile p:container.getProfiles())
            Assert.assertEquals(p.getName(),"Mewtwo");
    }
    @Test
    public void testGetTvseries() {
        for(TVSeries t:container.getTvseries())
            Assert.assertEquals(t.getTitle(),"Breaking Bad");
    }
    @Test
    public void testGetMedia() {
        for(Media m:container.getMedia()){
            Assert.assertTrue(container.getMedia().contains(m));
        }
    }
}//end class