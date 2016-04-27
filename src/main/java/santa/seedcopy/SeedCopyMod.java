package santa.seedcopy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(name = SeedCopyMod.NAME, modid = SeedCopyMod.MODID, version = SeedCopyMod.VERSION)
public class SeedCopyMod {
    public static final String NAME = "SeedCopy";
    public static final String MODID = "seedcopy";
    public static final String VERSION = "3.0.0";

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event);
    }

    @Mod.EventHandler
    public void onServerStart(FMLServerStartingEvent event) {
        event.registerServerCommand(new SeedCopyCommand());
        SeedCopyLogger.printLogMessage("SeedCopy command has been registered.");
    }
}
