package rrunner.trackmod.keybindStuff;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import rrunner.trackmod.guiStuff.RenderGuiHandler;

public class KeyInputHandler
{
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if(Keybinds.toggleTimer.isPressed())
        {
            RenderGuiHandler.showTimer = !RenderGuiHandler.showTimer;
        }
    }
}
