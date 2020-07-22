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
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description:
 */
public class PeopleSetTest extends TestCase {
    /**
     * Scenario: This test class tests the abstract class SoloIDDataSet and the concrete class PeopleSet
     * It firstly reads in the test.json file to the container and get the singleton of PeopleSet by passing
     * in container as param.
     * testGetPeopleSet() is the method tests the getPeopleSet() method, if the peopleSet is not null, it means the
     *      singleton is gotten successfully.
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable, if the catalogue in peopleSet has 3
     *      elements, it means that all three Person instances are successfully added.
     * testInitializeID() tests the abstract method initializeID() and abstract method getLastId() in SoloIDDataSet class
     * and the concrete implementation in the PeopleSet class.
     *       The id is initialized successfully if the last id is 1.
     */
    Person person1;
    Person person2;
    Person person3;
    Container container;
    PeopleSet peopleSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        //get the PeopleCatalogue
        peopleSet = PeopleSet.getPeopleSet(container);

        //create three Person instances
        person1 = new Person();
        person2 = new Person();
        person3 = new Person();
        person1.setName("a");
        person2.setName("a");
        person3.setName("b");
    }
    //if the peopleSet is not null, it means the singleton is gotten successfully
    @Test
    public void testGetPeopleSet() {
        Assert.assertNotNull(peopleSet);
    }

    //test the concrete implementation of the method in Addable, if the catalogue in peopleSet has 3
    //elements, it means that all three Person instances are successfully added
    @Test
    public void testAddNewData() {
        peopleSet.addNewData(person1);
        peopleSet.addNewData(person2);
        peopleSet.addNewData(person3);
        //person1 and person3 should be treated as the same person, so even though 3 people were added only 2
        //more new people are now in the peopleSet's catalogue
        Assert.assertEquals(peopleSet.getCatalogue().size(),3);
    }

    //this test the abstract method initializeID() and abstract method getLastId()
    // in SoloIDDataSet class.
    @Test
    public void testInitializeID() {
        //if the id is initialized successfully the last id should be 1
        Assert.assertEquals(peopleSet.getLastId(),(Integer)1);
    }
}//end class