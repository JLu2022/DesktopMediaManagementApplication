package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 09/04/2020
 * @version: 1.0.0
 * @description: SelectUser class
 */
public class SelectUser extends JFrame{

    //the button list
    private List<JButton> buttonList = new ArrayList<>();

    //constructor takes in the userName List and arrange the elements in the right position
    public SelectUser(List<String> userNames){
        super("Select User");
//        selectUserFrame = new JFrame("Select User");
        GridLayout buttonLayout = new GridLayout(4,1,30,30);

        //create Button Panel
        JPanel buttonsPanel = new JPanel(buttonLayout);
        //instantiate each of the button reference and add to the List of Button and the Button Panel
        for(int i=0;i<userNames.size();i++){
            JButton button = new JButton(userNames.get(i));
            buttonList.add(button);
            buttonsPanel.add(button);
        }//end for

        //put on the right position
        this.add(buttonsPanel,BorderLayout.CENTER);
        this.add(new JPanel(),BorderLayout.NORTH);
        this.add(new JPanel(),BorderLayout.EAST);
        this.add(new JPanel(),BorderLayout.WEST);
        this.add(new JPanel(),BorderLayout.SOUTH);

        this.setSize(100,300);
        this.setVisible(true);
    }

    /*getters*/
    public List<JButton> getButtonList() {
        return buttonList;
    }

    public JFrame getSelectUserFrame() {
        return this;
    }
    /*end of getters*/
}//end class
