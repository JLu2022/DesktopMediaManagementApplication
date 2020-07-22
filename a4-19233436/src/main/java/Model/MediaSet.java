package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: Singleton Pattern
 * The MediaSet class maintains the Media data from the container, it also is in charge of adding
 * new Media instance. Singleton pattern is adopted.
 */
public class MediaSet extends DoubleIDDataSet<Media> implements Addable<Media> {
    /*Singleton*/
    private static MediaSet mediaSet;

    private MediaSet(Container container) {
        this.catalogue = new TreeSet<>();
        catalogue.addAll(container.getMedia());
        initializeLastFID(container);
        initializeLastTID(container);
    }

    public static synchronized MediaSet getMediaSet(Container container) {
        if (mediaSet == null) {
            mediaSet = new MediaSet(container);
        }
        return mediaSet;
    }
    /*Singleton*/

    /**
     * Initialize the LastFID
     * @param container the container
     */
    public void initializeLastFID(Container container){
        List<Integer> idList = new ArrayList<>();
        for(Film film:container.getFilms()){
            idList.add(film.getFid());
        }//end for
        Collections.sort(idList);
        firstID = idList.get(idList.size()-1);
    }//end method

    /**
     * Initialize the LastTID
     * @param container the container
     */
    public void initializeLastTID(Container container){
        List<Integer> idList = new ArrayList<>();
        for(TVSeries tvSeries:container.getTvseries()){
            idList.add(tvSeries.getTid());
        }//end for
        Collections.sort(idList);
        secondID = idList.get(idList.size()-1);
    }//end method


    /**
     * This method takes in a Media media as param, it firstly determine the type of the reference variable 'media'
     * and set ID according to the type of the reference variable than add the Media instance into the catalogue.
     * @param media the Media instance added in the catalogue
     */
    @Override
    public void addNewData(Media media) {

        //if the media is a Film object
        if (media instanceof Film) {
            Film film = (Film) media;
            if (!catalogue.contains(film)) {
                firstID++;
                film.setFid(firstID);
            }//end if
            else
                for(Media m:catalogue)
                    if(m.equals(film))
                        film.setFid(((Film)m).getFid());
            catalogue.add(film);
            System.out.println("Film added");
        }

        //if the media is a TVSeries object
        else if (media instanceof TVSeries) {
            TVSeries tvSeries = (TVSeries) media;
            if (!catalogue.contains(tvSeries)) {
                secondID++;
                tvSeries.setTid(secondID);
            }//end if
            else
                for(Media m:catalogue)
                    if(m.equals(tvSeries))
                        tvSeries.setTid(((TVSeries)m).getTid());
            catalogue.add(tvSeries);
        }//end if
    }//end method

    /*Getters*/
    public Integer getLastFID() {
        return firstID;
    }

    public Integer getLastTID() {
        return secondID;
    }
    /*End of getters*/
}//end class
