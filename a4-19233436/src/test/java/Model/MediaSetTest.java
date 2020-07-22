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
public class MediaSetTest extends TestCase {

    /**
     * Scenario: This test class tests the abstract class DoubleIDDataSet, DataSet, the Interface Addable,
     * and the concrete class MediaSet.
     * It firstly reads in the test.json file to the container and create an Film instance film1 and TVSeries instance
     * tvSeries1 for the later testing of method addNewData() and pass the container MediaSet's
     * getMediaSet method to get the MediaSet singleton.
     * testGetCatalogue() if it is not null the method getCatalogue does get the protected catalogue variable from the DataSet class
     * testGetMediaSet() if the mediaSet reference is not null, that means getting the singleton successfully.
     * testInitializeLastFID() test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
     *      if the id is initialized successfully the last FID should be 1
     * testInitializeLastTID() test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
     *      if the id is initialized successfully the last TID should be 1
     * testAddNewData() test the method in Addable interface and the concrete implementation in MediaSet
     *      firstly add two Media objects into the mediaSet,the two Media objects should now be in the catalogue of the mediaSet
     *      if the MediaSet instance contains the two Media objects, it means the addNewData() method run successfully
     */
    //the fields
    Film film1;
    TVSeries tvSeries1;
    MediaSet mediaSet;
    Container container;

    @BeforeClass
    public void setUp() throws IOException {
        //read file
        ObjectMapper objectMapper = new ObjectMapper();
        container =objectMapper.readValue(new File("test.json"),Container.class);

        film1 = new Film();
        film1.setTitle("a");
        film1.setFid(4);
        tvSeries1 = new TVSeries();
        tvSeries1.setTitle("b");
        tvSeries1.setTid(3);
        //get the MediaSet singleton
        mediaSet =MediaSet.getMediaSet(container);
    }
    //if it is not null the method getCatalogue does get the protected catalogue variable from the DataSet class
    @Test
    public void testGetCatalogue(){
        Assert.assertNotNull(mediaSet.getCatalogue());
    }
    //test the singleton of the MediaSet
    @Test
    public void testGetMediaSet() {
        Assert.assertNotNull(mediaSet);
    }
    //test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
    @Test
    public void testInitializeLastFID() {
        //if the id is initialized successfully the last FID should be 1
        Assert.assertEquals(mediaSet.getLastFID(),(Integer) 1);
    }
    //test the abstract method in DoubleIDDataSet class and the concrete implementation in MediaSet class
    @Test
    public void testInitializeLastTID() {
        //if the id is initialized successfully the last TID should be 1
        Assert.assertEquals(mediaSet.getLastTID(),(Integer) 1);
    }
    //test the method in Addable interface and the concrete implementation in MediaSet
    @Test
    public void testAddNewData() {
        //add two Media objects into the mediaSet
        mediaSet.addNewData(film1);
        mediaSet.addNewData(tvSeries1);
        //the two Media objects should now be in the catalogue of the mediaSet
        Assert.assertTrue(mediaSet.getCatalogue().contains(film1));
        Assert.assertTrue(mediaSet.getCatalogue().contains(tvSeries1));
    }
}//end class