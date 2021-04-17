package com.wty.JCheckBox002;

import javax.swing.*;
import java.awt.*;

public class JCheckBoxExample extends JFrame {

    JCheckBoxExample(){
        super("复选框");
        Container container = this.getContentPane();
        container.setLayout(new FlowLayout());
        Favorite favorite = new Favorite();
        container.add(favorite);
        this.pack();
        this.setVisible(true);
    }

    public static void main(String[] args){
        new JCheckBoxExample();
    }

}
