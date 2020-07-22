package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author: Junlin Lu
 * @date: 16/04/2020
 * @version: 1.0.0
 * @description: ListMediaDisplay class is a class used by both list by Year and list by Genre function.
 */
public class ListMediaDisplay extends JFrame{
    private ArrayList<JLabel> titleList = new ArrayList<>();
    public static final int LIST_BY_YEAR=0,LIST_BY_GENRE=1;

    public ListMediaDisplay(Integer type,TreeMap<Comparable, TreeMap<String,String>> infoMap){
        //select the name of frame by the param Integer type.
        super();
        if(type==LIST_BY_GENRE) {
            this.setTitle("List by Genre");
        }
        else if(type==LIST_BY_YEAR){
            this.setTitle("List by Year");
        }
        //get the container
        Container container = this.getContentPane();

        JScrollPane jScrollPane = new JScrollPane(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel,BoxLayout.Y_AXIS));

        //arrange the content of the passed in TreeMap
        for(Map.Entry<Comparable, TreeMap<String,String>> entry : infoMap.entrySet()){
            JPanel subPanel = new JPanel(new GridLayout(entry.getValue().size()+1,2,10,10));
            JLabel genre = new JLabel(entry.getKey().toString());
            subPanel.add(genre);
            subPanel.add(new JLabel());

            //arrange the content of the passed in TreeMap's value: another TreeMap
            for(Map.Entry<String,String> valuesEntry: entry.getValue().entrySet()){
                JLabel title = new JLabel("<html><u>"+valuesEntry.getKey()+"<html><u>");
                title.setForeground(Color.blue);
                title.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                titleList.add(title);
                JLabel year = new JLabel(valuesEntry.getValue());
                subPanel.add(title);
                subPanel.add(year);
            }//end for
            jPanel.add(subPanel);
            jPanel.add(Box.createVerticalStrut(20));
        }

        jScrollPane.setViewportView(jPanel);
        container.add(jScrollPane,BorderLayout.CENTER);
        this.setSize(300,400);
        this.setVisible(true);
    }

    /*Getters*/
    public ArrayList<JLabel> getTitleList() {
        return titleList;
    }
    /*End of getters*/
}//end class
