package com.geullo.bank.Events;

import com.geullo.bank.Main;
import com.geullo.bank.UI.CallingUI;
import com.geullo.bank.UI.DonateUI;
import com.geullo.bank.UI.MusicOptionGui;
import com.geullo.bank.util.Sound;
import com.geullo.bank.util.SoundEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.*;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.HashMap;

public class RightClickItem {
    private CallingUI ui;
    private SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
    public static HashMap<String,SoundPlaying> soundHashMap = new HashMap<>();
    public static HashMap<SoundEvent,SoundPlaying> soundsPlaying = new HashMap<>();
    public static ISound bgMusic;
    public static ISound playingMusic;
    public static SoundEvent sounds;
    private static int timer = 1;
    protected static int id=-1;
    protected static int pastId = id;
    public static void setId(int id) {
        RightClickItem.pastId = RightClickItem.id;
        RightClickItem.id = id;
    }

    public static int getPastId() {
        return pastId;
    }

    public static int getId() {
        return id;
    }

    public RightClickItem(){
        int id=0;
        soundHashMap.put("벌목장",new SoundPlaying(id++,Sound.getSound(SoundEffect.TREES, Main.LIFESTORE_BACKGROUND_MUSIC,0.2f,1f)));
        soundHashMap.put("qjfahrwkd", soundHashMap.get("벌목장"));
        soundHashMap.put("beolmok", soundHashMap.get("벌목장"));

        soundHashMap.put("과수원",new SoundPlaying(id++,Sound.getSound(SoundEffect.FRUIT, Main.LIFESTORE_BACKGROUND_MUSIC,0.38f,1f)));
        soundHashMap.put("fruit",soundHashMap.get("과수원"));
        soundHashMap.put("rhktndnjs",soundHashMap.get("과수원"));

        soundHashMap.put("광산",new SoundPlaying(id++,Sound.getSound(SoundEffect.MINE, Main.LIFESTORE_BACKGROUND_MUSIC,0.17f,1f)) );
        soundHashMap.put("rhkdtks",soundHashMap.get("광산"));
        soundHashMap.put("mine", soundHashMap.get("광산"));

        soundHashMap.put("항구",new SoundPlaying(id++,Sound.getSound(SoundEffect.HANGGU, Main.LIFESTORE_BACKGROUND_MUSIC,0.1f,1f)));
        soundHashMap.put("hanggu", soundHashMap.get("항구"));
        soundHashMap.put("gkdrn", soundHashMap.get("항구"));

        soundHashMap.put("점프맵", new SoundPlaying(id++,Sound.getSound(SoundEffect.JUMP, Main.LIFESTORE_BACKGROUND_MUSIC,1f,1f)));
        soundHashMap.put("jump", soundHashMap.get("점프맵"));
        soundHashMap.put("jumpmap", soundHashMap.get("점프맵"));
        soundHashMap.put("wjavmaoq", soundHashMap.get("점프맵"));
        soundHashMap.put("wjavm", soundHashMap.get("점프맵"));

        soundHashMap.put("main",new SoundPlaying(id++,Sound.getSound(SoundEffect.MAIN, Main.LIFESTORE_BACKGROUND_MUSIC,0.24f,1f)));
        soundHashMap.put("햇무리", soundHashMap.get("main"));
        soundHashMap.put("gotanfl",soundHashMap.get("main"));
        soundHashMap.put("haetmuri", soundHashMap.get("main"));
        soundHashMap.put("apdls", soundHashMap.get("main"));

        soundHashMap.put("포포", new SoundPlaying(id++,Sound.getSound(SoundEffect.POPO, Main.LIFESTORE_BACKGROUND_MUSIC,0.28f,1f)));
        soundHashMap.put("popo", soundHashMap.get("포포"));
        soundHashMap.put("vhvh", soundHashMap.get("포포"));

        soundHashMap.put("할데스", new SoundPlaying(id++,Sound.getSound(SoundEffect.HALDESU, Main.LIFESTORE_BACKGROUND_MUSIC,0.24f,1f)));
        soundHashMap.put("haldesu", soundHashMap.get("할데스"));
        soundHashMap.put("gkfeptm", soundHashMap.get("할데스"));

        soundHashMap.put("에클레", new SoundPlaying(id++,Sound.getSound(SoundEffect.EQUELE, Main.LIFESTORE_BACKGROUND_MUSIC,0.3f,1f)));
        soundHashMap.put("equele", soundHashMap.get("에클레"));
        soundHashMap.put("dpzmffp", soundHashMap.get("에클레"));

        soundHashMap.put("피오사", new SoundPlaying(id++,Sound.getSound(SoundEffect.PIOSA, Main.LIFESTORE_BACKGROUND_MUSIC,0.43f,1f)));
        soundHashMap.put("piosa", soundHashMap.get("피오사"));
        soundHashMap.put("vldhtk", soundHashMap.get("피오사"));

        soundHashMap.put("베렌티", new SoundPlaying(id++,Sound.getSound(SoundEffect.BERENTY, Main.LIFESTORE_BACKGROUND_MUSIC,0.28f,1f)));
        soundHashMap.put("berenty", soundHashMap.get("베렌티"));
        soundHashMap.put("qpfpsxl", soundHashMap.get("베렌티"));

        soundHashMap.put("농장", new SoundPlaying(id++,Sound.getSound(SoundEffect.FARM, Main.LIFESTORE_BACKGROUND_MUSIC,0.17f,1f)));
        soundHashMap.put("farm", soundHashMap.get("농장"));
        soundHashMap.put("shdwkd", soundHashMap.get("농장"));

        soundHashMap.put("박물관", new SoundPlaying(id++,Sound.getSound(SoundEffect.MUSEUM, Main.LIFESTORE_BACKGROUND_MUSIC,0.4f,1f)));
        soundHashMap.put("qkranfrhks", soundHashMap.get("박물관"));
        soundHashMap.put("museum", soundHashMap.get("박물관"));

        soundHashMap.put("sea", new SoundPlaying(id++,Sound.getSound(SoundEffect.SEA, Main.LIFESTORE_BACKGROUND_MUSIC,0.45f,1f)));
        soundHashMap.put("gkdgo", soundHashMap.get("sea"));
        soundHashMap.put("항해", soundHashMap.get("sea"));

        soundHashMap.put("apmffl", new SoundPlaying(id++,Sound.getSound(SoundEffect.APEMFFL, Main.LIFESTORE_BACKGROUND_MUSIC,0.45f,1f)));
        soundHashMap.put("medley", soundHashMap.get("apmffl"));

        soundHashMap.put("djzntmxlr", new SoundPlaying(id++,Sound.getSound(SoundEffect.DJZNTMXLR, Main.LIFESTORE_BACKGROUND_MUSIC,0.45f,1f)));
        soundHashMap.put("어쿠스틱", soundHashMap.get("djzntmxlr"));

        soundsPlaying.put(SoundEffect.PHONE_CALL_CONNECTION,new SoundPlaying(0,Sound.getSound(SoundEffect.PHONE_CALL_CONNECTION, SoundCategory.PLAYERS,0.35f,1f)));
        soundsPlaying.put(SoundEffect.CALLING,new SoundPlaying(1,Sound.getSound(SoundEffect.CALLING, SoundCategory.PLAYERS,0.35f,1f)));
    }
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void c(GuiOpenEvent e) {
        if (e.getGui() instanceof GuiScreenOptionsSounds) {
            GuiScreenOptionsSounds a = (GuiScreenOptionsSounds) e.getGui();
            getParentScreen(a);
            e.setGui(new MusicOptionGui(getParentScreen(a),Minecraft.getMinecraft().gameSettings));
        }
    }
    public GuiScreen getParentScreen(GuiScreenOptionsSounds instance) {
        GuiScreen screen;
        screen = ObfuscationReflectionHelper.getPrivateValue(GuiScreenOptionsSounds.class,instance,instance.getClass().getDeclaredFields()[0].getName(),instance.getClass().getDeclaredFields()[0].getName());
        return screen;
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void a(TickEvent.ClientTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) return;
        if (Minecraft.getMinecraft().world == null) {
            soundsPlaying.forEach((a,b)-> {
                soundHandler.stopSound(b.getSound());
            });
        }
        if (Minecraft.getMinecraft().world!=null)
        {
            soundsPlaying.forEach((a,b)->{
                if (b.isPlaying()) {
                    if (!soundHandler.isSoundPlaying(b.getSound())) soundHandler.playSound(b.getSound());
                }
                else soundHandler.stopSound(b.getSound());
            });
        }
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void B(TickEvent.ClientTickEvent e) {
        if (!e.phase.equals(TickEvent.Phase.END)) return;
        if (timer%5!=0) {
            timer++;
            return;
        }
        else {
            timer = 1;
        }
        if (id == -1) {
            for (int i = 0; i <soundHashMap.size(); i++) {
                SoundPlaying b = (SoundPlaying) soundHashMap.values().toArray()[i];
                if (b.getId()==pastId) {
                    if (soundHandler.isSoundPlaying(b.getSound())) {
                        soundHandler.stopSounds();
                        return;
                    }
                }
            }
            return;
        }
        if (Minecraft.getMinecraft().world == null) {
            for (int i = 0; i <soundHashMap.size(); i++) {
                SoundPlaying b = (SoundPlaying) soundHashMap.values().toArray()[i];
                if (b.getId()==id) {
                    if (soundHandler.isSoundPlaying(b.getSound())) {
                        soundHandler.stopSounds();
                        return;
                    }
                }
            }
            return;
        }
        for (int i = 0; i <soundHashMap.size(); i++) {
            SoundPlaying b = (SoundPlaying) soundHashMap.values().toArray()[i];
            if (b.getId()==id) {
                if (!soundHandler.isSoundPlaying(b.getSound())) {
                    soundHandler.stopSounds();
                    soundHandler.playSound(b.getSound());
                    return;
                }
            }
            else soundHandler.stopSound(b.getSound());
        }
        /*if (Minecraft.getMinecraft().world ==null&&(bgMusic!=null)) {
            soundHandler.stopSound(bgMusic);
            playingMusic = null;
            bgMusic = null;
        }
        if (bgMusic!=null&&!soundHandler.isSoundPlaying(bgMusic)&&Minecraft.getMinecraft().world!=null) {
            soundHandler.stopSound(bgMusic);
            playingMusic = new PositionedSoundRecord(sounds, bgMusic.getCategory(), bgMusic.getVolume(), bgMusic.getPitch(), bgMusic.getXPosF(), bgMusic.getYPosF(), bgMusic.getZPosF());
            if (!soundHandler.isSoundPlaying(bgMusic)) soundHandler.playSound(bgMusic);
        }*/
    }
    public static class SoundPlaying {
        private boolean playing = false;
        private ISound sound;
        private int id;
        public SoundPlaying(int id,ISound sound) {
            this.id = id;
            this.sound = sound;
        }

        public SoundPlaying setPlaying(boolean playing) {
            this.playing = playing;
            return this;
        }

        public ISound getSound() {
            return sound;
        }

        public int getId() {
            return id;
        }

        public boolean isPlaying() {
            return playing;
        }
    }
}
