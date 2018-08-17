package rrunner.trackmod.main;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rrunner.trackmod.commandStuff.CommandChangeColor;
import rrunner.trackmod.guiStuff.RenderGuiHandler;
import rrunner.trackmod.keybindStuff.KeyInputHandler;
import rrunner.trackmod.keybindStuff.Keybinds;
import rrunner.trackmod.proxy.CommonProxy;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class LagClearMain
{
    @SidedProxy(serverSide = Reference.SERVER_PROXY_CLASS, clientSide = Reference.CLIENT_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.Instance(Reference.MODID)
    public static LagClearMain instance;

    public static final Logger logger = LogManager.getLogger(Reference.MODID);

    // Register capabilities / create and read configuration / Add client side commands
    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        try{
            instance = this;
            MinecraftForge.EVENT_BUS.register(this);
            Keybinds.register();
            ClientCommandHandler.instance.registerCommand(new CommandChangeColor());
        }catch(Exception e)
        {
            System.out.println("An exception occurred in preInit(). Stacktrace below:");
            e.printStackTrace();
        }
    }

    // Register world generators, event handlers, and sending IMC messages
    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());
    }

    // Do things that rely on other mods
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
}
