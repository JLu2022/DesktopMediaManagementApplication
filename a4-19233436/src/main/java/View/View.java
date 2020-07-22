package View;
import java.util.List;
import java.util.TreeMap;

/**
 * @author: Junlin Lu
 * @date: 16/04/2020
 * @version: 1.0.0
 * @description: This is the view Class. It is in charge of the creation of each kind of JFrame and their getters,
 * and communicated with Model directly through the Controller.
 */
public class View {
    //the fields
    private MainScreen mainScreen;
    private SelectUser selectUser;
    private ListMediaDisplay listMediaDisplay;
    private ItemView itemView;

    //constructor
    public View(){
    }

    //create main Screen
    public void createMainScreen(String profile,List<String> info){
        mainScreen = new MainScreen(profile,info);
    }

    //get the main Screen reference
    public MainScreen getMainScreen() {
        return mainScreen;
    }

    //create the Select User
    public void createSelectUser(List<String> userNames){
        selectUser = new SelectUser(userNames);
    }

    //get the selectUser reference
    public SelectUser getSelectUser() {
        return selectUser;
    }

    //create the ItemView
    public void createItemView(Boolean addNew,List<String> strings){
        itemView = new ItemView(addNew,strings);
    }

    //get the ItemView reference
    public ItemView getItemView(){
        return itemView;
    }

    //create ListMediaDisplay
    public void createListMediaDisplay(Integer type, TreeMap<Comparable, TreeMap<String,String>> genreMap){
        listMediaDisplay = new ListMediaDisplay(type,genreMap);
    }

    //get ListMediaDisplay reference
    public ListMediaDisplay getListMediaDisplay() {
        return listMediaDisplay;
    }
}//end class
