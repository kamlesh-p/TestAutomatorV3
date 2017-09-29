package test.autmator.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * This GUI class shows an work-in-progress window and provides a button to go to initial window.
 * 
 * @author kamalesh.p
 * 
 */
public class WorkInProgress {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    WorkInProgress window = new WorkInProgress();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public WorkInProgress() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        URL iconURL1 = getClass().getResource("/work-in-progress.jpg");
        ImageIcon icon = new ImageIcon(iconURL1);
        JLabel label = new JLabel(icon);
        label.setBounds(75, 11, 307, 207);
        frame.getContentPane().add(label);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnBack.setBounds(23, 228, 89, 23);
        frame.getContentPane().add(btnBack);

    }
}
