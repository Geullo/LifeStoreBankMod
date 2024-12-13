package com.geullo.bank.proxy;

import com.geullo.bank.Events.RightClickItem;
import com.geullo.bank.Keybind.CallingKey;
import com.geullo.bank.Keybind.RegisterKeybind;
import com.geullo.bank.Packet;
import com.geullo.bank.util.OnlineImageResource;
import com.geullo.bank.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ClientProxy extends CommonProxy{
    public static SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("lifeStore4");
    //public static File liveSkinFolder = new File(Minecraft.getMinecraft().mcDataDir, "resources/SKIN");
    //private final String url = "https://mc-heads.net/avatar/",resolution = "100";

    @Override
    public void preInit() throws IOException {
        //liveSkinFolder.mkdirs();
    }

    @Override
    public void init() {
        FMLCommonHandler.instance().bus().register(new CallingKey());
        //List<IResourcePack> defaultResourcePacks = ObfuscationReflectionHelper.getPrivateValue(FMLClientHandler.class, FMLClientHandler.instance(), "resourcePackList");
        //IResourcePack pack = new OnlineImageResource(liveSkinFolder);
        //defaultResourcePacks.add(pack);
        //((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).reloadResourcePack(pack);
        NETWORK.registerMessage(Packet.Handle.class, Packet.class, 0, Side.CLIENT);
        ClientRegistry.registerKeyBinding(RegisterKeybind.callingAccept);
        ClientRegistry.registerKeyBinding(RegisterKeybind.callingDeny);
    }

    @Override
    public void postInit() {
        FMLCommonHandler.instance().bus().register(new RightClickItem());
    }

}
