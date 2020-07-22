package Model;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: The Film class extended the Media class
 */

public class Film extends Media{
    private int fid;
    private Person director;

    //default constructor used by Jackson
    public Film(){}

    /**
     * this constructor is used when creating some new Film instance, the lastFID of the media set will
     * be passed in and increment by 1 to make a new FID for the newly created Film instance.
     *
     * @param fid the last fid in the media set
     * @param title title of the film
     * @param year the year when the film was made
     * @param genre the genre List of the film
     * @param director the director of the film which is an instance of Person
     * @param cast the cast List of the film
     * @param description the description of the film
     */
    public Film(int fid, String title, int year, List<Genre> genre, Person director, List<Person> cast, String description){
        super(title, year, genre, description, cast);
        this.fid = fid;
        this.director = director;
    }

    /*Getters and setters*/
    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Person getDirector() {
        return director;
    }

    public void setDirector(Person director) {
        this.director = director;
    }
    /*End of getters and setters*/

    @Override
    public String toString() {
        return "Film \n" +
                "director=" + director+"\n" +super.toString();
    }

}//end class
