package Controller;
import Model.*;
import View.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
/**
 * @author: Junlin Lu
 * @date: 15/04/2020
 * @version: 1.0.0
 * @description: This is the Controller class which maintains the communication between the View and the Model.
 */
public class Controller {
    private Model model;
    private View view;

    //constructor
    public Controller(Model model, View view){
        this.view = view;
        this.model = model;
        updateMainScreen();
    }

    //By default, the first window displayed is the MainScreen with the first element in the ProfileSet of Model
    public void updateMainScreen(){
        updateMainScreen(model.getProfile().get(0));
    }



    /**
     * When choosing another userProfile this initializing method is called
     * It firstly gets the info of the Media by the Profile.
     * If there was a MainScreen, dispose it, then create a new MainScreen and call initializeMainScreenController()
     * @param profile the userProfile whose information should be displayed on the MainScreen
     */
    public void updateMainScreen(String profile){
        List<String> infoList = model.getMediaInfoByProfile(profile);
        if(view.getMainScreen()!=null)
            view.getMainScreen().getMainScreenFrame().dispose();
        view.createMainScreen(profile,infoList);
        initializeMainScreenController();
    }

    /**
     * Initialize the MainScreen buttons and label links.
     */
    public void initializeMainScreenController(){
        //initialize the buttons by adding an ActionListener
        view.getMainScreen().getButtons().get(MainScreen.SWITCH_PROFILE_BTN).addActionListener(e -> getSelectUserView(model.getProfile()));
        view.getMainScreen().getButtons().get(MainScreen.LIST_BY_YEAR_BTN).addActionListener(e->getListedMedia(ListMediaDisplay.LIST_BY_YEAR,model.getListedMediaByYear()));
        view.getMainScreen().getButtons().get(MainScreen.LIST_BY_GENRE_BTN).addActionListener(e-> getListedMedia(ListMediaDisplay.LIST_BY_GENRE,model.getListedMediaByGenre()));
        view.getMainScreen().getButtons().get(MainScreen.ADD_NEW_BTN).addActionListener(e->getItemView(true,null));

        //each of the Title JLabel should be added a mouseListener as a link to the detailed interface
        for(JLabel jLabel:view.getMainScreen().getTitles()){
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    getItemView(false,model.getMediaInfoByTitle(jLabel.getText().replaceAll("</?[^>]+>", "")));
                }//end method
            });
        }//end for
}//end method

    /**
     * create a Item related frame by taking two params, addNew and strings.
     * @param addNew true means add new item, false means show detail info of the media
     * @param strings the strings want to show in the window.
     */
    public void getItemView(Boolean addNew,List<String> strings){
       view.createItemView(addNew,strings);
       initItemViewController(addNew);
    }

    /**
     * pass in a List of String of userName, call view.createSelectUser(userName) to create the 'SelectUser'
     * window, and call initSelectUserController() to initialize the controller.
     * @param userName the user names show in the SelectUserView window.
     */
    public void getSelectUserView(List<String> userName){
        view.createSelectUser(userName);
        initSelectUserController();
    }

    /**
     * initialize the controller of the SelectUser interface
     */
    public void initSelectUserController(){
        //iterates the ButtonList of the SelectUser interface and add ActionListener to each of them
        for(int i=0;i<view.getSelectUser().getButtonList().size();i++) {
            int finalI = i;
            view.getSelectUser().getButtonList().get(i).addActionListener(e -> {
                updateMainScreen(model.getProfile().get(finalI));//in local class it should be effective final
                view.getSelectUser().getSelectUserFrame().dispose();
            });
        }//end for
    }//end method

    /**
     * Initialize the Item ViewController according to the boolean addNew
     * @param addNew
     */
    public void initItemViewController(Boolean addNew){
        //this initializes the addNew interface
        if(addNew) {
            view.getItemView().getButton().addActionListener(e -> {
                addItem(view.getItemView().getTvSeries().isSelected());
            });
        }
        //initializes the detail info interface
        else view.getItemView().getButton().addActionListener(e->{backToMainScreen();});
    }

    //when the 'Back' button is pressed by the user, this method is called
    public void backToMainScreen(){
        view.getItemView().getItemViewFrame().dispose();
    }

    /**
     * Add Item to the Model. In this method the String of each field is assigned to the String variable.
     * After adding the item, call the initializeMainScreenView() to renew the mainScreen
     * @param tvSeries this is the boolean variable of the checkbox.
     */
    public void addItem(boolean tvSeries){
        String title = view.getItemView().getTextAreas().get(0).getText();
        String description = view.getItemView().getTextAreas().get(1).getText();
        String year = view.getItemView().getTextAreas().get(2).getText();
        String genres = view.getItemView().getTextAreas().get(3).getText();
        String person = view.getItemView().getTextAreas().get(4).getText();
        String cast = view.getItemView().getTextAreas().get(5).getText();
        try {//if the year does not consist of pure numbers InputFormatException is thrown out
            model.addMedia(title,description,year,genres,person,cast,tvSeries);
        } catch (InputFormatException e) {
            //catch the Exception and handle it in View part.
            view.getItemView().handleInputException(e.getMessage());
        }
        view.getMainScreen().getMainScreenFrame().dispose();

        //update the MainScreen
        updateMainScreen();
        view.getItemView().getItemViewFrame().dispose();
    }

    /**
     * Get Media listed by some criteria the Integer type is the type of the criteria and the mediaMap is the Map
     * being listed by the criteria. Call initListMedia() after creating the ListMediaDisplay interface.
     * @param type the type of the criteria by which the Media is listed.
     * @param mediaMap the data the user wants to list
     */
    public void getListedMedia(Integer type,TreeMap<Comparable, TreeMap<String,String>> mediaMap){
        view.createListMediaDisplay(type,mediaMap);
        initListMedia();
    }

    /**
     * initialize the ListMediaDisplay interface.
     */
    public void initListMedia(){
        for(JLabel jLabel:view.getListMediaDisplay().getTitleList()){
            //get pure String without html
            String labelTxt = jLabel.getText().replaceAll("</?[^>]+>", "");
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    getItemView(false,model.getMediaInfoByTitle(labelTxt));
                }
            });
        }//end for
    }//end method
}//end class
