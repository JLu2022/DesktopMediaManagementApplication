package Model;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: The TVSeries class extended the Media class
 */

public class TVSeries extends Media{
    private int tid;
    private Person creator;

    //the default constructor used by Jackson
    public TVSeries(){}

    /**
     * this constructor is used when creating some new TVSeries instance, the lastTID of the media set will
     * be passed in and increment by 1 to make a new TID for the newly created TVSeries instance.
     *
     * @param tid the last tid in the media set
     * @param title title of the TVSeries
     * @param year the year when the TVSeries was made
     * @param genre the genre List of the TVSeries
     * @param creator the creator of the TVSeries which is an instance of Person
     * @param cast the cast List of the TVSeries
     * @param description the description of the TVSeries
     */
    public TVSeries(int tid, String title, int year, List<Genre> genre, Person creator, List<Person> cast, String description){
        super(title, year, genre, description, cast);
        this.tid = tid;
        this.creator = creator;
    }

    /*Getters and setters*/
    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }
    /*End getters and setters*/
    @Override
    public String toString() {
        return "TVSeries \n" +
                "creator=" + creator+"\n" +super.toString();
    }

}//end class
