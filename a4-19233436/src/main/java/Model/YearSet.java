package Model;

import java.util.TreeSet;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: Singleton
 * The YearSet class maintains and the Year data from the container,
 * Singleton pattern is adopted.
 */
public class YearSet extends NoneIDDataSet<Integer> implements Addable<Integer> {
    /*Singleton*/
    private static YearSet yearSet;

    private YearSet(Container container){
        catalogue = new TreeSet<>();
        for(Film film: container.getFilms()){
            catalogue.add(film.getYear());
        }
        for(TVSeries tvSeries:container.getTvseries()){
            catalogue.add(tvSeries.getYear());
        }
    }
    public static synchronized YearSet getYearSet(Container container){
        if(yearSet==null){
            yearSet = new YearSet(container);
        }
        return yearSet;
    }
    /*Singleton*/

    /**
     * Add the integer (year) to the YearSet.And add it to the catalogue,
     * if it has already existed in the Set, it would be removed automatically.
     * @param integer The year being supposed to be added in the Set.
     */
    @Override
    public void addNewData(Integer integer) {
        catalogue.add(integer);
    }

}
