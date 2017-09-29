package test.autmator.gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import test.automator.control.xml.parse.EditInputExcel;
import test.automator.control.xml.parse.ParseXML;

import test.automator.common.ExceptionHandler;

import test.automator.constants.Constants;

/**
 * This class asks for the User input for Request XML and Response XML.
 * Also has a checkBox to specify: Remove Repeated node names.
 * 
 * @author kamalesh.p
 * 
 */
public class XmlRequestWindow {

    private JFrame     frame;
    private JTextArea  reqXMLTextField;
    private JTextArea  resXMLTextField;
    JCheckBox          chckbxRemoverepeatednodes;
    JCheckBox          chckbxUpdateAutomatorOutput;
    Boolean            removeRepestedNodes;
    Boolean            updateAutomatorOutputFileNames;
    private JTextField serviceNameTextField;
    private JTextField operationNameTextField;

    /**
     * Launch the application.
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    XmlRequestWindow window = new XmlRequestWindow();
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
    public XmlRequestWindow() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Automator_V2");
        frame.setBounds(100, 100, 450, 375);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        // hidden button to clear text-fields
        final JButton btnClear = new JButton("Clear");
        btnClear.setVisible(false);
        btnClear.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                String cmd = e.getActionCommand();
                if (cmd.equals("Clear")) {
                    reqXMLTextField.setText("");
                    resXMLTextField.setText("");
                }
            }
        });
        btnClear.setBounds(10, 303, 89, 23);
        frame.getContentPane().add(btnClear);

        JLabel lblService = new JLabel("Service Name:");
        lblService.setBounds(50, 65, 89, 14);
        frame.getContentPane().add(lblService);

        JLabel lblOperationName = new JLabel("Operation Name: ");
        lblOperationName.setBounds(50, 112, 98, 14);
        frame.getContentPane().add(lblOperationName);

        JLabel lblRequestXml = new JLabel("Request XML:");
        lblRequestXml.setBounds(50, 152, 89, 14);
        frame.getContentPane().add(lblRequestXml);

        JLabel lblResponseXml = new JLabel("Response XML:");
        lblResponseXml.setBounds(50, 229, 89, 14);
        frame.getContentPane().add(lblResponseXml);

        reqXMLTextField = new JTextArea();
        reqXMLTextField.setBounds(149, 152, 247, 53);
        frame.getContentPane().add(reqXMLTextField);
        reqXMLTextField.setColumns(10);

        resXMLTextField = new JTextArea();
        resXMLTextField.setColumns(10);
        resXMLTextField.setBounds(149, 229, 247, 53);
        frame.getContentPane().add(resXMLTextField);

        serviceNameTextField = new JTextField();
        serviceNameTextField.setBounds(151, 62, 247, 20);
        frame.getContentPane().add(serviceNameTextField);
        serviceNameTextField.setColumns(10);

        operationNameTextField = new JTextField();
        operationNameTextField.setBounds(152, 109, 246, 20);
        frame.getContentPane().add(operationNameTextField);
        operationNameTextField.setColumns(10);

        JButton btnNewButton = new JButton("Next");
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                Constants.SERVICE_NAME = serviceNameTextField.getText();
                Constants.OPERATION_NAME = operationNameTextField.getText();
                String reqXML = reqXMLTextField.getText();
                String resXML = resXMLTextField.getText();
                String cmd = e.getActionCommand();

                if (cmd.equals("Next"))
                {
                    System.out.println(reqXML);
                    System.out.println(resXML);
                    Object[] options = { "Back", "Continue" };
                    if (reqXML.equals("") && resXML.equals("")) {
                        JOptionPane.showMessageDialog(null, "Input XML fields are empty");
                    } else if (reqXML.equals(resXML)) {
                        // TODO: add confirm dialog with back and continue button
                        int userOption = JOptionPane.showOptionDialog(null, "Request XML and Response XML\nfields contain same file!", "Confirm", 0, 3, null,
                                options, options[0]);
                        if (userOption == 0) {
                            // back
                        } else if (userOption == 1) {
                            // continue
                        }
                    } else if (reqXML.equals("")) {
                        JOptionPane.showMessageDialog(null, "Request XML field is empty");
                    } else if (resXML.equals("")) {
                        JOptionPane.showMessageDialog(null, "Response XML field is empty");
                    } else {
                        try {
                            ParseXML.parseRequest(reqXML, removeRepestedNodes);
                            ParseXML.parseResponse(resXML, removeRepestedNodes);
                            EditInputExcel.updateInuptFileHeader();
                        } catch (Exception e1) {
                            e1.printStackTrace();
                            btnClear.doClick();
                            throw new ExceptionHandler(e1);

                        }
                        frame.dispose();
                        try {
                            new NewFilesNameWindow();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        NewFilesNameWindow.main(null);
                    }
                }
            }
        });
        btnNewButton.setBounds(309, 303, 89, 23);
        frame.getContentPane().add(btnNewButton);

        JButton btnSkip = new JButton("Skip");
        btnSkip.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                String cmd = e.getActionCommand();
                Constants.SERVICE_NAME = serviceNameTextField.getText();
                Constants.OPERATION_NAME = operationNameTextField.getText();
                if (cmd.equals("Skip"))
                {
                    frame.dispose();
                    try {
                        new NewFilesNameWindow();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    NewFilesNameWindow.main(null);
                }
            }
        });
        btnSkip.setBounds(105, 303, 89, 23);
        frame.getContentPane().add(btnSkip);

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnBack.setBounds(210, 303, 89, 23);
        frame.getContentPane().add(btnBack);

        chckbxRemoverepeatednodes = new JCheckBox("Remove Repeated Nodes");
        chckbxRemoverepeatednodes.addItemListener(new ItemListener() {

            public void itemStateChanged(final ItemEvent e) {
                removeRepestedNodes = e.getStateChange() == 1;
            }
        });
        chckbxRemoverepeatednodes.setSelected(true);
        chckbxRemoverepeatednodes.setBounds(29, 7, 207, 23);
        frame.getContentPane().add(chckbxRemoverepeatednodes);

        chckbxUpdateAutomatorOutput = new JCheckBox("Update Automator output file names");
        chckbxUpdateAutomatorOutput.addItemListener(new ItemListener() {

            public void itemStateChanged(final ItemEvent e) {
                Constants.UPDATE_NEWFILES_NAMES = e.getStateChange() == 1;
            }
        });
        chckbxUpdateAutomatorOutput.setSelected(true);
        // chckbxUpdateAutomatorOutput.setVisible(false);
        chckbxUpdateAutomatorOutput.setBounds(29, 33, 261, 23);
        frame.getContentPane().add(chckbxUpdateAutomatorOutput);

    }
}
