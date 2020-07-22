package Model;

import Model.Model;
import Model.Media;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: Factory pattern.
 * Using generic in Java to make sure all the subclass of this parent factory class can create the subclass
 * instance of Media
 */
public abstract class MediaFactory<T extends Media> {
    public abstract T createMedia(int id, String title, String description, int year, List<Genre> genre, Person person, List<Person> cast);
}
