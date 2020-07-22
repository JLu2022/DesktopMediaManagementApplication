package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of ProfileTest
 */
public class ProfileTest extends TestCase {
    /**
     * Scenario: create two Profile instances profile1, profile2
     * Use setters set the profile1, profile2 different names.
     * Then use getters to get the perferredGenre and name of profile1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Profile class, as the Profile instances
     * are compared by their name. When comparing profile1 and profile2, profile1 (with the name "b")
     * should be bigger than profile2(named "a"). If the 'Assert.assertTrue(profile1.compareTo(profile2)>0);'
     * successfully worked, it means the 'compareTo' method in Profile class works fine.
     *
     */
    Profile profile1;
    Profile profile2;
    Genre genre;
    @BeforeClass
    public void setUp(){
        //create two profile
        profile1=new Profile();
        profile2=new Profile();
        profile1.setName("b");
        profile2.setName("a");
        genre = new Genre();
        profile1.setPreferredGenre(genre);
    }
    @Test
    public void testGetPrefferedGenre() {
        Assert.assertEquals(profile1.getPreferredGenre(),genre);
    }
    @Test
    public void testGetProfile() {
        Assert.assertEquals(profile1.getName(),"b");
    }
    @Test
    public void testCompareTo() {
        //The profile1 named 'b', should be bigger than the profile2 named 'a'
        Assert.assertTrue(profile1.compareTo(profile2)>0);
    }
}//end class