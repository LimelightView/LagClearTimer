package rrunner.trackmod.keybindStuff;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class Keybinds
{
    private static KeyBinding toggleTimer;

    public static void register()
    {
        toggleTimer = new KeyBinding("key.toggleTimer", Keyboard.KEY_GRAVE, "key.categories.prison");
        ClientRegistry.registerKeyBinding(toggleTimer);
    }
}
