package pw.cinque.keystrokesmod;

import net.minecraft.client.*;
import java.util.*;
import org.lwjgl.input.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;

public class MouseButton
{
    private static final String[] BUTTONS;
    private final Minecraft mc;
    private final int button;
    private final int xOffset;
    private final int yOffset;
    private List<Long> clicks;
    private boolean wasPressed;
    private long lastPress;
    private int color;
    private double textBrightness;
    
    public MouseButton(final int button, final int xOffset, final int yOffset) {
        this.mc = Minecraft.getMinecraft();
        this.clicks = new ArrayList<Long>();
        this.wasPressed = true;
        this.lastPress = 0L;
        this.color = 255;
        this.textBrightness = 1.0;
        this.button = button;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    private int getCPS() {
        final long time = System.currentTimeMillis();
        final Iterator<Long> iterator = this.clicks.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() + 1000L < time) {
                iterator.remove();
            }
        }
        return this.clicks.size();
    }
    
    public void renderMouseButton(final int x, final int y, final int textColor) {
        final boolean pressed = Mouse.isButtonDown(this.button);
        final String name = MouseButton.BUTTONS[this.button];
        if (pressed != this.wasPressed) {
            this.wasPressed = pressed;
            this.lastPress = System.currentTimeMillis();
            if (pressed) {
                this.clicks.add(this.lastPress);
            }
        }
        if (pressed) {
            this.color = Math.min(255, (int)(2L * (System.currentTimeMillis() - this.lastPress)));
            this.textBrightness = Math.max(0.0, 1.0 - (System.currentTimeMillis() - this.lastPress) / 20.0);
        }
        else {
            this.color = Math.max(0, 255 - (int)(2L * (System.currentTimeMillis() - this.lastPress)));
            this.textBrightness = Math.min(1.0, (System.currentTimeMillis() - this.lastPress) / 20.0);
        }
        Gui.drawRect(x + this.xOffset, y + this.yOffset, x + this.xOffset + 34, y + this.yOffset + 22, 2013265920 + (this.color << 16) + (this.color << 8) + this.color);
        final int red = textColor >> 16 & 0xFF;
        final int green = textColor >> 8 & 0xFF;
        final int blue = textColor & 0xFF;
        this.mc.fontRenderer.drawString(name, x + this.xOffset + 8, y + this.yOffset + 4, -16777216 + ((int)(red * this.textBrightness) << 16) + ((int)(green * this.textBrightness) << 8) + (int)(blue * this.textBrightness));
        final String cpsText = this.getCPS() + " CPS";
        final int cpsTextWidth = this.mc.fontRenderer.getStringWidth(cpsText);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        this.mc.fontRenderer.drawString(cpsText, (x + this.xOffset + 17) * 2 - cpsTextWidth / 2, (y + this.yOffset + 14) * 2, -16777216 + ((int)(255.0 * this.textBrightness) << 16) + ((int)(255.0 * this.textBrightness) << 8) + (int)(255.0 * this.textBrightness));
        GL11.glScalef(2.0f, 2.0f, 2.0f);
    }
    
    static {
        BUTTONS = new String[] { "LMB", "RMB" };
    }
}
