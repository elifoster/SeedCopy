package santa.seedcopy

import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import java.io.File

object Config {
    var ENABLE_LOGGING = false;
    var ONLY_ALLOW_OPS = false;

    fun load(event: FMLPreInitializationEvent) {
        val configurationDir = event.modConfigurationDirectory
        val configFile = File(configurationDir, "SeedCopy.cfg")
        val config = Configuration(configFile)
        config.load()

        ENABLE_LOGGING = config.get("Toggle", "Enable debug logging", false).boolean
        ONLY_ALLOW_OPS = config.get("Toggle", "When true, only operators (ops) will be able to use the command", false).boolean

        config.save()
    }
}