package santa.seedcopy;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class Config {

    public static boolean enableLogging;
    public static boolean onlyAllowOps;

    public static void load(FMLPreInitializationEvent event) {
        File configurationDir = event.getModConfigurationDirectory();
        File configFile = new File(configurationDir, "SeedCopy.cfg");
        Configuration config = new Configuration(configFile);
        config.load();

        enableLogging = config.get("Toggle", "Enable debug logging", false).getBoolean();
        onlyAllowOps = config.get("Toggle", "When true, only operators (ops) will be able to use the command", false).getBoolean();

        config.save();
    }
}
