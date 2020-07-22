package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: The test class of Genre class.
 */
public class GenreTest extends TestCase {
    /**
     * Scenario: create three Genre instances genre1, genre2, and genre3
     * Use setters set the genre1, genre3 the same genre(name), and genre2 a different genre(name).
     * Then use getters to get the name and id of genre1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Genre class, as the Genre instances
     * are compared by their genre(name). When comparing genre1 and genre2, genre2 (with the name "b_testGenre")
     * should be bigger than genre1(named "a_testGenre"). If the 'Assert.assertTrue(genre1.compareTo(genre2)<0);'
     * successfully worked, it means the 'compareTo' method in Genre class works fine.
     *
     * Also, the method testEquals() and testHashCode() are the methods testing the overridden
     * equals() and hashcode() methods. Two Genre instances are equal to each other when they have the same
     * genre(name). So with the same name genre1 and genre3 should be equal to each other.
     * If the testEquals() and testHashCode() work fine, then the equals() and hashcode() in Genre class
     * work fine.
     */
    Genre genre1;
    Genre genre2;
    Genre genre3;
    @BeforeClass
    public void setUp(){
        //create three Genre instances, genre1 and genre3 have the same name but different ids
        genre1 = new Genre();
        genre1.setGenre("a_testGenre");
        genre1.setGid(1);
        genre2 = new Genre();
        genre2.setGenre("b_testGenre");
        genre2.setGid(2);
        genre3 = new Genre();
        genre3.setGenre("a_testGenre");
        genre3.setGid(3);
    }
    @Test
    public void testGetGid() {
        Assert.assertEquals(genre1.getGid(),1);
    }
    @Test
    public void testGetGenre() {
        Assert.assertEquals(genre1.getGenre(),"a_testGenre");
    }

    @Test
    public void testCompareTo() {
        //genre1 should be bigger than genre2 because of their name alphabetically
        Assert.assertTrue(genre1.compareTo(genre2)<0);
    }

    @Test
    public void testEquals() {
        //as have the same name, genre1 and genre3 should be equal
        Assert.assertTrue(genre1.equals(genre3));
    }

    @Test
    public void testHashCode() {
        //the hashcode of genre1 and genre3 are equal, because of the same name
        Assert.assertTrue(genre1.hashCode() == genre3.hashCode());
    }
}//end class