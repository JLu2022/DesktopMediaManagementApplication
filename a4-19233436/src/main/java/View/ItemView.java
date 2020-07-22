package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Junlin Lu
 * @date: 17/04/2020
 * @version: 1.0.0
 * @description: ItemView is a class.
 * Realize the UI of the “Add Item” screen can be reused for “Item details” screen with disabled fields.
 */
public class ItemView extends JFrame{
    private ArrayList<JTextArea> textAreas = new ArrayList<>();
    private JPanel mainPanel;
    private JPanel optionalPanel;
    private String[] labelTxt = {"Title:","Description:","Year:","Genre:","Director:","Cast:"};
    private ArrayList<JLabel> jLabels = new ArrayList<>();
    private JButton button;
    private JCheckBox tvSeries;

    //the constructor takes in a boolean variable and a List of String
    //the boolean is used to justify whether the user wants to add a new Item
    //or just want to show the detail info of the Media.
    public ItemView(Boolean addNew, List<String> strings){
        super();
        //two different JFrame is created according to the boolean variable
        if(addNew) {
            this.setTitle("Add Item");

        }
        else {
            this.setTitle("Item Details");
        }

        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container,BoxLayout.Y_AXIS));

        /*initialize the same part of the frames regardless of the boolean*/
        mainPanel = new JPanel(new GridLayout(labelTxt.length,3,10,10));

        for(int i = 0;i<labelTxt.length;i++){
            JLabel label = new JLabel(labelTxt[i]);
            jLabels.add(label);

            JTextArea textArea = new JTextArea();

            if(!addNew) {
                //this if block is used to set the Text of the Label where it is 'Director' for Film and
                //make sure it is 'Creator' if the passed in String List is from a TVSeries
                if(strings.get(strings.size()-1).equals("TVSeries")&&jLabels.size()==strings.size()-1){
                    jLabels.get(4).setText("Creator");
                }
                textArea.setEditable(false);
                textArea.setText(strings.get(i));
            }
            textArea.setLineWrap(true);
            JScrollPane jsp = new JScrollPane(textArea);
            mainPanel.add(label);
            textAreas.add(textArea);
            mainPanel.add(jsp);
        }

        //this is the optionalPanel depends on the boolean variable
        optionalPanel = new JPanel();
        optionalPanel.setLayout(new BoxLayout(optionalPanel,BoxLayout.X_AXIS));

        //if the user wants to add a new Item
        if(addNew) {
            button = new JButton("Save");
            optionalPanel.add(new JLabel("TVSeries"));
            tvSeries = new JCheckBox();
            optionalPanel.add(tvSeries);
        }
        //if the user just wants to show the detail of the Media
        else{
            button = new JButton("Back");
        }

        //add
        optionalPanel.add(Box.createHorizontalGlue());
        optionalPanel.add(button);
        container.add(mainPanel);
        container.add(optionalPanel);

        this.setSize(300,500);
        this.setVisible(true);

    }

    /*Getters*/
    public ArrayList<JTextArea> getTextAreas() {
        return textAreas;
    }

    public JButton getButton(){
        return button;
    }

    public JFrame getItemViewFrame() {
        return this;
    }

    public JCheckBox getTvSeries() {
        return tvSeries;
    }
    /*End of getters*/

    /**
     * Handle the exception.
     * @param formatInfo the message of the exception
     */
    public void handleInputException(String formatInfo){
        //create a JOptionPane to show the exception
        JOptionPane.showMessageDialog(null,formatInfo,"Incorrect format",JOptionPane.INFORMATION_MESSAGE);
    }
}
