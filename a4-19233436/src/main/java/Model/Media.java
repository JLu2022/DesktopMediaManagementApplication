package Model;
import java.util.List;
import java.util.Objects;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: This is an abstract class Media, the parent class of Film and TVSeries, implementing Comparable<Media>
 *
 */
public abstract class Media implements Comparable<Media> {
    private String title;
    private int year;
    private List<Genre> genre;
    private String description;
    private List<Person> cast;

    //default constructor
    public Media(){}

    //constructor
    public Media(String title, int year, List<Genre> genre, String description, List<Person> cast) {
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.description = description;
        this.cast = cast;
    }

    /*Getters and setters*/
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public List<Genre> getGenre() {
        return genre;
    }

    public void setGenres(List<Genre> genres) {
        this.genre = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getCast() {
        return cast;
    }

    public void setCast(List<Person> cast) {
        this.cast = cast;
    }
    /*End of getters and setters*/

    @Override
    public String toString() {
        return
                "title=" + title + '\n' +
                "year=" + year +'\n'+
                "genre=" + genre +'\n'+
                "description='" + description + '\n' +
                "cast=" + cast+'\n';
    }

    /**
     * Implement Comparable, this method is override to order the Media by title.
     * @param o Media instance
     * @return int represents the result of comparing
     */
    @Override
    public int compareTo(Media o) {
        return this.getTitle().compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Media media = (Media) o;
        return Objects.equals(title, media.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}//end class
