package pw.cinque.keystrokesmod;

import net.minecraft.server.*;
import net.minecraft.command.*;

public class CommandKeystrokes extends CommandBase
{
    public String getName() {
        return "keystrokesmod";
    }
    
    public void execute(final MinecraftServer server, final ICommandSender sender, final String[] args) throws CommandException {
        KeystrokesMod.openGui();
    }
    
    public String getUsage(final ICommandSender sender) {
        return "/keystrokesmod";
    }
    
    public int getRequiredPermissionLevel() {
        return 0;
    }
    
    public boolean canCommandSenderUseCommand(final ICommandSender sender) {
        return true;
    }
}
