package santa.seedcopy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.stream.ChatController;
import net.minecraft.client.stream.TwitchStream;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
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
        if (Config.enableTwitchInteraction) {
            return "copyseed <twitch (optional)>";
        } else {
            return "copyseed";
        }
    }

    @Override
    public List<String> getCommandAliases() {
        return Collections.emptyList();
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        SeedCopyLogger.printLogMessage("About to be processed");
        World world = sender.getEntityWorld();
        if (sender instanceof EntityPlayer && !world.isRemote && canCommandSenderUseCommand(sender)) {
            String seed = Long.toString(world.getSeed());

            if (Config.enableTwitchInteraction && Minecraft.getMinecraft().getTwitchStream()
              instanceof TwitchStream && args.length > 0 && args[0].equals("twitch")) {
                String worldName = world.getWorldInfo().getWorldName();
                String message = StatCollector.translateToLocalFormatted("command.twitch", worldName, seed);
                ChatController chat = new ChatController();
                chat.func_175984_n();
                chat.func_175986_a(sender.getName(), message);
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
        return !Config.onlyAllowOps || isPlayerOpped(player);
    }

    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
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
        return MinecraftServer.getServer().getConfigurationManager().canSendCommands(player.getGameProfile());
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public int compareTo(ICommand o) {
        return 0;
    }
}
