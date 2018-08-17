package rrunner.trackmod.commandStuff;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import rrunner.trackmod.guiStuff.RenderGuiHandler;
import rrunner.trackmod.util.Util;

import java.util.ArrayList;
import java.util.List;

public class CommandChangeColor implements ICommand
{
    private final List aliases;

    public CommandChangeColor()
    {
        aliases = new ArrayList();
        aliases.add("color");

    }

    @Override
    public String getCommandName()
    {
        return "Color_Change";
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "/color <DEFAULT_COLOR | HEXADECIMAL | \"COLORS\">";
    }

    @Override
    public List<String> getCommandAliases()
    {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        World world = sender.getEntityWorld();
        if(args.length > 0)                                                            // Checks if an argument was provided
        {
            String color = args[0];
            if("help".equals(color))
            {
                sender.addChatMessage(new ChatComponentText(getCommandUsage(sender))); // Sends the command usage to the player
            }
            else if("colors".equals(color))
            {
                chatColors(sender);                                                    // Sends a list of colors to the user
            }
            else if(!RenderGuiHandler.getHexNames().contains(color.toUpperCase()))     // Checks if the color is in the default color list
            {
                if(args[0].length() != 6 || !Util.isHex(color.toUpperCase()))          // Checks if the color is hexadecimal
                {
                    sender.addChatMessage(new ChatComponentText("Color is not a default color or Hexadecimal."));
                    return;
                }else
                {
                    ChangeColor(sender, color);
                }
            }
            else if(!color.isEmpty())
            {
                ChangeColor(sender, color);
            }
        }
        else
        {
            sender.addChatMessage(new ChatComponentText(getCommandUsage(sender)));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender)
    {
        return true;
    }

    /*
        @Description   : Adds the default colors to the command's autocomplete
     */
    @Override
    public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos)
    {
        List<String> tabCompletionOptions = new ArrayList<String>();
        for(int i = 0; i < RenderGuiHandler.getHexNames().size(); i++)
        {
            tabCompletionOptions.add(RenderGuiHandler.getHexNames().toArray()[i].toString());
        }
        System.out.println(tabCompletionOptions);
        return tabCompletionOptions;
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index)
    {
        return false;
    }

    @Override
    public int compareTo(ICommand o) { return 0; }

    /*
        @Param sender: The user (ICommandSender) that sent the command
        @Param color : The string representing the color to be used
        @Description : Changes the color of the GUI text
     */
    public void ChangeColor(ICommandSender sender, String color)
    {
        sender.addChatMessage(new ChatComponentText("Changing color to " + color));
        RenderGuiHandler.setHexColor(color);
        RenderGuiHandler.flipShowTimer();
        RenderGuiHandler.flipShowTimer();
    }

    /*
        @Param sender: The user (ICommandSender) that sent the command
        @Description : Sends a color coded list of default colors to the user
     */
    public void chatColors(ICommandSender sender)
    {
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "Default Colors: "));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_RED + "    Dark_Red"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "    Red"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GOLD + "    Gold"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.YELLOW + "    Yellow"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GREEN + "    Dark_Green"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GREEN + "    Green"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + "    Aqua"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_AQUA + "    Dark_Aqua"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.BLUE + "    Blue"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.LIGHT_PURPLE + "    Light_Purple"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_PURPLE + "    Dark_Purple"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.WHITE + "    White"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.GRAY + "    Gray"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.DARK_GRAY + "    Dark_Gray"));
        sender.addChatMessage(new ChatComponentText(EnumChatFormatting.BLACK + "    Black"));
    }
}
