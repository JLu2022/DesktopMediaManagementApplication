package Model;

import java.util.*;

/**
 * @author: Junlin Lu
 * @date: 08/04/2020
 * @version: 1.0.0
 * @description: Strategy pattern and singleton pattern
 * GrouperByGenre class which implements the Grouper interface, provides an implementation of the methods in
 * the interface. The two methods here can sort the passed in Set and reassemble to TreeMap, and can be used in different
 * cases. Also there is no need to have more than one instances of this class, so singleton pattern is adopted to implement
 * this class.
 */
public class GrouperByGenre implements Grouper {
    /*Singleton*/
    private static final GrouperByGenre grouperByGenre = new GrouperByGenre();
    private GrouperByGenre(){
    }
    public static GrouperByGenre getGrouperByGenre(){
        return grouperByGenre;
    }
    /*End of Singleton*/

    /**
     * The set of the Genre in the media library is passed in, and it is sorted and reassemble to the
     * TreeMap<Comparable,TreeSet<Media>> data structure, where a specific genre leads a Set of Media.
     * @param dataSets the set want to be sorted
     * @return the Media organized in a new form of data structure.
     */
    @Override
    public TreeMap sort(DataSet...dataSets) {
        MediaSet mediaSet = (MediaSet)dataSets[0];
        GenreSet genreSet =(GenreSet) dataSets[1];
        //create a new TreeMap, with the key Comparable, and the value TreeSet<Media>
       TreeMap<Comparable,TreeSet<Media>> mediaMapByGenre = new TreeMap<>();

       //use for-loop to add each Genre of the treeSetGenre as the key of the created TreeMap, also creating new TreeSet
       for(Comparable genre:genreSet.getCatalogue()){//this is the initialization of the mediaMapByGenre
           mediaMapByGenre.put(genre,new TreeSet<>());
       }//end for

        //use for-loop to add the Media from the mediaCatalogue from Model
       for(Media media: mediaSet.getCatalogue()){
           //use for-loop to add the Genre in the List of Genre of media to the map
           for(Genre genre: media.getGenre()){
               //get the value according to the genre and add the media into that value(TreeSet)
               mediaMapByGenre.get(genre).add(media);
           }
       }
       return  mediaMapByGenre;
    }

    /**
     * The set of the Genre in the media library is passed in, and it is sorted and reassemble to the
     * TreeMap<Comparable, TreeMap<String, String>> data structure, where a specific genre leads a TreeMap whose key is
     * String title, and the value is String year.
     * In short, a new data structure where is like Genre->String title->String year is implemented and returned for the
     * directly use of the View part of the program.
     * @param dataSets the set want to be sorted
     * @return the Media organized in a new form of data structure.
     */
    @Override
    public TreeMap<Comparable, TreeMap<String, String>> getSortedMedia(DataSet...dataSets) {
        MediaSet mediaSet = (MediaSet)dataSets[0];
        GenreSet genreSet =(GenreSet) dataSets[1];
        //the param Set is firstly called the first method in this class and get the return of sorted and reassembled
        //data structure
        TreeMap<Comparable, TreeSet<Media>> map =sort(mediaSet,genreSet);

        //create a new data structure as the container of the return contents
        TreeMap<Comparable, TreeMap<String,String>> mapByGenreDisplay = new TreeMap<>();

        //this for-loop iterates the K-V pair in the map which is the sorted and reassembled mediaCatalogue
        for(Map.Entry<Comparable, TreeSet<Media>> entry:map.entrySet()){

            //store the Key as String genre
            String genre = entry.getKey().toString();

            //create a new TreeMap whose key and value are both String
            TreeMap<String,String> mapTitleYear = new TreeMap<>();

            //this inner for-loop iterates the TreeSet<Media> instance and get each Media instance
            for (Media media : entry.getValue()) {

                //get the title of the Media instance
                String title = media.getTitle();

                //get the year of the Media instance and store it as String
                String year = "" + media.getYear();

                //put the title and year into the TreeMap
                mapTitleYear.put(title, year);
            }//end for

            //put the genre and TreeMap created in the inner for-loop into the TreeMap
            mapByGenreDisplay.put(genre,mapTitleYear);
        }//end for

        return mapByGenreDisplay;
    }//end of method

}//end class
