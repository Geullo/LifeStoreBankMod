package com.geullo.bank.util;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;
@SuppressWarnings("WeakerAccess")
public class SoundEffect {
    public static SoundEvent CALLING;
    public static SoundEvent PHONE_CALL_CONNECTION;
    public static SoundEvent HALDESU;
    public static SoundEvent PIOSA;
    public static SoundEvent POPO;
    public static SoundEvent EQUELE;
    public static SoundEvent BERENTY;
    public static SoundEvent MAIN;
    public static SoundEvent CAVE;
    public static SoundEvent FARM;
    public static SoundEvent FRUIT;
    public static SoundEvent SEA;
    public static SoundEvent HANGGU;
    public static SoundEvent MINE;
    public static SoundEvent MUSEUM;
    public static SoundEvent TREES;
    public static SoundEvent OPENING;
    public static SoundEvent JUMP;
    public static SoundEvent APEMFFL;
    public static SoundEvent DJZNTMXLR;

    public static void registerSounds(IForgeRegistry<SoundEvent> e) {
        CALLING = registerSound("phone.calling",e);
        PHONE_CALL_CONNECTION = registerSound("phone.call_connection",e);
        HALDESU = registerSound("bgm.haldesu",e);
        PIOSA = registerSound("bgm.piosa",e);
        POPO = registerSound("bgm.popo",e);
        EQUELE = registerSound("bgm.equele",e);
        BERENTY = registerSound("bgm.berenty",e);
        MAIN = registerSound("bgm.main",e);
        CAVE = registerSound("bgm.cave",e);
        FARM = registerSound("bgm.farm",e);
        FRUIT = registerSound("bgm.fruit",e);
        SEA = registerSound("bgm.sea",e);
        HANGGU = registerSound("bgm.hanggu",e);
        MINE = registerSound("bgm.mine",e);
        MUSEUM = registerSound("bgm.museum",e);
        TREES = registerSound("bgm.beolmok",e);
        OPENING = registerSound("bgm.opening",e);
        JUMP = registerSound("bgm.jump",e);
        APEMFFL = registerSound("bgm.apemffl",e);
        DJZNTMXLR = registerSound("bgm.djzntmxlr",e);
    }

    private static SoundEvent registerSound(String soundName, IForgeRegistry e) {
        final ResourceLocation soundId = new ResourceLocation(Reference.MOD_ID,soundName);
        SoundEvent soundEvent = new SoundEvent(soundId).setRegistryName(soundName);
        e.register(soundEvent);
        return soundEvent;
    }
    public static SoundEvent getSound(String name) {
        switch (name) {
            case "할데스":
            case "haldesu":
            case "gkfeptm":
                return HALDESU;
            case "피오사":
            case "piosa":
            case "vldhtk":
                return PIOSA;
            case "포포":
            case "popo":
            case "vhvh":
                return POPO;
            case "에클레":
            case "equele":
            case "dpzmffp":
                return EQUELE;
            case "베렌티":
            case "berenty":
            case "qpfpsxl":
                return BERENTY;
            case "main":
            case "햇무리":
            case "gotanfl":
            case "haetmuri":
            case "apdls":
                return MAIN;
            case "동굴":
            case "cave":
            case "ehdrnf":
                return CAVE;
            case "농장":
            case "farm":
            case "shdwkd":
                return FARM;
            case "과수원":
            case "fruit":
            case "rhktndnjs":
                return FRUIT;
            case "항해":
            case "바다":
            case "sea":
            case "gkdgo":
                return SEA;
            case "항구":
            case "hanggu":
            case "gkdrn":
                return HANGGU;
            case "광산":
            case "rhkdtks":
            case "mine":
                return MINE;
            case "박물관":
            case "qkranfrhks":
            case "museum":
                return MUSEUM;
            case "벌목장":
            case "qjfahrwkd":
            case "beolmok":
                return TREES;
            case "오프닝":
            case "opening":
            case "dhvmsld":
                return OPENING;
            case "점프맵":
            case "jump":
            case "jumpmap":
            case "wjavmaoq":
            case "wjavm":
                return JUMP;
            case "apmffl":
            case "medley":
                return APEMFFL;
            case "djzntmxlr":
            case "어쿠스틱":
                    return DJZNTMXLR;

        }
        return SEA;
    }
}
