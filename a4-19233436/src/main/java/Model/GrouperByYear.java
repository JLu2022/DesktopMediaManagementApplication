package Model;

import java.util.*;

/**
 * @author: Junlin Lu
 * @date: 08/04/2020
 * @version: 1.0.0
 * @description: Strategy pattern and singleton pattern
 * GrouperByYear class which implements the Grouper interface, provides an implementation of the methods in
 * the interface. The two methods here can sort the passed in Set and reassemble to TreeMap, and can be used in different
 * cases. Also there is no need to have more than one instances of this class, so singleton pattern is adopted to implement
 * this class.
 */
public class GrouperByYear implements Grouper {

    /*Singleton*/
    private static final GrouperByYear grouperByYear = new GrouperByYear();
    private GrouperByYear(){}
    public static GrouperByYear getGrouperByYear(){
        return grouperByYear;
    }
    /*End of Singleton*/


    /**
     * The set of the Genre in the media library is passed in, and it is sorted and reassemble to the
     * TreeMap<Comparable,TreeSet<Media>> data structure, where a specific year leads a Set of Media.
     * @param dataSets the Set want to be sorted
     * @return the Media organized in a new form of data structure.
     */
    @Override
    public TreeMap sort(DataSet...dataSets) {
        MediaSet mediaSet = (MediaSet)dataSets[0];
        YearSet yearSet =(YearSet) dataSets[1];

        /*--create a new treeMap, with Integer (the year) as the key, while the TreeSet of Media as the value--*/
        TreeMap<Comparable, TreeSet<Media>> mediaMapByYear = new TreeMap<Comparable,TreeSet<Media>>();
        /*----------------------------------------------------------------------------*/
        //Initialize the TreeMap<Integer>
        for(Comparable year:yearSet.getCatalogue()){
            mediaMapByYear.put(year, new TreeSet<>());
        }//end for

        for(Media media: mediaSet.getCatalogue()){
            //get the year of this media and use the year as the key to get the value(Set) from mediaMapByYear and add the
            //media to that specific Set pointed by the year
                mediaMapByYear.get(media.getYear()).add(media);
        }//end for

        return mediaMapByYear;
    }

    /**
     * The set of the Year in the media library is passed in, and it is sorted and reassemble to the
     * TreeMap<Comparable, TreeMap<String, String>> data structure, where a specific genre leads a TreeMap whose key is
     * String title, and the value is String genre.
     * In short, a new data structure where is like Genre->String title->String genre is implemented and returned for the
     * directly use of the View part of the program.
     * @param dataSets the set want to be sorted
     * @return the Media organized in a new form of data structure.
     */
    @Override
    public TreeMap<Comparable, TreeMap<String, String>> getSortedMedia(DataSet...dataSets) {
        MediaSet mediaSet = (MediaSet)dataSets[0];
        YearSet yearSet = (YearSet)dataSets[1];
        //the param Set is firstly called the first method in this class and get the return of sorted and reassembled
        //data structure
        TreeMap<Comparable, TreeSet<Media>> map = sort(mediaSet,yearSet);

        //create a new data structure as the container of the return contents, but this TreeMap needs to reverse the
        // order according to the keys, as is shown in the requirement the latest year should be firstly displayed
        TreeMap<Comparable,TreeMap<String,String>> mapByYearDisplay = new TreeMap<Comparable,TreeMap<String,String>>(Comparator.reverseOrder());

        //this for-loop iterates the K-V pair in the map which is the sorted and reassembled mediaCatalogue
        for(Map.Entry<Comparable, TreeSet<Media>> entry:map.entrySet()){
            //store the key "year" as String year
            String year = ""+entry.getKey();

            //create a new TreeMap whose key and value are both String
            TreeMap<String,String> mapTitleGenre = new TreeMap<>();

            //this inner for-loop iterates the value of the TreeMap map and get the title and genres
            for (Media media : entry.getValue()) {

                //get the title of Media instance
                String title = media.getTitle();

                //create a StringBuilder to append the element of the String more easily
                StringBuilder genre = new StringBuilder();

                //this double inner for-loop iterates the List<Genre> of the Media instance and append each of the genre
                //String as a new String
                for (Genre g : media.getGenre()) {
                    genre.append(g.getGenre()).append("|");//appending the genre String
                }//end double inner for

                //put the two String into the map
                mapTitleGenre.put(title, genre.toString());
            }//end inner for

            //put the String and TreeMap into the bigger TreeMap
            mapByYearDisplay.put(year,mapTitleGenre);

        }//end for

        return mapByYearDisplay;
    }//end method
}//end class
