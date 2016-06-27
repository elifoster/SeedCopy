package santa.seedcopy

import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.common.event.FMLServerStartingEvent

@Mod(
  modid = SeedCopyMod.MOD_ID,
  name = SeedCopyMod.NAME,
  version = SeedCopyMod.VERSION,
  modLanguage = "kotlin",
  modLanguageAdapter = "io.drakon.forge.kotlin.KotlinAdapter")
object SeedCopyMod {
    const val NAME = "SeedCopy"
    const val MOD_ID = "seedcopy"
    const val VERSION = "3.0.0"

    @Mod.EventHandler
    fun preInit(event: FMLPreInitializationEvent) {
        Config.load(event)
    }

    @Mod.EventHandler
    fun onServerStart(event: FMLServerStartingEvent) {
        event.registerServerCommand(SeedCopyCommand())
        SeedCopyLogger.printLogMessage("SeedCopy command has been registered.")
    }
}