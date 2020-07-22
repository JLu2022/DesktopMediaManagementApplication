package Model;

import java.util.*;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: Singleton pattern.
 * The GenreSet class maintains the Genre data from the container, it also is in charge of adding
 * new Genre instance. Singleton pattern is adopted.
 */
public class GenreSet extends SoloIDDataSet<Genre> implements Addable<Genre> {

    /*Singleton*/
    private static GenreSet genreSet;
    private GenreSet (Container container){
        //create a new TreeSet and assign it to the catalogue reference
        catalogue = new TreeSet<>();

        //add all the Genres from the container
        catalogue.addAll(container.getGenres());
        initializeID(container);
    }
    public static synchronized GenreSet getGenreSet(Container container) {
        if (genreSet == null) {
            genreSet = new GenreSet(container);
        }
        return genreSet;
    }
    /*Singleton*/

    /**
     * Add the genre to the GenreSet. If it has not been in the Set yet, assign it a new gid.
     * And add it to the catalogue, if it has already existed in the Set, it would be removed automatically.
     * @param genre The genre being supposed to be added in the Set.
     */
    @Override
    public void addNewData(Genre genre) {
        if(!catalogue.contains(genre)){
            lastID++;
            genre.setGid(lastID);
        }
        else {
            for(Genre g:catalogue) if(g.equals(genre)) genre.setGid(g.getGid());
        }
        catalogue.add(genre);
    }//end method

    /**
     * The initializing method of the id.
     * @param container the container
     */
    @Override
    public void initializeID(Container container) {
        //the List containing the gid
        List<Integer> idList = new ArrayList<>();

        //iterates the List<Genre> and add the gid of each elements of the List
        for(Genre genre:container.getGenres()){
            idList.add(genre.getGid());
        }//end for

        //sort the List of gid by ascending order
        Collections.sort(idList);

        //get the biggest which is the lastID of the GenreSet
        lastID = idList.get(idList.size()-1);
    }//end method

}//end class
