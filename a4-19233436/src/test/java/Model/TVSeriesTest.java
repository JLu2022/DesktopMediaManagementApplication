package Model;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author: Junlin Lu
 * @date: 13/04/2020
 * @version: 1.0.0
 * @description: This is the test class of TVSeries.
 */
public class TVSeriesTest extends TestCase {
    /**
     * Scenarios: Create a tvSeries and use the setters to set properties. And the test methods
     * test the getters, if it is correct then it will return the same property value as set.
     *
     */
    TVSeries tvSeries;
    Person creator;
    @BeforeClass
    public void setUp(){
        //create a new TVSeries instance
        tvSeries = new TVSeries();
        tvSeries.setTitle("a");
        tvSeries.setTid(1);
        creator = new Person();
        creator.setName("creator");
        tvSeries.setCreator(creator);
    }

    //test of two getters of TVSeries class
    @Test
    public void testGetTid() {
        Assert.assertEquals(tvSeries.getTid(),1);
    }
    @Test
    public void testGetCreator() {
        Assert.assertEquals(tvSeries.getCreator().getName(),"creator");
    }
}//end class