package test.autmator.gui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
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
 * This class is the final Window in GUI. It shows an ThankYou image with two
 * buttons, Button 1. Go To Folder: this will open the default directory of
 * files Button 2. Continue: this will go to the initial window (SoapOrRest gui)
 * 
 * @author kamalesh.p
 * 
 */
public class ClosingWindow {

    private JFrame       frame;
    private final String VERDANA = "Verdana";

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    ClosingWindow window = new ClosingWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     * 
     * @throws IOException
     */
    public ClosingWindow() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @throws IOException
     */
    private void initialize() throws IOException {

        javax.swing.UIManager.put("OptionPane.font", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.messageFont", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.buttonFont", new Font(VERDANA, Font.PLAIN, 16));

        frame = new JFrame();
        frame.setTitle("Automator_V3");
        frame.setBounds(100, 100, 980, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        JButton btnOk = new JButton("Continue execution");
        btnOk.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnOk.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnOk.setBounds(507, 453, 376, 117);
        frame.getContentPane().add(btnOk);

        JButton btnGoToFolder = new JButton("View Generated Files");
        btnGoToFolder.setFont(new Font("Tahoma", Font.PLAIN, 24));
        btnGoToFolder.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {

                Desktop desktop = Desktop.getDesktop();
                File dirToOpen = null;
                try {
                    dirToOpen = new File(Constants.OUT_FILE_PATH);
                    desktop.open(dirToOpen);
                } catch (IOException exp) {
                    exp.printStackTrace();
                }
                frame.dispose();
            }
        });
        btnGoToFolder.setBounds(81, 453, 376, 117);
        frame.getContentPane().add(btnGoToFolder);

        JLabel lblThankYou = new JLabel("Thank You!");
        lblThankYou.setFont(new Font("Tahoma", Font.PLAIN, 44));
        lblThankYou.setBounds(342, 214, 229, 194);
        frame.getContentPane().add(lblThankYou);

        // URL iconURL1 = getClass().getResource("/thankYou.png");
        // ImageIcon icon = new ImageIcon(iconURL1);
        frame.setResizable(false);

    }
}
