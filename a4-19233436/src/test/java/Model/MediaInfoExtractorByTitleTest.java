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
public class MediaInfoExtractorByTitleTest extends TestCase {

    /**
     * Scenario: Unit test and integration test of MediaInfoExtractorByProfile,Container,DataSet
     * This class tests the interface MediaInfoExtractor and the concrete class MediaInfoExtractorByTitle
     *
     * testGetMediaInfoExtractorByProfile() test the singleton getter.
     *      If the mediaInfoExtractor is not null, the singleton is gotten successfully.
     * testGetMediaInfo() test the getMediaInfo() method.
     *      If succeed, the third element of the returned List should be "2008"
     */
    MediaInfoExtractor mediaInfoExtractor;
    Container container;
    DataSet mediaSet;
    @BeforeClass
    public void setUp() throws IOException {
        //read in a test.json file and check the result of the read in objects
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);
        mediaInfoExtractor = MediaInfoExtractorByTitle.getMediaInfoExtractorByTitle();
        mediaSet = MediaSet.getMediaSet(container);
    }
    @Test
    public void testGetMediaInfoExtractorByProfile() {
        Assert.assertNotNull(mediaInfoExtractor);
    }

    @Test
    public void testGetMediaInfo() {
        //If succeed, the first element of the returned List should be the title of the Media instance, "The Platform"
        Assert.assertEquals(mediaInfoExtractor.getMediaInfo("Breaking Bad",mediaSet).get(2),"2008");
    }
}