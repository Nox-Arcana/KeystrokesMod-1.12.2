package pw.cinque.keystrokesmod.render;

import net.minecraft.client.gui.*;
import pw.cinque.keystrokesmod.*;
import java.io.*;
import java.util.logging.*;

public class GuiScreenKeystrokes extends GuiScreen
{
    private static final String[] COLOR_NAMES;
    private GuiButton buttonEnabled;
    private GuiButton buttonShowMouseButtons;
    private GuiButton buttonTextColor;
    private boolean dragging;
    private int lastMouseX;
    private int lastMouseY;
    
    public GuiScreenKeystrokes() {
        this.dragging = false;
    }
    
    public void initGui() {
        final KeystrokesSettings settings = KeystrokesMod.getSettings();
        this.buttonList.add(this.buttonEnabled = new GuiButton(0, this.width / 2 - 70, this.height / 2 - 28, 140, 20, "Enabled: " + (settings.isEnabled() ? "On" : "Off")));
        this.buttonList.add(this.buttonShowMouseButtons = new GuiButton(1, this.width / 2 - 70, this.height / 2 - 6, 140, 20, "Show mouse buttons: " + (settings.isShowingMouseButtons() ? "On" : "Off")));
        this.buttonList.add(this.buttonTextColor = new GuiButton(2, this.width / 2 - 70, this.height / 2 + 16, 140, 20, "Text color: " + GuiScreenKeystrokes.COLOR_NAMES[settings.getTextColor()]));
    }
    
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        KeystrokesMod.getRenderer().renderKeystrokes();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    protected void actionPerformed(final GuiButton button) {
        final KeystrokesSettings settings = KeystrokesMod.getSettings();
        switch (button.id) {
            case 0: {
                settings.setEnabled(!settings.isEnabled());
                this.buttonEnabled.displayString = "Enabled: " + (settings.isEnabled() ? "On" : "Off");
            }
            case 1: {
                settings.setShowingMouseButtons(!settings.isShowingMouseButtons());
                this.buttonShowMouseButtons.displayString = "Show mouse buttons: " + (settings.isShowingMouseButtons() ? "On" : "Off");
            }
            case 2: {
                settings.setTextColor((settings.getTextColor() == 6) ? 0 : (settings.getTextColor() + 1));
                this.buttonTextColor.displayString = "Text color: " + GuiScreenKeystrokes.COLOR_NAMES[settings.getTextColor()];
                break;
            }
        }
    }
    
    protected void mouseClicked(final int mouseX, final int mouseY, final int button) throws IOException {
        super.mouseClicked(mouseX, mouseY, button);
        if (button != 0) {
            return;
        }
        final KeystrokesSettings settings = KeystrokesMod.getSettings();
        final int startX = settings.getX();
        final int startY = settings.getY();
        final int endX = startX + 74;
        final int endY = startY + (settings.isShowingMouseButtons() ? 74 : 50);
        if (mouseX >= startX && mouseX <= endX && mouseY >= startY && mouseY <= endY) {
            this.dragging = true;
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
        }
    }
    
    protected void mouseMovedOrUp(final int mouseX, final int mouseY, final int action) {
        this.mouseMovedOrUp(mouseX, mouseY, action);
        this.dragging = false;
    }
    
    protected void mouseClickMove(final int mouseX, final int mouseY, final int lastButtonClicked, final long timeSinceMouseClick) {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        if (!this.dragging) {
            return;
        }
        final KeystrokesSettings settings = KeystrokesMod.getSettings();
        settings.setX(settings.getX() + mouseX - this.lastMouseX);
        settings.setY(settings.getY() + mouseY - this.lastMouseY);
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
    }
    
    public void onGuiClosed() {
        try {
            KeystrokesMod.getSettings().save();
        }
        catch (IOException e) {
            Logger.getLogger("KeystrokesMod").warning("Failed to save Keystrokes settings file!");
            e.printStackTrace();
        }
    }
    
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    static {
        COLOR_NAMES = new String[] { "White", "Red", "Green", "Blue", "Yellow", "Purple", "Rainbow" };
    }
}
