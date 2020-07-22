package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 09/04/2020
 * @version: 1.0.0
 * @description: The main Screen Class.
 *
 */
public class MainScreen extends JFrame{

    private List<JLabel> titles = new ArrayList<>();
    private List<JButton> buttons = new ArrayList<>();
    public static final int SWITCH_PROFILE_BTN=0, ADD_NEW_BTN=1, LIST_BY_YEAR_BTN=2, LIST_BY_GENRE_BTN=3;
    private String[] btnTxt={"Switch Profile","Add New","List by Year","List by Genre"};

    public MainScreen(String profile,List<String> info){
        super("Video Catalogue");
        //create new Frame and set DefaultCloseOperation
//        this = new JFrame("Video Catalogue");
        this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);

        //get the container
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(2,2));


        //create the left panel and set the layout to be Y.AXIS BoxLayout
        JPanel leftPanel = new JPanel(new GridLayout(4,2,15,15));



        //create rightBottomPanel
        JPanel rightBottomPanel = new JPanel(new GridLayout(info.size(),3,3,3));

        //disassemble the String List 'info', and put the elements at the right position
        for(String i: info){
            JPanel subPanel = new JPanel();
            subPanel.setLayout(new BoxLayout(subPanel,BoxLayout.X_AXIS));
            //each String is a set of different elements so it has to be split into Strings.
            String[] strings = i.split(",");

            //in-line html underlines the JLabel
            JLabel title = new JLabel("<html><u>"+strings[0]+"<html><u>",SwingConstants.LEFT);
            title.setForeground(Color.blue);//set color
            title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//set the cursor

            //add the title to both titles and the Panel
            titles.add(title);
            subPanel.add(title);

            subPanel.add(Box.createHorizontalStrut(20));
            //add the year
            subPanel.add(new JLabel(strings[1]));
            subPanel.add(Box.createHorizontalStrut(20));
            //add the genres
            subPanel.add(new JLabel(strings[2]));
            rightBottomPanel.add(subPanel);
        }

        //create the button, set the btnTxt and add to the button list and panel
        for(String s:btnTxt){
            JButton jButton = new JButton(s);
            buttons.add(jButton);
            leftPanel.add(jButton);
            leftPanel.add(new JPanel());
        }

        //add all the sub-panels to the container
        container.add(new JPanel());
        container.add(new JLabel("User profile: "+profile,SwingConstants.RIGHT));
        container.add(leftPanel);//add the leftPanel to the container
        container.add(rightBottomPanel);
        this.setSize(1000,300);
        this.setVisible(true);
    }
    /*getters*/
    public List<JLabel> getTitles() {
        return titles;
    }

    public List<JButton> getButtons() {
        return buttons;
    }

    public JFrame getMainScreenFrame() {
        return this;
    }
    /*end of getters*/
}//end class
