package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: The test class of Person.
 */
public class PersonTest extends TestCase {
    /**
     * Scenario: create three Person instances person1, person2, and person3
     * Use setters set the person1, person2 the same name, and person3 a different name.
     * Then use getters to get the name and id of person1 and compare with the real value. If the values
     * match, it means the setters successfully set the value and the getters successfully get the value.
     *
     * Further more, the method testCompareTo() tests the compareTo() in the Person class, as the Person instances
     * are compared by their name. When comparing person1 and person3, person1 (with the name "a")
     * should be smaller than person3(named "b"). If the 'Assert.assertTrue(person1.compareTo(person3)<0);'
     * successfully worked, it means the 'compareTo' method in Person class works fine.
     *
     * Also, the method testEquals() and testHashCode() are the methods testing the overridden
     * equals() and hashcode() methods. Two Person instances are equal to each other when they have the same
     * genre(name). So with the same name person1 and person2 should be equal to each other.
     * If the testEquals() and testHashCode() work fine, then the equals() and hashcode() in Person class
     * work fine.
     */
    Person person1;
    Person person2;
    Person person3;
    @BeforeClass
    public void setUp(){
        //create 3 instances of Person
        person1 = new Person();
        person2 = new Person();
        person1.setName("a");
        person1.setPid(10);
        person2.setName("a");
        person2.setPid(99);
        person3 = new Person();
        person3.setName("b");
    }
    @Test
    public void testGetPid() {
        Assert.assertEquals(person1.getPid(),10);
    }
    @Test
    public void testGetPerson() {
        Assert.assertEquals(person1.getName(),"a");
    }
    @Test
    public void testTestEquals() {
        //person1 and person2 are two different reference variable but has the same name
        //they are supposed to be the same person
        Assert.assertEquals(person1,person2);
    }
    @Test
    public void testTestHashCode() {
        Assert.assertEquals(person1.hashCode(),person2.hashCode());
    }
    @Test
    public void testCompareTo() {
        //person1 (named 'a') should be smaller than person3 (named 'b') alphabetically because of their names.
       Assert.assertTrue(person1.compareTo(person3)<0);
    }
}//end class