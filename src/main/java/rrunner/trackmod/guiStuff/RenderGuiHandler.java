package rrunner.trackmod.guiStuff;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rrunner.trackmod.util.Util;
import scala.actors.threadpool.Arrays;

import java.util.HashSet;
import java.util.Set;

public class RenderGuiHandler
{
    public final String nameString = "Next Lag Clear: ";            // Static string to represent the beginning of the string
    private final String lagClearMsg = "[MCC] All ground items removed!"; // Static string to represent the message to be found
    public long lastLagClear = 0L;                                  // Long tracking the time since the last lag clear
    public int LAG_TIME = 460000;                                   // 7 Minutes and 40 Seconds
    public String baseName = "Next Lag Clear: ";                    // Dynamic name to track the time left
    public static boolean showTimer = true;                         // Boolean controlling whether the timer should be rendered
    protected static String hexColor = "32CD32";                    // Color of the timer string
    public enum colors
    {
        DARK_RED, RED, GOLD, YELLOW, DARK_GREEN, GREEN, AQUA,
        DARK_AQUA, BLUE, LIGHT_PURPLE, DARK_PURPLE, WHITE,
        GRAY, DARK_GRAY, BLACK;
    };

    private static final String HEX_COLORS[] =
            {"AA0000", "FF5555", "FFAA00", "FFFF55", "00AA00", "55FF55",
            "55FFFF", "00AAAA", "0000AA", "FF55FF", "AA00AA",
            "FFFFFF", "AAAAAA", "555555", "000000"};
    private static final String hexNames[] =
            {"DARK_RED", "RED", "GOLD", "YELLOW", "DARK_GREEN", "GREEN",
            "AQUA", "DARK_AQUA", "BLUE", "LIGHT_PURPLE", "DARK_PURPLE",
            "WHITE", "GRAY", "DARK_GRAY", "BLACK"};
    private static final Set<String> HEX_NAMES = new HashSet<String>(Arrays.asList(
            new String[] {"DARK_RED", "RED", "GOLD", "YELLOW", "DARK_GREEN", "GREEN",
                    "AQUA", "DARK_AQUA", "BLUE", "LIGHT_PURPLE", "DARK_PURPLE",
                    "WHITE", "GRAY", "DARK_GRAY", "BLACK"}));

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event)
    {
        // Checks whether it should render the text
        if ((event.type != RenderGameOverlayEvent.ElementType.ALL)) {
            return;
        }
        // Checks if the time for the last lag clear is known
        if (this.lastLagClear == 0L)
        {
            this.baseName = nameString + "Unknown";
        }
        else
        {
            int time = (int)((LAG_TIME - (System.currentTimeMillis() - this.lastLagClear)) / 1000L);
            int seconds = time % 60;
            int totalMinutes = time / 60;
            int minutes = totalMinutes % 600;
            int hours = totalMinutes / 600;

            // Converts the time left into text
            this.baseName = (nameString + (hours > 0 ? hours + "h, " : "") + (minutes > 0 ? minutes + "m, " : "") + (seconds > 0 ? seconds + "s" : "0s"));
        }
        if(showTimer)
        {
            new LagClearGUI(Minecraft.getMinecraft(), baseName, hexColor); // Displays the text
        }
    }

    // Fires when a message is seen in chat
    @SubscribeEvent
    public void onClientChat(ClientChatReceivedEvent event)
    {
        String message = event.message.getUnformattedText(); // Grabs the message from the chat
        if (message.equals(this.lagClearMsg)) {              // Checks if it is the lag clear
            this.lastLagClear = System.currentTimeMillis();  // Resets the time of the last lag clear
        }
    }

    public static void setHexColor(String newHexColor)
    {
        newHexColor = newHexColor.toUpperCase();
        if(HEX_NAMES.contains(newHexColor))
        {
            for(int count = 0; count < hexNames.length; count++)
            {
                if(newHexColor.equals(hexNames[count]))
                {
                    hexColor = HEX_COLORS[count];
                }
            }
        }else if(newHexColor.length() == 6 && Util.isHex(newHexColor))
        {
            hexColor = newHexColor;
        }else
        {
            return;
        }
    }

    public static Set<String> getHexNames()
    {
        return HEX_NAMES;
    }
}
