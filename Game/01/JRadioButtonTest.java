import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class JRadioButtonTest{

	JFrame frame;
	JPanel p1;
	JRadioButton r1,r2,r3;

	public JRadioButtonTest(){
		frame = new JFrame("111");
		Container contentPane = frame.getContentPane();
		contentPane.setLayout(new FlowLayout());
		p1 = new JPanel();
		p1.setLayout(new GridLayout(3,1));
		p1.setBorder(BorderFactory.createTitledBorder("请你喜欢的城"));
		r1 = new JRadioButton("北京");
		r2 = new JRadioButton("上海");
		r3 = new JRadioButton("青岛");
		p1.add(r1);
		p1.add(r2);
		p1.add(r3);
		r1.setSelected(true);
		contentPane.add(p1);
		frame.pack();
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	public static void main(String[] args){
		new JRadioButtonTest();
	}

}