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
 * @date: 21/04/2020
 * @version: 1.0.0
 * @description:
 */
public class MediaInfoExtractorByProfileTest extends TestCase {

    /**
     * Scenario: Unit test and integration test of MediaInfoExtractorByProfile,Container,DataSet
     * This class tests the interface MediaInfoExtractor and the concrete class MediaInfoExtractorByProfile
     *
     * testGetMediaInfoExtractorByProfile() test the singleton getter.
     *      If the mediaInfoExtractor is not null, the singleton is gotten successfully.
     * testGetMediaInfo() test the getMediaInfo() method.
     *      If succeed, the first element of the returned List should be "The Platform,2019,Horror|"
     */
    MediaInfoExtractor mediaInfoExtractor;
    Container container;
    DataSet profileSet;
    DataSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in a test.json file and check the result of the read in objects
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        mediaInfoExtractor = MediaInfoExtractorByProfile.getMediaInfoExtractorByProfile();
        profileSet = ProfileSet.getProfileSet(container);
        mediaSet = MediaSet.getMediaSet(container);
    }

    @Test
    public void testGetMediaInfoExtractorByProfile() {
        Assert.assertNotNull(mediaInfoExtractor);
    }

    @Test
    public void testGetMediaInfo() {
        //If succeed, the first element of the returned List should be the title of the Media instance, "The Platform"
        Assert.assertEquals(mediaInfoExtractor.getMediaInfo("Mewtwo",mediaSet,profileSet).get(0),"The Platform,2019,Horror|");
    }
}