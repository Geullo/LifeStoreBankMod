package com.geullo.bank;

import com.geullo.bank.proxy.CommonProxy;
import com.geullo.bank.util.MODSoundCategory;
import com.geullo.bank.util.Reference;
import com.geullo.bank.util.SoundEffect;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME)
public class Main {
    @Instance
    public static Main instacne;

    @SidedProxy(clientSide = Reference.CLIENTPROXY,serverSide = Reference.COMMONPROXY)
    public static CommonProxy proxy;
    public static Logger logger;
    public static SoundCategory LIFESTORE_BACKGROUND_MUSIC;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IOException {
        logger = event.getModLog();
        proxy.preInit();
    }
    @EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init();
        LIFESTORE_BACKGROUND_MUSIC = MODSoundCategory.add("lifestore");
        System.out.println("GeulloPre :: " + LIFESTORE_BACKGROUND_MUSIC);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit();
    }

    @Mod.EventBusSubscriber
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
            SoundEffect.registerSounds(event.getRegistry());
        }

    }
}
