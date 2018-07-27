package test.automator.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * This class reads the configuration file (An external property file)
 * 
 * @author kamalesh.p
 * 
 */
public class ReadConfig {

    private final static Logger LOGGER     = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    Properties                  configProp = new Properties();

    public ReadConfig(final String PropertyPath) {

        try (FileInputStream input = new FileInputStream(new File(PropertyPath));
                Scanner in = new Scanner(input);
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            while (in.hasNext()) {
                out.write(in.nextLine().replace("\\", "\\\\").getBytes());
                out.write("\n".getBytes());
            }
            try (InputStream is = new ByteArrayInputStream(out.toByteArray());) {
                configProp.load(is);
                in.close();
                is.close();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "CONFIGURATION FILE NOT FOUND!\n\n" + e);
            e.printStackTrace();
        }
    }

    public String getValue(final String name) {
        String value = configProp.getProperty(name);
        LOGGER.info(name + ": " + value);
        return value;
    }

    public String getValue(final String name, final String defaultValue) {
        String value = configProp.getProperty(name, defaultValue);
        LOGGER.info(name + ": " + value);
        return value;
    }

    public void clearProperty() {
        configProp.clear();
    }
}
