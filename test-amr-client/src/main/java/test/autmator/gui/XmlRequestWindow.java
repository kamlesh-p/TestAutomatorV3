package test.autmator.gui;

import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import test.automator.common.ExceptionHandler;
import test.automator.constants.Constants;
import test.automator.control.xml.parse.EditInputExcel;
import test.automator.control.xml.parse.ParseXML;

/**
 * This class asks for the User input for Request XML and Response XML.
 * Also has a checkBox to specify: Remove Repeated node names.
 * 
 * @author kamalesh.p
 * 
 */
public class XmlRequestWindow {

    private static final String VERDANA = "Verdana";
    private JFrame              frame;
    private JTextArea           reqXMLTextField;
    private JTextArea           resXMLTextField;
    JCheckBox                   chckbxRemoverepeatednodes;
    JCheckBox                   chckbxUpdateAutomatorOutput;
    Boolean                     removeRepestedNodes;
    Boolean                     updateAutomatorOutputFileNames;
    private JTextField          serviceNameTextField;
    private JTextField          operationNameTextField;

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
        javax.swing.UIManager.put("OptionPane.font", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.messageFont", new Font(VERDANA, Font.PLAIN, 16));
        javax.swing.UIManager.put("OptionPane.buttonFont", new Font(VERDANA, Font.PLAIN, 16));

        frame = new JFrame();
        frame.setTitle("Automator_V3");
        frame.setBounds(100, 100, 980, 740);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // set icon image for frame
        URL iconURL = getClass().getResource("/Manipulator.png");
        ImageIcon img = new ImageIcon(iconURL);
        frame.setIconImage(img.getImage());

        // hidden button to clear text-fields
        final JButton btnClear = new JButton("Clear");
        btnClear.setFont(new Font("Tahoma", Font.PLAIN, 20));
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
        btnClear.setBounds(50, 615, 107, 40);
        frame.getContentPane().add(btnClear);

        JLabel lblService = new JLabel("Service Name:");
        lblService.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblService.setBounds(50, 74, 148, 22);
        frame.getContentPane().add(lblService);

        JLabel lblOperationName = new JLabel("Operation Name: ");
        lblOperationName.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblOperationName.setBounds(50, 122, 171, 18);
        frame.getContentPane().add(lblOperationName);

        JLabel lblRequestXml = new JLabel("Request XML:");
        lblRequestXml.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblRequestXml.setBounds(50, 169, 148, 30);
        frame.getContentPane().add(lblRequestXml);

        JLabel lblResponseXml = new JLabel("Response XML:");
        lblResponseXml.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblResponseXml.setBounds(50, 371, 154, 40);
        frame.getContentPane().add(lblResponseXml);

        reqXMLTextField = new JTextArea();
        reqXMLTextField.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane scroll1 = new JScrollPane(reqXMLTextField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll1.setSize(641, 171);
        scroll1.setLocation(224, 166);
        reqXMLTextField.setColumns(10);
        frame.getContentPane().add(scroll1);

        resXMLTextField = new JTextArea();
        resXMLTextField.setFont(new Font("Monospaced", Font.PLAIN, 20));
        resXMLTextField.setColumns(10);
        JScrollPane scroll2 = new JScrollPane(resXMLTextField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll2.setSize(646, 186);
        scroll2.setLocation(219, 368);
        frame.getContentPane().add(scroll2);

        serviceNameTextField = new JTextField();
        serviceNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        serviceNameTextField.setBounds(224, 74, 435, 30);
        frame.getContentPane().add(serviceNameTextField);
        serviceNameTextField.setColumns(10);

        operationNameTextField = new JTextField();
        operationNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 20));
        operationNameTextField.setBounds(224, 116, 435, 30);
        frame.getContentPane().add(operationNameTextField);
        operationNameTextField.setColumns(10);

        JButton btnNewButton = new JButton("Next");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnNewButton.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                Constants.SERVICE_NAME = serviceNameTextField.getText();
                Constants.OPERATION_NAME = operationNameTextField.getText();
                String reqXML = reqXMLTextField.getText();
                String resXML = resXMLTextField.getText();
                String cmd = e.getActionCommand();

                if (cmd.equals("Next")) {
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
        btnNewButton.setBounds(685, 615, 180, 40);
        frame.getContentPane().add(btnNewButton);

        JButton btnSkip = new JButton("Skip");
        btnSkip.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnSkip.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                String cmd = e.getActionCommand();
                Constants.SERVICE_NAME = serviceNameTextField.getText();
                Constants.OPERATION_NAME = operationNameTextField.getText();
                if (cmd.equals("Skip")) {
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
        btnSkip.setBounds(224, 615, 199, 40);
        frame.getContentPane().add(btnSkip);

        JButton btnBack = new JButton("Back");
        btnBack.setFont(new Font("Tahoma", Font.PLAIN, 20));
        btnBack.addActionListener(new ActionListener() {

            public void actionPerformed(final ActionEvent e) {
                frame.dispose();
                new SoapOrRestWindow();
                SoapOrRestWindow.main(null);
            }
        });
        btnBack.setBounds(460, 615, 199, 40);
        frame.getContentPane().add(btnBack);

        chckbxRemoverepeatednodes = new JCheckBox("Remove Repeated Nodes");
        chckbxRemoverepeatednodes.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxRemoverepeatednodes.addItemListener(new ItemListener() {

            public void itemStateChanged(final ItemEvent e) {
                removeRepestedNodes = e.getStateChange() == 1;
            }
        });
        chckbxRemoverepeatednodes.setSelected(true);
        chckbxRemoverepeatednodes.setBounds(29, 7, 444, 23);
        frame.getContentPane().add(chckbxRemoverepeatednodes);

        chckbxUpdateAutomatorOutput = new JCheckBox("Update Automator output file names");
        chckbxUpdateAutomatorOutput.setFont(new Font("Tahoma", Font.PLAIN, 20));
        chckbxUpdateAutomatorOutput.addItemListener(new ItemListener() {

            public void itemStateChanged(final ItemEvent e) {
                Constants.UPDATE_NEWFILES_NAMES = e.getStateChange() == 1;
            }
        });
        chckbxUpdateAutomatorOutput.setSelected(true);
        // chckbxUpdateAutomatorOutput.setVisible(false);
        chckbxUpdateAutomatorOutput.setBounds(29, 33, 444, 23);
        frame.getContentPane().add(chckbxUpdateAutomatorOutput);
        frame.setResizable(false);

    }
}
