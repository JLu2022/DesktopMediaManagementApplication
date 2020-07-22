package Model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Objects;

/**
 * @author: Junlin Lu
 * @date: 07/04/2020
 * @version: 1.0.0
 * @description: Genre class which implements Comparable<Genre>, and this class use gid as ID.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "gid",scope = Genre.class)
public class Genre implements Comparable<Genre>{
    private int gid;
    private String genre;

    //the default constructor for Jackson
    public Genre(){}

    /*Getters and setters*/
    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public String getGenre() {//shallow method actually
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
    /*End of getters and setters*/

    @Override
    public String toString() {
        return genre;
    }

    //Override the method compareTo(Genre o) inherit from Comparable<Genre>
    //and Genre instances are sorted by their String genre fields, following the sorting rule of String
    @Override
    public int compareTo(Genre o) {
        return this.getGenre().compareTo(o.getGenre());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre1 = (Genre) o;
        return genre.equals(genre1.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre);
    }
}
