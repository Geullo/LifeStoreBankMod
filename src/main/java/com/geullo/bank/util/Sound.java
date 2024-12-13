package com.geullo.bank.util;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public class Sound {
    public static ISound getSound(SoundEvent soundEvent,SoundCategory soundCategory){
        return getSound(soundEvent,soundCategory,1.0f);
    }
    public static ISound getSound(SoundEvent soundEvent,SoundCategory soundCategory,float volume) {
        return getSound(soundEvent,soundCategory,volume,1.0f);
    }
    public static ISound getSound(SoundEvent soundEvent,SoundCategory soundCategory,float volume,float pitch){
        return new PositionedSoundRecord(soundEvent.getSoundName(), soundCategory, volume, pitch, false, 0, ISound.AttenuationType.NONE, 0.0F, 0.0F, 0.0F);
    }

    public static ISound getSound(SoundEvent soundEvent,SoundCategory soundCategory,float volume,float pitch,float x,float y,float z){
        return new PositionedSoundRecord(soundEvent.getSoundName(), soundCategory, volume, pitch, false, 0, ISound.AttenuationType.NONE, x, y, z);
    }
}
