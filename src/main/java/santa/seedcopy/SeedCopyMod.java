package santa.seedcopy;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;

@Mod(name = SeedCopyMod.NAME, modid = SeedCopyMod.MODID, version = SeedCopyMod.VERSION)
public class SeedCopyMod {

    public static final String NAME = "SeedCopy";
    public static final String MODID = "seedcopy";
    public static final String VERSION = "1.1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event);
    }

    @EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new SeedCopyCommand());
        SeedCopyLogger.printLogMessage("SeedCopy command has been registered.");
    }
}
