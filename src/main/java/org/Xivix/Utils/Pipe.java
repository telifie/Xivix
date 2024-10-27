package org.Xivix.Utils;

import org.Xivix.Start;
import java.io.File;
import java.util.UUID;

public class Pipe {

    public static File[] list(String dir){
        File thisFile = new File(Start.WINDOWS_SYSTEM_DIR + "\\.store\\" + dir);
        if(!thisFile.exists()){
            thisFile.mkdirs(); //May have to redo because it may make folders for object IDs like before
        }
        return new File(Start.WINDOWS_SYSTEM_DIR + "\\.store\\" + dir).listFiles();
    }

    public static void save(String dir, Object o){
        File f = new File(Start.WINDOWS_SYSTEM_DIR + "\\.store\\" + dir);
        if(!f.exists()){
            f.mkdirs();
        }
        Json.save(Start.WINDOWS_SYSTEM_DIR + "\\.store\\" + dir + "\\" + UUID.randomUUID() + dir.replace("/", ".").toLowerCase(), o);
    }

    public static void saveConfiguration(String theme) {
        File md = new File(Start.WINDOWS_SYSTEM_DIR);
        File[] mdf = md.listFiles();
        if (mdf != null && mdf.length > 0) {
            for (int i = 0; i < mdf.length; i++) {
                File file = mdf[i];
                if (file.getPath().endsWith(".cnl.mfg")) {
                    Configuration newConfig = new Configuration(Engine.getConfiguration().getInstance_name());
                    newConfig.setTheme(theme);
                    Json.save(file.getPath(), newConfig);
                    break;
                }
            }
        }
    }
}
