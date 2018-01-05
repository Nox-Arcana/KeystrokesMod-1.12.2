package pw.cinque.keystrokesmod;

import net.minecraftforge.fml.common.event.*;
import net.minecraft.client.*;
import java.util.logging.*;
import java.io.*;
import net.minecraftforge.client.*;
import net.minecraft.command.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.gameevent.*;
import pw.cinque.keystrokesmod.render.*;
import net.minecraft.client.gui.*;
import net.minecraftforge.fml.common.eventhandler.*;

@Mod(name = "KeystrokesMod", modid = "keystrokesmod", version = "v3", acceptedMinecraftVersions = "[1.12.2]")
public class KeystrokesMod
{
    private static KeystrokesSettings settings;
    private static KeystrokesRenderer renderer;
    private static boolean openGui;
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        KeystrokesMod.settings = new KeystrokesSettings(new File(Minecraft.getMinecraft().mcDataDir, "keystrokes.dat"));
        KeystrokesMod.renderer = new KeystrokesRenderer();
        try {
            KeystrokesMod.settings.load();
        }
        catch (IOException e) {
            Logger.getLogger("KeystrokesMod").warning("Failed to load Keystrokes settings file!");
            e.printStackTrace();
        }
        ClientCommandHandler.instance.registerCommand((ICommand)new CommandKeystrokes());
        FMLCommonHandler.instance().bus().register((Object)KeystrokesMod.renderer);
        FMLCommonHandler.instance().bus().register((Object)this);
    }
    
    @SubscribeEvent
    public void onClientTick(final TickEvent.ClientTickEvent event) {
        if (KeystrokesMod.openGui) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuiScreenKeystrokes());
            KeystrokesMod.openGui = false;
        }
    }
    
    public static KeystrokesSettings getSettings() {
        return KeystrokesMod.settings;
    }
    
    public static KeystrokesRenderer getRenderer() {
        return KeystrokesMod.renderer;
    }
    
    public static void openGui() {
        KeystrokesMod.openGui = true;
    }
}
