package rrunner.trackmod.guiStuff;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class LagClearGUI extends Gui
{
    public LagClearGUI(Minecraft mc, String name, String hexColor)
    {
        mc.fontRendererObj.drawStringWithShadow(name, 0, 0, Integer.parseInt(hexColor, 16));
    }
}
