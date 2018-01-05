package pw.cinque.keystrokesmod;

import java.io.*;

public class KeystrokesSettings
{
    private final File file;
    private int x;
    private int y;
    private int textColor;
    private boolean showingMouseButtons;
    private boolean enabled;
    
    public KeystrokesSettings(final File file) {
        this.x = 0;
        this.y = 0;
        this.textColor = 0;
        this.showingMouseButtons = false;
        this.enabled = true;
        this.file = file;
    }
    
    public void load() throws IOException {
        try (final DataInputStream in = new DataInputStream(new FileInputStream(this.file))) {
            this.x = in.readShort();
            this.y = in.readShort();
            this.textColor = in.readInt();
            this.showingMouseButtons = in.readBoolean();
            this.enabled = in.readBoolean();
        }
    }
    
    public void save() throws FileNotFoundException, IOException {
        try (final DataOutputStream out = new DataOutputStream(new FileOutputStream(this.file, false))) {
            out.writeShort(this.x);
            out.writeShort(this.y);
            out.writeInt(this.textColor);
            out.writeBoolean(this.showingMouseButtons);
            out.writeBoolean(this.enabled);
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getTextColor() {
        return this.textColor;
    }
    
    public void setTextColor(final int textColor) {
        this.textColor = textColor;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isShowingMouseButtons() {
        return this.showingMouseButtons;
    }
    
    public void setShowingMouseButtons(final boolean showingMouseButtons) {
        this.showingMouseButtons = showingMouseButtons;
    }
}
