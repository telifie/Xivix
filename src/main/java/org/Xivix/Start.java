package org.Xivix;

import com.formdev.flatlaf.IntelliJTheme;
import org.Xivix.UI.Views.Singleton.Controller;
import org.Xivix.Utils.Configuration;
import org.Xivix.Utils.Engine;
import org.Xivix.Utils.Json;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.UUID;

public class Start {

    public static String WINDOWS_SYSTEM_DIR = System.getenv("APPDATA") + "\\Xivix\\";

    public static void main(String[] args) {
        try {
            IntelliJTheme.setup(Start.class.getResourceAsStream("/com/formdev/flatlaf/intellijthemes/themes/SolarizedLight.theme.json"));
        } catch (Exception e) {
            System.err.println("Failed FlatLaf dark theme.");
        }
        File appDataPath = new File(WINDOWS_SYSTEM_DIR);
        if(!appDataPath.exists()) {
            appDataPath.mkdirs();
            String orgName = JOptionPane.showInputDialog("Organization Name");
            if (orgName != null && !orgName.trim().isEmpty()) {
                Configuration newConfiguration = new Configuration(orgName);
                Json.save(WINDOWS_SYSTEM_DIR + UUID.randomUUID() + ".xiix", newConfiguration);
                Engine.setConfiguration(newConfiguration);
                new Controller();
                System.out.println("Configuration saved and controller initialized.");
            } else {
                System.out.println("Operation canceled or invalid input.");
            }
        }else{
            File[] mdf = appDataPath.listFiles();
            if (mdf != null && mdf.length > 0) {
                for (int i = 0; i < mdf.length; i++) {
                    File file = mdf[i];
                    if (file.getPath().endsWith(".xiix")) {
                        Engine.setConfiguration(Json.load(file.getPath(), Configuration.class));
                        try {
                            Font defaultFont = UIManager.getFont("defaultFont");
                            if (defaultFont != null) {
                                UIManager.put("defaultFont", defaultFont.deriveFont(12f));
                            }
                            IntelliJTheme.setup(Start.class.getResourceAsStream(Engine.getConfiguration().getTheme()));
                        } catch (Exception e) {
                            System.err.println("Failed self assigned theme.");
                        }
                        i = mdf.length + 1;
                        Engine.load();
                        new Controller();
                    }
                }
            }else{
                String orgName = JOptionPane.showInputDialog("Organization Name");
                if (orgName != null && !orgName.trim().isEmpty()) {
                    Configuration newConfiguration = new Configuration(orgName);
                    Json.save(WINDOWS_SYSTEM_DIR + UUID.randomUUID() + ".xiix", newConfiguration);
                    Engine.setConfiguration(newConfiguration);
                    new Controller();
                    System.out.println("Configuration saved and controller initialized.");
                } else {
                    System.out.println("Operation canceled or invalid input.");
                }
            }
        }
    }
}