package com.geullo.bank.util;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
public class MODSoundCategory
{

    private static final String SRG_soundLevels = "field_186714_aM";
    private static final String SRG_SOUND_CATEGORIES = "field_187961_k";
    private static Map<SoundCategory, Float> soundLevels;
    private static MODSoundCategory instance = new MODSoundCategory();

    private MODSoundCategory() {}

    public static MODSoundCategory getInstance() {return instance;}

    public static SoundCategory add(String name)
    {
        Map<String, SoundCategory> SOUND_CATEGORIES;
        String constantName;
        String referenceName;
        SoundCategory soundCategory;
        constantName = name.toUpperCase().replace(" ", "");
        referenceName = constantName.toLowerCase();
        soundCategory =  EnumHelper.addEnum(SoundCategory.class , constantName, new Class[]{String.class}, new Object[]{referenceName});
        System.out.println("GeulloSoundCt :: "+soundCategory);
        SOUND_CATEGORIES = ObfuscationReflectionHelper.getPrivateValue(SoundCategory.class, SoundCategory.VOICE ,"SOUND_CATEGORIES", SRG_SOUND_CATEGORIES);
        if (SOUND_CATEGORIES.containsKey(referenceName)) throw new Error("Clash in Sound Category name pools! Cannot insert " + constantName);
        SOUND_CATEGORIES.put(referenceName, soundCategory);
        if (FMLLaunchHandler.side() == Side.CLIENT) setSoundLevels();
        return soundCategory;
    }

    @SideOnly(Side.CLIENT)
    private static void setSoundLevels()
    {
        soundLevels = Maps.newEnumMap(SoundCategory.class);
        ObfuscationReflectionHelper.setPrivateValue(GameSettings.class, Minecraft.getMinecraft().gameSettings, soundLevels, "soundLevels", SRG_soundLevels);
    }

}