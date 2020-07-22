package Model;


import java.util.TreeSet;

/**
 * @author: Junlin Lu
 * @date: 19/04/2020
 * @version: 1.0.0
 * @description: This is the profile set.
 */
public class ProfileSet extends NoneIDDataSet<Profile> {
    /*Singleton*/
    private static ProfileSet profileSet;
    private ProfileSet(Container container){
        catalogue = new TreeSet<>();
        catalogue.addAll(container.getProfiles());
    }
    public static synchronized ProfileSet getProfileSet(Container container){
        if(profileSet==null){
            profileSet = new ProfileSet(container);
        }
        return profileSet;
    }
    /*Singleton*/
}
