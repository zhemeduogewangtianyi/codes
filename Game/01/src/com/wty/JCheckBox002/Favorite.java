package com.wty.JCheckBox002;

import javax.swing.*;

public class Favorite extends JPanel {

    JCheckBox sport,computer,music,read;

    public Favorite(){
        sport = new JCheckBox("运动");
        computer = new JCheckBox("计算机");
        music = new JCheckBox("音乐");
        read = new JCheckBox("阅读");

        this.add(sport);
        this.add(computer);
        this.add(music);
        this.add(read);

        sport.setSelected(false);
        computer.setSelected(false);
        music.setSelected(false);
        read.setSelected(false);


    }

}
