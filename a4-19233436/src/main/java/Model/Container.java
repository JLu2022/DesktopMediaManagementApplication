package Model;
import java.util.*;

/**
 * @author: Junlin Lu
 * @date: 07/04/2020
 * @version: 1.0.0
 * @description:  This is the container Class which hold the contents read from the JSON file
 */
public class Container {
    HashSet<Person> people = new HashSet<>();
    HashSet<Film> films = new HashSet<>();
    HashSet<Genre> genres = new HashSet<>();
    List<Profile> profiles = new ArrayList<>();
    HashSet<TVSeries> tvseries = new HashSet<>();

    public HashSet<Person> getPeople() {
        return people;
    }

    public HashSet<Film> getFilms() {
        return films;
    }

    public HashSet<Genre> getGenres() {
        return genres;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public HashSet<TVSeries> getTvseries() {
        return tvseries;
    }

    public HashSet<Media> getMedia(){
        HashSet<Media> mediaTreeSet = new HashSet<Media>();
        mediaTreeSet.addAll(getFilms());
        mediaTreeSet.addAll(getTvseries());
        return mediaTreeSet;
    }
}
