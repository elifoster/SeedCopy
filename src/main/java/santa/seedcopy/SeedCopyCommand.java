package santa.seedcopy;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.*;
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
        return "copyseed";
    }

    @Override
    public List getCommandAliases() {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        SeedCopyLogger.printLogMessage("About to be processed");

        World world = sender.getEntityWorld();
        if (sender instanceof EntityPlayer && !world.isRemote) {
            SeedCopyLogger.printLogMessage("About to copy to clipboard");

            String seed = Long.toString(world.getSeed());
            StringSelection selection = new StringSelection(seed);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("command.success")));
            SeedCopyLogger.printLogMessage(String.format("%s has been copied to the clipboard", seed));
        } else {
            sender.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("command.failure")));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender p_71516_1_, String[] p_71516_2_) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] p_82358_1_, int p_82358_2_) {
        return false;
    }
}
