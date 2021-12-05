package Ex2;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    public static void main (String[] args) {
        JFrame j = new JFrame();
        //Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        j.setSize(500,500);
        j.setTitle("Graph GUI");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("file");
        menuBar.add(menu);
        j.add(menuBar);
        JButton b = new JButton();
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        j.setVisible(true);

    }
}
