package pw.cinque.keystrokesmod;

import net.minecraft.client.*;
import net.minecraft.client.settings.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;

public class Key
{
    private final Minecraft mc;
    private final KeyBinding key;
    private final int xOffset;
    private final int yOffset;
    private boolean wasPressed;
    private long lastPress;
    private int color;
    private double textBrightness;
    
    public Key(final KeyBinding key, final int xOffset, final int yOffset) {
        this.mc = Minecraft.getMinecraft();
        this.wasPressed = true;
        this.lastPress = 0L;
        this.color = 255;
        this.textBrightness = 1.0;
        this.key = key;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    public void renderKey(final int x, final int y, final int textColor) {
        final boolean pressed = this.key.isKeyDown();
        final String name = Keyboard.getKeyName(this.key.getKeyCode());
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
        }
        if (pressed) {
            this.color = Math.min(255, (int)(2L * (System.currentTimeMillis() - this.lastPress)));
            this.textBrightness = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.lastPress) / 20.0);
        }
        else {
            this.color = Math.max(0, 255 - (int)(2L * (System.currentTimeMillis() - this.lastPress)));
            this.textBrightness = Math.min(1.0, (System.currentTimeMillis() - this.lastPress) / 20.0);
        }
        Gui.drawRect(x + this.xOffset, y + this.yOffset, x + this.xOffset + 22, y + this.yOffset + 22, 2013265920 + (this.color << 16) + (this.color << 8) + this.color);
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        this.mc.fontRenderer.drawString(name, x + this.xOffset + 8, y + this.yOffset + 8, -16777216 + ((int)(red * this.textBrightness) << 16) + ((int)(green * this.textBrightness) << 8) + (int)(blue * this.textBrightness));
    }
}
