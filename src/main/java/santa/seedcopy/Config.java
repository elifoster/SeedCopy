package santa.seedcopy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import net.minecraftforge.common.config.Configuration;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Config {

    public static boolean enableLogging;
    public static boolean onlyAllowOps;
    public static boolean enableTwitchInteraction;

    public static void load(FMLPreInitializationEvent event) {
        File configurationDir = ReflectionHelper.getPrivateValue(FMLPreInitializationEvent.class, event, 2);
        File oldConfigFile = new File(configurationDir, "SeedCopy.cfg");
        if (oldConfigFile.exists()) {
            try {
                FileUtils.copyFile(new File(configurationDir, "SeedCopy.cfg"), new File(configurationDir, "SeedCopy.cfg"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            oldConfigFile.delete();
        }
        Configuration config = new Configuration(new File(configurationDir, "SeedCopy.cfg"));
        config.load();

        enableLogging = config.get("Toggle", "Enable debug logging", false).getBoolean(false);
        onlyAllowOps = config.get("Toggle", "When true, only operators (ops) will be able to use the command", false).getBoolean(false);
        enableTwitchInteraction = config.get("Toggle", "Enable Twitch feature (see wiki)", true).getBoolean(true);

        config.save();
    }
}
