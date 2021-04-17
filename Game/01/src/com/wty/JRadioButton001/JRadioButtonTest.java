package com.wty.JRadioButton001;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JRadioButtonTest {

    JFrame frame;
    JPanel p1;
    JRadioButton r1,r2,r3;

    public JRadioButtonTest(){
        frame = new JFrame("单选框示例");
        //创建一个内容面板容器
        Container container = frame.getContentPane();
        //设置窗口的布局
        container.setLayout(new FlowLayout());
        //创建一个面板对象
        p1 = new JPanel();
        //设置布局管理器的布局
        p1.setLayout(new GridLayout(3,1));
        p1.setBorder(BorderFactory.createTitledBorder("请选择你喜欢的城市"));
        r1 = new JRadioButton("北京");
        r2 = new JRadioButton("上海");
        r3 = new JRadioButton("青岛");

        p1.add(r1);
        p1.add(r2);
        p1.add(r3);

        r1.setSelected(true);
        container.add(p1);
        frame.pack();
        frame.setVisible(true);
        //添加一个窗口监听器
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args){
        new JRadioButtonTest();
    }

}
