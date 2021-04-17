package com.wty.JRadioButton001;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class DrawFrame extends JFrame {

    DrawFrame(){
        super("画线");
        setSize(300,300);
        setLocation(300,200);
        setVisible(true);
        MyPanel myPanel = new MyPanel();
        add(myPanel);
        MyPanelPlus myPanelPlus = new MyPanelPlus();
        add(myPanelPlus);
        MyPanelRect myPanelRect = new MyPanelRect();
        add(myPanelRect);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args){
        DrawFrame drawFrame = new DrawFrame();
    }

}

class MyPanel extends JPanel{
    public void paint(Graphics g){
        g.setColor(Color.BLUE);
        g.drawLine(20,30,280,260);
    }
}

class MyPanelPlus extends JPanel{
    public void paint(Graphics g){
        Point2D p1 = new Point2D.Double(50,50);
        Point2D p2 = new Point2D.Double(100,100);
        Line2D line = new Line2D.Double(p1,p2);
        Graphics2D graphics2D = (Graphics2D)g;
        graphics2D.setColor(Color.MAGENTA);
        graphics2D.draw(line);
    }
}

class MyPanelRect extends JPanel{
    public void print(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRect(0,0,250,220);
    }
}