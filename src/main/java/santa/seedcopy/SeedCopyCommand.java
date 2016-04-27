package santa.seedcopy;

import net.minecraft.client.Minecraft;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Collections;
import java.util.List;

public class SeedCopyCommand implements ICommand {
    @Override
    public String getCommandName() {
        return "copyseed";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "copyseed";
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) {
        SeedCopyLogger.printLogMessage("About to be processed");
        World world = sender.getEntityWorld();
        if (sender instanceof EntityPlayer && !world.isRemote && checkPermission(server, sender)) {
            String seed = Long.toString(world.getSeed());

            SeedCopyLogger.printLogMessage("About to copy to clipboard");
            copyString(seed);
            sender.addChatMessage(new TextComponentString(I18n.translateToLocal("command.success")));
            SeedCopyLogger.printLogMessage(String.format("%s has been copied to the clipboard", seed));
        } else {
            sender.addChatMessage(new TextComponentString(I18n.translateToLocal("command.failure")));
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return !Config.onlyAllowOps || isPlayerOpped(server, sender.getName());
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos pos) {
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

    public boolean isPlayerOpped(MinecraftServer server, String name) {
        for (String n : server.getPlayerList().getOppedPlayerNames()) {
            if (n.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
