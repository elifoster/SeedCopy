package santa.seedcopy

import net.minecraft.command.ICommand
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.math.BlockPos
import net.minecraft.util.text.TextComponentString
import net.minecraft.util.text.translation.I18n
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.util.*

class SeedCopyCommand: ICommand {
    override fun execute(server: MinecraftServer, sender: ICommandSender, args: Array<out String>) {
        SeedCopyLogger.printLogMessage("About to be processed")
        val world = sender.entityWorld
        if (sender is EntityPlayer && !world.isRemote && checkPermission(server, sender)) {
            val seed = world.seed.toString()

            SeedCopyLogger.printLogMessage("About to copy to clipboard")
            copyString(seed)
            sender.addChatMessage(TextComponentString(I18n.translateToLocal("command.success")))
            SeedCopyLogger.printLogMessage(String.format("%s has been copied to the clipboard", seed))
        } else {
            sender.addChatMessage(TextComponentString(I18n.translateToLocal("command.failure")))
        }
    }

    fun copyString(string: String) {
        val selection = StringSelection(string)
        val clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(selection, null)
    }

    fun isPlayerOpped(server: MinecraftServer, name: String): Boolean {
        for (n in server.playerList?.oppedPlayerNames!!) {
            if (n.equals(name, ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    override fun checkPermission(server: MinecraftServer, sender: ICommandSender): Boolean {
        return Config.ONLY_ALLOW_OPS || isPlayerOpped(server, sender.name)
    }

    override fun getCommandName(): String? {
        return "copyseed"
    }

    override fun getTabCompletionOptions(server: MinecraftServer, sender: ICommandSender, args: Array<out String>, pos: BlockPos): MutableList<String>? {
        return null
    }

    override fun getCommandUsage(sender: ICommandSender): String? {
        return "copyseed"
    }

    override fun getCommandAliases(): MutableList<String> {
        return Collections.emptyList()
    }

    override fun isUsernameIndex(args: Array<out String>, index: Int): Boolean {
        return false
    }

    override fun compareTo(other: ICommand): Int {
        return 0
    }
}