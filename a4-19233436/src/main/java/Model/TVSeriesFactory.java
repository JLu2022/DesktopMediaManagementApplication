package Model;

import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 06/04/2020
 * @version: 1.0.0
 * @description: This is the TVSeriesFactory class which extends MediaFactory<Film> following factory pattern.
 * Also, for TVSeriesFactory, singleton pattern is taken as there is no need to create more than one TVSeriesFactory instance
 */
public class TVSeriesFactory extends MediaFactory<TVSeries> {
    private static TVSeriesFactory tvSeriesFactory= new TVSeriesFactory();
    private TVSeriesFactory(){}
    public static TVSeriesFactory getTvSeriesFactory(){
        return tvSeriesFactory;
    }

    /**
     * Create a new TVSeries
     * @param id the id of the last instance of the TVSeries set
     * @param title the title of the TVSeries instance
     * @param description the description of the TVSeries instance
     * @param year the year when the TVSeries was made
     * @param genre the genre List
     * @param person the Person person (actually the director in TVSeries case)
     * @param cast the cast List
     * @return this method returns a new TVSeries instance.
     */
    @Override
    public TVSeries createMedia(int id, String title, String description, int year, List<Genre> genre, Person person, List<Person> cast) {
        return new TVSeries(id,title,year,genre,person,cast,description);
    }
}//end class
