package test.autmator.gui;

import static test.automator.constants.Constants.OPERATION_NAME;
import static test.automator.constants.Constants.SERVICE_NAME;
import static test.automator.constants.Constants.UPDATE_NEWFILES_NAMES;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import test.automator.common.Commons;
import test.automator.constants.Constants;
import test.automator.control.script.generate.JsonBuilderScriptGenerator;
import test.automator.control.script.generate.RESTGroovyScriptGenerator;
import test.automator.control.script.generate.ScriptGenerator;
import test.automator.control.testcase.generate.TestGenerator;
import test.automator.control.testdata.generate.TestDataGenerator;

/**
 * This class ask for the user input of File Names for different files to be
 * generated. 1. txtTestSpecName: Test Spec Name 2. txtGroovyScriptName: Groovy
 * Script Name (SOAP or REST) 3. txtRequestGroovyScript: Request Groovy Script
 * Name OR JSON Builder Name 4. txtPropertyFileName: Property File Name 5.
 * txtDataSourceFileName: DataSource File Name 6. txtDataSinkFileName: Data Sink
 * File Name
 * 
 * @author kamalesh.p
 * 
 */
public class NewFilesNameWindow {

    private JFrame       frame;
    private JTextField   txtTestSpecName;
    private JTextField   txtGroovyScriptName;
    private JTextField   txtRequestGroovyScript;
    private JTextField   txtPropertyFileName;
    private JTextField   txtDataSourceFileName;
    private JTextField   txtDataSinkFileName;
    private final String VERDANA = "Verdana";

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    NewFilesNameWindow window = new NewFilesNameWindow();
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
    public NewFilesNameWindow() throws IOException {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     * 
     * @throws IOException
     */
    private void initialize() {
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

        JLabel lblTestspecName = new JLabel("Test-Spec:");
        lblTestspecName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblTestspecName.setBounds(47, 150, 167, 30);
        frame.getContentPane().add(lblTestspecName);

        JLabel lblGroovyScriptName = new JLabel("Groovy Script:");
        lblGroovyScriptName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblGroovyScriptName.setBounds(47, 207, 167, 30);
        frame.getContentPane().add(lblGroovyScriptName);

        JLabel lblRequestScriptName = new JLabel("Request Script:");
        lblRequestScriptName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblRequestScriptName.setBounds(47, 268, 167, 30);
        frame.getContentPane().add(lblRequestScriptName);

        JLabel lblPropertyFile = new JLabel("Property file:");
        lblPropertyFile.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPropertyFile.setBounds(47, 324, 173, 30);
        frame.getContentPane().add(lblPropertyFile);

        JLabel lblDatasourceFile = new JLabel("DataSource file:");
        lblDatasourceFile.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDatasourceFile.setBounds(47, 421, 173, 33);
        frame.getContentPane().add(lblDatasourceFile);

        JLabel lblDatasinkFile = new JLabel("DataSink file:");
        lblDatasinkFile.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblDatasinkFile.setBounds(47, 480, 173, 36);
        frame.getContentPane().add(lblDatasinkFile);

        txtTestSpecName = new JTextField();
        txtTestSpecName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtTestSpecName.setText(UPDATE_NEWFILES_NAMES && !SERVICE_NAME.equals("") ? getTestSpecName() : "Use Default");
        txtTestSpecName.setBounds(253, 150, 445, 29);
        frame.getContentPane().add(txtTestSpecName);
        txtTestSpecName.setColumns(10);

        txtGroovyScriptName = new JTextField();
        txtGroovyScriptName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtGroovyScriptName
                .setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getGroovyScriptName() : "Use Default");
        txtGroovyScriptName.setColumns(10);
        txtGroovyScriptName.setBounds(253, 207, 445, 28);
        frame.getContentPane().add(txtGroovyScriptName);

        txtRequestGroovyScript = new JTextField();
        txtRequestGroovyScript.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtRequestGroovyScript
                .setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getRequestHandlerName() : "Use Default");
        txtRequestGroovyScript.setColumns(10);
        txtRequestGroovyScript.setBounds(253, 267, 445, 33);
        frame.getContentPane().add(txtRequestGroovyScript);

        txtPropertyFileName = new JTextField();
        txtPropertyFileName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtPropertyFileName.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("")
                ? (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "")
                        + Commons.toUpperFirstChar(OPERATION_NAME)
                : "Use Default");
        txtPropertyFileName.setColumns(10);
        txtPropertyFileName.setBounds(253, 324, 445, 33);
        frame.getContentPane().add(txtPropertyFileName);

        txtDataSourceFileName = new JTextField();
        txtDataSourceFileName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtDataSourceFileName
                .setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getDataSourceFileName() : "Use Default");
        txtDataSourceFileName.setColumns(10);
        txtDataSourceFileName.setBounds(253, 420, 445, 36);
        frame.getContentPane().add(txtDataSourceFileName);

        txtDataSinkFileName = new JTextField();
        txtDataSinkFileName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        txtDataSinkFileName
                .setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getDataSinkFileName() : "Use Default");
        txtDataSinkFileName.setColumns(10);
        txtDataSinkFileName.setBounds(253, 481, 445, 36);
        frame.getContentPane().add(txtDataSinkFileName);

        JLabel lblSpecifyTheName = new JLabel("Specify the name for new files to be created.");
        lblSpecifyTheName.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblSpecifyTheName.setBounds(34, 54, 512, 47);
        frame.getContentPane().add(lblSpecifyTheName);

        JButton btnNext = new JButton("Next");
        btnNext.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNext.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {

                String testSpecName = txtTestSpecName.getText();
                String requestGroovyScript = txtRequestGroovyScript.getText();
                String groovyScriprName = txtGroovyScriptName.getText();
                String dataSourceFileName = txtDataSourceFileName.getText();
                String dataSinkFileName = txtDataSinkFileName.getText();
                String propertyFileName = txtPropertyFileName.getText();

                String cmd = e.getActionCommand();
                if (cmd.equals("Next")) {
                    if (!requestGroovyScript.equalsIgnoreCase("Use Default")
                            && requestGroovyScript.equalsIgnoreCase(groovyScriprName)) {
                        JOptionPane.showMessageDialog(null,
                                "GroovyScriptName and RequestGroovyName should not be same.");
                    } else if (!dataSourceFileName.equalsIgnoreCase("Use Default")
                            && dataSourceFileName.equalsIgnoreCase(dataSinkFileName)) {
                        JOptionPane.showMessageDialog(null,
                                "DataSource file name and DataSink File name should not be same.");
                    } else {
                        try {
                            // generate test case document
                            TestGenerator.createTestSpec(testSpecName);
                            txtTestSpecName.setBackground(Color.GREEN);
                            // generate groovy script
                            if (Constants.SOAP_OR_REST.equals(Constants.SOAP_SERVICE)) {
                                ScriptGenerator.createScript(requestGroovyScript, groovyScriprName);
                            } else {
                                RESTGroovyScriptGenerator.createScript(groovyScriprName);
                                JsonBuilderScriptGenerator.createScript(requestGroovyScript);
                            }

                            txtRequestGroovyScript.setBackground(Color.GREEN);
                            txtGroovyScriptName.setBackground(Color.GREEN);
                            // generate dataSource and Data sink files.
                            TestDataGenerator.createTestDataFiles(dataSourceFileName, dataSinkFileName,
                                    propertyFileName);
                            txtDataSourceFileName.setBackground(Color.GREEN);
                            txtDataSinkFileName.setBackground(Color.GREEN);
                            txtPropertyFileName.setBackground(Color.GREEN);

                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }

                        frame.dispose();
                        try {
                            new ClosingWindow();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                        ClosingWindow.main(null);
                    }
                }
            }
        });
        btnNext.setBounds(691, 588, 182, 47);
        frame.getContentPane().add(btnNext);

        JButton btnCancel = new JButton("Back");
        btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnCancel.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                if (Constants.SOAP_OR_REST.equals(Constants.SOAP_SERVICE)) {
                    frame.dispose();
                    new XmlRequestWindow();
                    XmlRequestWindow.main(null);
                } else if (Constants.SOAP_OR_REST.equals(Constants.REST_SERVICE)) {
                    frame.dispose();
                    new RestServiceInitWindow();
                    RestServiceInitWindow.main(null);
                }

            }
        });
        btnCancel.setBounds(47, 588, 201, 47);
        frame.getContentPane().add(btnCancel);

        JButton btnNewButton = new JButton("Edit Property List");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File(
                            Constants.PATH_TO_TEST_GENERATOR_EXCEL + "/" + Constants.TEST_INPUT_DATA_FILE_NAME));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnNewButton.setBounds(314, 588, 332, 47);
        frame.getContentPane().add(btnNewButton);

        JCheckBox chckbxUpdateNodeNames = new JCheckBox("Update Node Names in Script and Excel");
        chckbxUpdateNodeNames.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxUpdateNodeNames.addItemListener(new ItemListener() {

            public void itemStateChanged(final ItemEvent e) {
                Constants.EDIT_NODE_NAMES_IN_SCRIPTS = e.getStateChange() == 1;
            }
        });
        chckbxUpdateNodeNames.setSelected(true);
        chckbxUpdateNodeNames.setBounds(253, 368, 445, 33);
        frame.getContentPane().add(chckbxUpdateNodeNames);
        frame.setResizable(false);
    }

    private String getTestSpecName() {
        return (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "") + Commons.toUpperFirstChar(OPERATION_NAME)
                + "TestSpec";
    }

    private String getGroovyScriptName() {
        return (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "") + Commons.toUpperFirstChar(OPERATION_NAME)
                + "GroovyScript";
    }

    private String getRequestHandlerName() {
        return (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "") + Commons.toUpperFirstChar(OPERATION_NAME)
                + "RequestHandler";
    }

    private String getDataSourceFileName() {
        return (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "") + Commons.toUpperFirstChar(OPERATION_NAME)
                + "TestData";
    }

    private String getDataSinkFileName() {
        // if (SERVICE_NAME.contains("service")) {
        // return SERVICE_NAME.split("service")[0] + "CompleteTemplate";
        // } else if (SERVICE_NAME.contains("Service")) {
        // return SERVICE_NAME.split("Service")[0] + "CompleteTemplate";
        // }
        return (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "") + Commons.toUpperFirstChar(OPERATION_NAME)
                + "Template";
    }

}
