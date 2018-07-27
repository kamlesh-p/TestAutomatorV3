package test.autmator.gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * NOT USED:
 * This GUI class was designed with the intent to provide user-input window for config file editing link: but has been replaced with a direct link to config file.
 * 
 * @author kamalesh.p
 * 
 */
public class ConfigWindow {

    private JFrame       frame;
    private JTextField   txtTestspectemplatexls;
    private JTextField   txtTestdatatemplatexls;
    private JTextField   txtTestgeneratorxlsx;
    private JTextField   textField_3;
    private JTextField   textField_4;
    private JTextField   textField_5;
    private final String VERDANA = "Verdana";

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    ConfigWindow window = new ConfigWindow();
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
    public ConfigWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        javax.swing.UIManager.put("OptionPane.font", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.messageFont", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.buttonFont", new Font(VERDANA, Font.PLAIN, 16));

        frame = new JFrame();
        frame.setBounds(100, 100, 980, 720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        JCheckBox chckbxNewCheckBox = new JCheckBox("Generate Test Case");
        chckbxNewCheckBox.setSelected(true);
        chckbxNewCheckBox.setBounds(30, 7, 150, 23);
        frame.getContentPane().add(chckbxNewCheckBox);

        JButton btnOk = new JButton("OK");
        btnOk.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {

                // String TestSpecTemplateName = txtTestspectemplatexls.getText();
                // String TestDataTemplateName = txtTestdatatemplatexls.getText();
                // String TestInputDataFileName = txtTestgeneratorxlsx.getText();

                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnOk.setBounds(356, 230, 86, 23);
        frame.getContentPane().add(btnOk);

        JButton btnNewButton = new JButton("Test Case Properties");
        btnNewButton.setBounds(30, 37, 159, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnNewButton_1 = new JButton("Script Properties");
        btnNewButton_1.setBounds(214, 37, 159, 23);
        frame.getContentPane().add(btnNewButton_1);

        JLabel lblTestspec = new JLabel("TestSpecTemplateName  :");
        lblTestspec.setBounds(30, 71, 150, 14);
        frame.getContentPane().add(lblTestspec);

        txtTestspectemplatexls = new JTextField();
        txtTestspectemplatexls.setText("TestSpecTemplate.xls");
        txtTestspectemplatexls.setBounds(214, 68, 229, 20);
        frame.getContentPane().add(txtTestspectemplatexls);
        txtTestspectemplatexls.setColumns(10);

        JLabel lblTestdatatemplatename = new JLabel("TestDataTemplateName   :");
        lblTestdatatemplatename.setBounds(30, 96, 150, 14);
        frame.getContentPane().add(lblTestdatatemplatename);

        txtTestdatatemplatexls = new JTextField();
        txtTestdatatemplatexls.setText("TestDataTemplate.xls");
        txtTestdatatemplatexls.setColumns(10);
        txtTestdatatemplatexls.setBounds(214, 93, 229, 20);
        frame.getContentPane().add(txtTestdatatemplatexls);

        JLabel lblTestinputdatafilename = new JLabel("TestInputDataFileName     :");
        lblTestinputdatafilename.setBounds(30, 121, 150, 14);
        frame.getContentPane().add(lblTestinputdatafilename);

        txtTestgeneratorxlsx = new JTextField();
        txtTestgeneratorxlsx.setText("TestGenerator.xlsx");
        txtTestgeneratorxlsx.setColumns(10);
        txtTestgeneratorxlsx.setBounds(214, 118, 229, 20);
        frame.getContentPane().add(txtTestgeneratorxlsx);

        JLabel lblPathtotestspectemplateexcel = new JLabel("PathToTestSpecTemplateExcel :");
        lblPathtotestspectemplateexcel.setBounds(30, 152, 190, 14);
        frame.getContentPane().add(lblPathtotestspectemplateexcel);

        JLabel lblPathtotestdatatemplateexcel = new JLabel("PathToTestDataTemplateExcel  :");
        lblPathtotestdatatemplateexcel.setBounds(30, 177, 190, 14);
        frame.getContentPane().add(lblPathtotestdatatemplateexcel);

        JLabel lblPathtotestgeneratorexcel = new JLabel("PathToTestGeneratorExcel         :");
        lblPathtotestgeneratorexcel.setBounds(30, 202, 190, 14);
        frame.getContentPane().add(lblPathtotestgeneratorexcel);

        textField_3 = new JTextField();
        textField_3.setBounds(224, 149, 218, 20);
        frame.getContentPane().add(textField_3);
        textField_3.setColumns(10);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(224, 174, 218, 20);
        frame.getContentPane().add(textField_4);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(224, 199, 218, 20);
        frame.getContentPane().add(textField_5);

        JButton btnCancel = new JButton("Reset");
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                // TODO add reset functionality
                frame.dispose();
            }
        });
        btnCancel.setBounds(224, 230, 89, 23);
        frame.getContentPane().add(btnCancel);
        frame.setResizable(false);
    }
}
