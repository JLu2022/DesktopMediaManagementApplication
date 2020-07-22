package Model;

import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: This is the FilmFactory class which extends MediaFactory<Film> following factory pattern.
 * Also, for FilmFactory, singleton pattern is taken as there is no need to create more than one FilmFactory instance
 */
public class FilmFactory extends MediaFactory<Film> {
    /*singleton*/
    private static final FilmFactory filmFactory= new FilmFactory();
    private FilmFactory(){}
    public static FilmFactory getFilmFactory(){
        return filmFactory;
    }
    /*singleton*/

    /**
     * Create a new Film
     * @param id the id of the last instance of the Film set
     * @param title the title of the Film instance
     * @param description the description of the Film instance
     * @param year the year when the Film was made
     * @param genre the genre List
     * @param person the Person person (actually the director in Film case)
     * @param cast the cast List
     * @return this method returns a new Film instance.
     */
    @Override
    public Film createMedia(int id, String title, String description, int year, List<Genre> genre, Person person, List<Person> cast) {
        return new Film(id,title,year,genre,person,cast,description);
    }
}//end of class
