package santa.seedcopy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import tv.twitch.chat.ChatMessage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

public class SeedCopyCommand implements ICommand {

    @Override
    public int compareTo(Object object) {
        return 0;
    }

    @Override
    public String getCommandName() {
        return "copyseed";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        if (Config.enableTwitchInteraction) {
            return "copyseed <twitch (optional)>";
        } else {
            return "copyseed";
        }
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        SeedCopyLogger.printLogMessage("About to be processed");
        World world = sender.getEntityWorld();
        if (sender instanceof EntityPlayer && !world.isRemote && canCommandSenderUseCommand(sender)) {
            String seed = Long.toString(world.getSeed());

            if (Config.enableTwitchInteraction && Minecraft.getMinecraft().func_152346_Z() instanceof TwitchStream && args[0] == "twitch") {
                String worldName = world.getWorldInfo().getWorldName();
                String message = StatCollector.translateToLocalFormatted("command.twitch", new Object[] { worldName, seed });
                ChatController chat = new ChatController();
                chat.func_152991_c();
                chat.func_152992_g(message);
            }

            SeedCopyLogger.printLogMessage("About to copy to clipboard");
            copyString(seed);
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("command.success")));
            SeedCopyLogger.printLogMessage(String.format("%s has been copied to the clipboard", seed));
        } else {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("command.failure")));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        EntityPlayer player = (EntityPlayer) sender;
        if (Config.onlyAllowOps) {
            return isPlayerOpped(player);
        } else {
            return true;
        }
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] strings) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }

    public void copyString(String string) {
        StringSelection selection = new StringSelection(string);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    public boolean isPlayerOpped(EntityPlayer player) {
        return MinecraftServer.getServer().getConfigurationManager().func_152596_g(player.getGameProfile());
    }
}
