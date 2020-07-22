package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 18/04/2020
 * @version: 1.0.0
 * @description: Strategy pattern and singleton pattern.
 * This class MediaInfoExtractorByTitle implementing MediaInfoExtractor and the overridden method "getMediaInfo"
 * can return the data structure of media info which can be directly used in 'Get information of Media by Title' use
 * case.
 */
public class MediaInfoExtractorByTitle implements MediaInfoExtractor{
    /*Singleton*/
    private static final MediaInfoExtractorByTitle mediaInfoExtractorByTitle = new MediaInfoExtractorByTitle();
    private MediaInfoExtractorByTitle(){
    }
    public static MediaInfoExtractorByTitle getMediaInfoExtractorByTitle(){
        return mediaInfoExtractorByTitle;
    }
    /*End of Singleton*/

    @Override
    public List<String> getMediaInfo(String title, DataSet...dataSets) {
        DataSet<Media> mediaSet = dataSets[0];
        //create the returned List
        List<String> stringArrayList = new ArrayList<>();
        //create two String
        String genres = "";
        String cast = "";
        //create a String representing the type of the media
        String mediaType="";
        //this for-loop iterates the mediaCatalogue to get the Media to see if the passed in Media
        //has the title as the some other media in the mediaCatalogue does.
        for(Media m : mediaSet.getCatalogue()){

            if(m.getTitle().equals(title)){//if titles match

                //add the String into the stringArrayList in the order of displayed in the View part
                stringArrayList.add(m.getTitle());
                stringArrayList.add(m.getDescription());
                stringArrayList.add(""+m.getYear());

                for(Genre g:m.getGenre()){
                    genres+=g.getGenre()+"|";
                }//end for

                stringArrayList.add(genres);

                if(m instanceof Film) {
                    stringArrayList.add(((Film) m).getDirector().getName());
                    mediaType="Film";
                }//end if

                if(m instanceof TVSeries){
                    stringArrayList.add(((TVSeries) m).getCreator().getName());
                    mediaType="TVSeries";
                }//end if

                for(Person p:m.getCast()){
                    cast+=p.getName()+"|";
                }//end for
                stringArrayList.add(cast);
                stringArrayList.add(mediaType);
            }//end if
        }//end for
        return stringArrayList;
    }//end method
}//end class
