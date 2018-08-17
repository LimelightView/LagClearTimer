package rrunner.trackmod.guiStuff;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import rrunner.trackmod.util.Util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RenderGuiHandler
{

    private static String LAG_CLEAR_MSG = "[MCC] All ground items removed!"; // Static string to represent the message to be found
    private static String hexColor = "32CD32";                               // Color of the timer string
    private static int clearTime = 460000;                                   // 7 Minutes and 40 Seconds
    private long lastLagClear = 0L;                                          // Long tracking the time since the last lag clear
    private static boolean showTimer = true;                                 // Boolean controlling whether the timer should be rendered

    private static final String HEX_COLORS[] =
            {"AA0000", "FF5555", "FFAA00", "FFFF55", "00AA00", "55FF55",
            "55FFFF", "00AAAA", "0000AA", "FF55FF", "AA00AA",
            "FFFFFF", "AAAAAA", "555555", "000000"};
    private static final String hexNames[] =
            {"DARK_RED", "RED", "GOLD", "YELLOW", "DARK_GREEN", "GREEN",
            "AQUA", "DARK_AQUA", "BLUE", "LIGHT_PURPLE", "DARK_PURPLE",
            "WHITE", "GRAY", "DARK_GRAY", "BLACK"};
    private static final Set<String> HEX_NAMES = new HashSet<String>(Arrays.asList(hexNames));

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Post event)
    {
        final String NAME_STRING = "Next Lag Clear: ";            // Static string to represent the beginning of the string
        String baseName;                                          // Dynamic name to track the time left

        // Checks whether it should render the text
        if ((event.type != RenderGameOverlayEvent.ElementType.ALL)) {
            return;
        }
        // Checks if the time for the last lag clear is known
        if (this.lastLagClear == 0L)
        {
            baseName = NAME_STRING + "Unknown";
        }
        else
        {
            int time = (int)((clearTime - (System.currentTimeMillis() - this.lastLagClear)) / 1000L);
            int seconds = time % 60;
            int totalMinutes = time / 60;
            int minutes = totalMinutes % 600;
            int hours = totalMinutes / 600;

            // Converts the time left into text
            baseName = (NAME_STRING + (hours > 0 ? hours + "h, " : "") + (minutes > 0 ? minutes + "m, " : "") + (seconds > 0 ? seconds + "s" : "0s"));
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
        if (message.equals(LAG_CLEAR_MSG)) {              // Checks if it is the lag clear
            this.lastLagClear = System.currentTimeMillis();  // Resets the time of the last lag clear
        }
    }

    public static void setHexColor(String newHexColor)
    {
        newHexColor = newHexColor.toUpperCase();                 // Changes newHexColor to all caps
        if(HEX_NAMES.contains(newHexColor))                      // Checks if newHexColor is in HEX_NAMES
        {
            for(int count = 0; count < hexNames.length; count++) // Loops through hexNames
            {
                if(newHexColor.equals(hexNames[count]))          // Checks if the current color is the target color
                {
                    hexColor = HEX_COLORS[count];                // Sets the hexColor to the current color
                }
            }
        }else if(newHexColor.length() == 6 && Util.isHex(newHexColor)) // Checks if the newHexColor is hexadecimal
        {
            hexColor = newHexColor;
        }
    }

    public static Set<String> getHexNames()
    {
        return HEX_NAMES;
    }

    public static void setLagClearMSG(String msg)
    {
        LAG_CLEAR_MSG = msg;
    }

    public static void setClearTime(int time)
    {
        clearTime = time;
    }

    public static void flipShowTimer()
    {
        showTimer = !showTimer;
    }
}
