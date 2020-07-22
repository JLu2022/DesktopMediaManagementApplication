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
 * @date: 20/04/2020
 * @version: 1.0.0
 * @description: The test class of YearSet.
 */
public class YearSetTest extends TestCase {
    /**
     * Scenario: This test class tests the concrete class YearSet
     * It firstly reads in the test.json file to the container and get the singleton of YearSet by passing
     * in container as param.
     * testGetYearSet() is the method tests the getYearSet() method, if the yearSet is not null, it means the
     *      singleton is gotten successfully.
     * testAddNewData() is the method tests the addNewData() method inherits from the Addable interface,
     *      test the concrete implementation of the method in Addable. Add a new Integer 1998 to the yearSet
     *      If the size of the catalogue in YearSet singleton is 3, it means the Integer is added successfully
     */

    Container container;
    YearSet yearSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        yearSet = YearSet.getYearSet(container);
    }
    @Test
    public void testGetYearSet() {
        //test the singleton getter, the reference should not be null
        Assert.assertNotNull(yearSet);
    }
    @Test
    public void testAddNewData() {
        //add a new Integer to the yearSet
        yearSet.addNewData(1988);
        //the size of the yearSet should be 3 now
        Assert.assertEquals(yearSet.getCatalogue().size(),3);
    }
}