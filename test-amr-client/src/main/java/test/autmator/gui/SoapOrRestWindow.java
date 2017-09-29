package test.autmator.gui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import test.automator.common.SetConfigProp;

import test.automator.constants.Constants;

/**
 * This is the initial GUI class
 * It shows the window which ask UserInput for web-service type - SOAP or REST
 * It also provides button for editing TestSpecConfig file, which opens the TestSpecConfig File.
 * 
 * @author kamalesh.p
 * 
 */
public class SoapOrRestWindow {

    private JFrame frame;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    SoapOrRestWindow window = new SoapOrRestWindow();
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
    public SoapOrRestWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Automator_V2");
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        JButton btnSoapService = new JButton("SOAP Service");
        btnSoapService.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                Constants.SOAP_OR_REST = Constants.SOAP_SERVICE;
                SetConfigProp.getUserInput();
                frame.dispose();
                new XmlRequestWindow();
                XmlRequestWindow.main(null);
            }
        });
        btnSoapService.setBounds(37, 76, 169, 99);
        frame.getContentPane().add(btnSoapService);

        JButton btnRestService = new JButton("REST Service");
        btnRestService.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                Constants.SOAP_OR_REST = Constants.REST_SERVICE;
                SetConfigProp.getUserInput();
                frame.dispose();
                new RestServiceInitWindow();
                RestServiceInitWindow.main(null);
            }
        });
        btnRestService.setBounds(219, 76, 169, 99);
        frame.getContentPane().add(btnRestService);
        JLabel lblPleaseSelectThe = new JLabel("Please select the web-service type");
        lblPleaseSelectThe.setBounds(37, 41, 290, 14);
        frame.getContentPane().add(lblPleaseSelectThe);

        JLabel lblEditConfig = new JLabel("To Edit Configuration file:");
        lblEditConfig.setBounds(37, 201, 140, 14);
        frame.getContentPane().add(lblEditConfig);

        JButton btnConfig = new JButton("Edit Config");
        btnConfig.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(System.getProperty("user.dir") + "/" + Constants.CONFIG_FILE_NAME));
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, "CONFIGURATION FILE NOT FOUND!\n\nMake sure that the config file: \"" + Constants.CONFIG_FILE_NAME
                            + "\" is present\nin the same path as the Automator File.   ");
                    e1.printStackTrace();
                }
            }
        });
        btnConfig.setBounds(187, 197, 116, 23);
        frame.getContentPane().add(btnConfig);
    }
}
