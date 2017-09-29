package test.autmator.gui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import test.automator.constants.Constants;

/**
 * This class is used for REST services, It informs the user to edit the input Excel file manually.
 * It also provides the link (Edit Button) which opens the input Excel file.
 * 
 * @author kamalesh.p
 * 
 */
public class RestServiceInitWindow {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    RestServiceInitWindow window = new RestServiceInitWindow();
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
    public RestServiceInitWindow() {
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

        JLabel lblYouHaveTo = new JLabel(
                "<html><h3>You have to update the input excel sheet manually,<br>\r\nwith input and output details for REST services<br>\r\n<br>\r\nPress Edit to edit excel file.</h3></html>");
        lblYouHaveTo.setBounds(45, 43, 368, 88);
        frame.getContentPane().add(lblYouHaveTo);

        JButton btnEdit = new JButton("Edit");
        btnEdit.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(Constants.PATH_TO_TEST_GENERATOR_EXCEL + "/" + Constants.TEST_INPUT_DATA_FILE_NAME));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnEdit.setBounds(45, 131, 89, 23);
        frame.getContentPane().add(btnEdit);

        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                try {
                    new NewFilesNameWindow();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                NewFilesNameWindow.main(null);
            }
        });
        btnNext.setBounds(293, 205, 89, 23);
        frame.getContentPane().add(btnNext);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnBack.setBounds(194, 205, 89, 23);
        frame.getContentPane().add(btnBack);
    }
}
