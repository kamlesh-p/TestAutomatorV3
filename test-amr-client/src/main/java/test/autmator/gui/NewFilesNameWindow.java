package test.autmator.gui;

import static test.automator.constants.Constants.OPERATION_NAME;
import static test.automator.constants.Constants.SERVICE_NAME;
import static test.automator.constants.Constants.UPDATE_NEWFILES_NAMES;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
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

import test.automator.control.script.generate.JsonBuilderScriptGenerator;
import test.automator.control.script.generate.RESTGroovyScriptGenerator;
import test.automator.control.script.generate.ScriptGenerator;
import test.automator.control.testcase.generate.TestGenerator;
import test.automator.control.testdata.generate.TestDataGenerator;
import test.automator.constants.Constants;

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

	private JFrame frame;
	private JTextField txtTestSpecName;
	private JTextField txtGroovyScriptName;
	private JTextField txtRequestGroovyScript;
	private JTextField txtPropertyFileName;
	private JTextField txtDataSourceFileName;
	private JTextField txtDataSinkFileName;

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
		frame = new JFrame();
		frame.setTitle("Automator_V2");
		frame.setBounds(100, 100, 450, 409);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		// set icon image for frame
		URL iconURL = getClass().getResource("/Manipulator.png");
		ImageIcon img = new ImageIcon(iconURL);
		frame.setIconImage(img.getImage());

		JLabel lblTestspecName = new JLabel("Test-Spec:");
		lblTestspecName.setBounds(35, 41, 88, 14);
		frame.getContentPane().add(lblTestspecName);

		JLabel lblGroovyScriptName = new JLabel("Groovy Script:");
		lblGroovyScriptName.setBounds(35, 87, 91, 14);
		frame.getContentPane().add(lblGroovyScriptName);

		JLabel lblRequestScriptName = new JLabel("Request Script:");
		lblRequestScriptName.setBounds(35, 139, 88, 14);
		frame.getContentPane().add(lblRequestScriptName);

		JLabel lblPropertyFile = new JLabel("Property file:");
		lblPropertyFile.setBounds(35, 191, 80, 14);
		frame.getContentPane().add(lblPropertyFile);

		JLabel lblDatasourceFile = new JLabel("DataSource file:");
		lblDatasourceFile.setBounds(35, 244, 99, 14);
		frame.getContentPane().add(lblDatasourceFile);

		JLabel lblDatasinkFile = new JLabel("DataSink file:");
		lblDatasinkFile.setBounds(35, 292, 80, 14);
		frame.getContentPane().add(lblDatasinkFile);

		txtTestSpecName = new JTextField();
		txtTestSpecName.setText(UPDATE_NEWFILES_NAMES && !SERVICE_NAME.equals("") ? getTestSpecName() : "Use Default");
		txtTestSpecName.setBounds(133, 38, 257, 20);
		frame.getContentPane().add(txtTestSpecName);
		txtTestSpecName.setColumns(10);

		txtGroovyScriptName = new JTextField();
		txtGroovyScriptName
				.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getGroovyScriptName() : "Use Default");
		txtGroovyScriptName.setColumns(10);
		txtGroovyScriptName.setBounds(133, 84, 257, 20);
		frame.getContentPane().add(txtGroovyScriptName);

		txtRequestGroovyScript = new JTextField();
		txtRequestGroovyScript
				.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getRequestHandlerName() : "Use Default");
		txtRequestGroovyScript.setColumns(10);
		txtRequestGroovyScript.setBounds(133, 136, 257, 20);
		frame.getContentPane().add(txtRequestGroovyScript);

		txtPropertyFileName = new JTextField();
		txtPropertyFileName.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("")
				? (!SERVICE_NAME.equals("") ? SERVICE_NAME.substring(0, 4) : "")
						+ Commons.toUpperFirstChar(OPERATION_NAME)
				: "Use Default");
		txtPropertyFileName.setColumns(10);
		txtPropertyFileName.setBounds(133, 188, 257, 20);
		frame.getContentPane().add(txtPropertyFileName);

		txtDataSourceFileName = new JTextField();
		txtDataSourceFileName
				.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getDataSourceFileName() : "Use Default");
		txtDataSourceFileName.setColumns(10);
		txtDataSourceFileName.setBounds(133, 241, 257, 20);
		frame.getContentPane().add(txtDataSourceFileName);

		txtDataSinkFileName = new JTextField();
		txtDataSinkFileName
				.setText(UPDATE_NEWFILES_NAMES && !OPERATION_NAME.equals("") ? getDataSinkFileName() : "Use Default");
		txtDataSinkFileName.setColumns(10);
		txtDataSinkFileName.setBounds(133, 289, 257, 20);
		frame.getContentPane().add(txtDataSinkFileName);

		JLabel lblSpecifyTheName = new JLabel("Specify the name for new files to be created.");
		lblSpecifyTheName.setBounds(36, 11, 265, 14);
		frame.getContentPane().add(lblSpecifyTheName);

		JButton btnNext = new JButton("Next");
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
		btnNext.setBounds(301, 333, 89, 23);
		frame.getContentPane().add(btnNext);

		JButton btnCancel = new JButton("Back");
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
		btnCancel.setBounds(35, 333, 89, 23);
		frame.getContentPane().add(btnCancel);

		JButton btnNewButton = new JButton("Edit Property List");
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
		btnNewButton.setBounds(133, 333, 158, 23);
		frame.getContentPane().add(btnNewButton);

		JCheckBox chckbxUpdateNodeNames = new JCheckBox("Update Node Names in Script and Excel");
		chckbxUpdateNodeNames.addItemListener(new ItemListener() {

			public void itemStateChanged(final ItemEvent e) {
				Constants.EDIT_NODE_NAMES_IN_SCRIPTS = e.getStateChange() == 1;
			}
		});
		chckbxUpdateNodeNames.setSelected(true);
		chckbxUpdateNodeNames.setBounds(133, 211, 257, 23);
		frame.getContentPane().add(chckbxUpdateNodeNames);
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
