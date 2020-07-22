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
public class ProfileSetTest extends TestCase {
    /**
     * Scenario: This test class tests the concrete class ProfileSet
     * It firstly reads in the test.json file to the container and get the singleton of ProfileSet by passing
     * in container as param.
     * testGetProfileSet() is the method tests the getYearSet() method, if the yearSet is not null, it means the
     *      singleton is gotten successfully.
     */
    Container container;
    ProfileSet profileSet;
    @BeforeClass
    public void setUp() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        profileSet = ProfileSet.getProfileSet(container);
    }
    @Test
    public void testGetProfileSet() {
        Assert.assertNotNull(profileSet);
    }
}