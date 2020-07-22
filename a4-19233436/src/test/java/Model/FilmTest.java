package Model;

import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of Film.
 */
public class FilmTest extends TestCase {
    private Film film1;
    private Film film2;
    private Person director;
    private Film film3;

    /**Scenario: Three Film instances are created, both assign the title "a" but different ids.
     * The testEqualsOfMedia() is used to test whether the overridden equals() hashcode() in
     * the super class 'Media' of 'Film' and 'TVSeries' work or not.
     * If the equals() and hashcode() work successfully, the film1.equals(film2) should be true.
     * The testCompareTo() is used to test whether the overridden compareTo in 'Media' class works
     * or not.
     * If it works the film1.compareTo(film3)<0 should be true which means the Film instance
     * which has a alphabetically small name is smaller than the Film instance with a alphabetically
     * bigger name.
     *
     * Then is the getters test if the instance of the Film is constructed successfully, then if the getters
     * can get the corresponding data, it means they work successfully.
     *
     * This testClass test the methods in Media and Film. So in TVSeries testing, I won't repeat
     * the test of Media part.
     */
    @BeforeClass
    public void setUp(){
        //create new Film instance
        film1 = new Film();
        director =new Person();
        film1.setTitle("a");
        film1.setFid(2);
        director.setName("director");
        film1.setDirector(director);

        film2 = new Film();
        film2.setTitle("a");
        film2.setFid(3);
        film2.setDirector(director);

        film3 = new Film();
        film3.setTitle("c");
        film3.setFid(1);
        film3.setDirector(director);

    }

    @Test
    public void testEqualsOfMedia(){
        Assert.assertEquals(film1,film2);
    }

    @Test
    public void testCompareTo(){
        Assert.assertTrue(film1.compareTo(film3)<0);
    }
    //test the getters
    @Test
    public void testGetFid() {
        Assert.assertEquals(film1.getFid(),2);
    }
    @Test
    public void testGetDirector() {
        Assert.assertEquals(film1.getDirector(),director);
    }
}//end class