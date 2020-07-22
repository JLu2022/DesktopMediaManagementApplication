package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 18/04/2020
 * @version: 1.0.0
 * @description: Strategy pattern and singleton pattern.
 * This class MediaInfoExtractorByProfile implementing MediaInfoExtractor and the overridden method "getMediaInfo"
 * can return the data structure of media info which can be directly used in 'Get information of Media by Profile' use
 * case.
 */
public class MediaInfoExtractorByProfile implements MediaInfoExtractor{
    /*Singleton*/
    private static MediaInfoExtractorByProfile mediaInfoExtractorByProfile = new MediaInfoExtractorByProfile();
    private MediaInfoExtractorByProfile(){
    }
    public static MediaInfoExtractorByProfile getMediaInfoExtractorByProfile(){
        return mediaInfoExtractorByProfile;
    }
    /*End of Singleton*/


    @Override
    public List<String> getMediaInfo(String profile, DataSet...dataSets) {
        DataSet<Media> mediaSet=dataSets[0];
        DataSet<Profile> profileSet=dataSets[1];
        //Create a new List which is then returned
        List<String> mediaInfoByProfile = new ArrayList<>();

        //The media gotten by Profile is mainly based on the preference of the Profile, so
        //a List of media of specific genre(the profile's preference) is created to contain the related data.
        List<Media> mediaBySpecificGenre = new ArrayList<>();
        //create a new StringBuilder to expand String easily
        StringBuilder info = new StringBuilder();
        for(Profile p: profileSet.getCatalogue()) {//find each profile in the profiles of the container
            if (profile.equals(p.getName())) {//if the profile equals to some existed profile
                for (Media media : mediaSet.getCatalogue()) {//iterate the mediaCatalogue to find the Media with the same gid as the profile's preference
                    for (Genre genre : media.getGenre()) {
                        if (genre.getGid() == p.getPreferredGenre().getGid()) {//if the gid is the same as the gid of the profile's preference gid
                            mediaBySpecificGenre.add(media);//add the related media to the List
                        }//end if
                    }//end for
                }//end for
            }//end if
        }//end for
        //this for-loop iterates the mediaBySpecificGenre
        for(Media s:mediaBySpecificGenre){
            //append the title of the Media and append the separator ','
            info.append(s.getTitle()).append(",");
            //append the year of the Media and append the separator ','
            info.append(s.getYear()).append(",");

            //reassemble the genre as a new String
            for(Genre genre :s.getGenre()){
                info.append(genre.getGenre()).append("|");
            }//end for

            mediaInfoByProfile.add(info.toString());
            info= new StringBuilder();//create a new StringBuilder
        }//end for
        return  mediaInfoByProfile;
    }//end method
}//end class
