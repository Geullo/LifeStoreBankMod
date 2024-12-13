package com.geullo.bank;

import com.geullo.bank.Events.RightClickItem;
import com.geullo.bank.UI.*;
import com.geullo.bank.util.Sound;
import com.geullo.bank.util.SoundEffect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
	
	private static Message instance;
	private final Minecraft mc = Minecraft.getMinecraft();
	public Bank bank;
	private static CallingUI callUI;
	public static ISound nowCallSound = null;
	public static CallingUI getCallUI() {
		return callUI;
	}
	public static Message getInstance() {
		if(instance == null) {
			instance = new Message();
		}
		return instance;
	}
	
	private Message() {
	}
	/*
	* {Stat=name:싼손,point:2},{Stat=name:축지법,point:3},{Stat=name:밤의 등불,point:1},{Stat=name:추가 인벤,point:0}
	* {name:0,point:2},{name:1,point:3},{name:2,point:1},{name:3,point:0}
	* */
	public void handle(Packet message) {
		String code = message.data.substring(0,2);
		System.out.println("Geullo :: " + message.data);
		if (code.equals(PacketList.GET_MONEY.recogCode)){
			if (mc.currentScreen instanceof PhoneBankUI){
				PhoneBankUI ui = (PhoneBankUI) mc.currentScreen;
				ui.bank = new Bank(Integer.parseInt(message.data.substring(2).split(";")[0]),Integer.parseInt(message.data.split(";")[1]));
			}
			else if (mc.currentScreen instanceof ATMUI){
				ATMUI ui = (ATMUI) mc.currentScreen;
				ui.bank = new Bank(Integer.parseInt(message.data.substring(2).split(";")[0]),Integer.parseInt(message.data.split(";")[1]));
			}
		}
		else if (code.equals(PacketList.OPEN_BANK_UI.recogCode)){
			String[] split1 = message.data.split("/");
			if ("0".equals(split1[1])) {
				mc.displayGuiScreen(new ATMUI(true));
			}
			else if (split1[1].contains("1")) {
				String[] split2 = split1[1].split("=");
				if (mc.currentScreen instanceof Phone)
					mc.displayGuiScreen(new PhoneBankUI(split2[1],mc.currentScreen));
			}
		}
		else if (code.equals(PacketList.PHONE_BANK_OPEN.recogCode)){
			String[] split1 = message.data.split("/");
			Phone.bankOpened = "1".equals(split1[1]);
		}
		else if (code.equals(PacketList.OPEN_PHONE_BOOK.recogCode)){
			if (mc.currentScreen instanceof PhoneCellPhoneUI) {
				String[] split1 = message.data.split("/");
				String[] split2=split1[1].split("-");
				PhoneCellPhoneUI p = (PhoneCellPhoneUI) mc.currentScreen;

				p.clearPhoneNumbers();
				if (split2[0].equalsIgnoreCase("all")) {
					p.addPhoneNumber("양띵");
					p.addPhoneNumber("루태");
					p.addPhoneNumber("삼식");
					p.addPhoneNumber("후추");
					p.addPhoneNumber("콩콩");
					p.addPhoneNumber("눈꽃");
					p.addPhoneNumber("다주");
					p.addPhoneNumber("서넹");
				}
				else if (!split2[0].equalsIgnoreCase("null"))
					p.addPhoneNumbers(Arrays.asList(split2));
			}
		}
		else if (code.equals(PacketList.CALLING_PLAYER.recogCode)) {
			String[] split1 = message.data.substring(3).split("/");
			if (message.data.charAt(2) == '1') {
				if (nowCallSound!=null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}
//				nowCallSound = Sound.getSound(SoundEffect.PHONE_CALL_CONNECTION, SoundCategory.PLAYERS,0.45f,1f);
//				Minecraft.getMinecraft().getSoundHandler().playSound(nowCallSound);
				RightClickItem.soundsPlaying.get(SoundEffect.PHONE_CALL_CONNECTION).setPlaying(true);
				if (callUI!=null) MinecraftForge.EVENT_BUS.unregister(callUI);
				callUI = new CallingUI(split1[0], split1[1], CallingUI.CallState.ANSWER_PHONE);
				MinecraftForge.EVENT_BUS.register(callUI);
			}
			else if (message.data.charAt(2) == '0') {
				if (nowCallSound!=null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}
				if (callUI!=null) MinecraftForge.EVENT_BUS.unregister(callUI);
//				nowCallSound = Sound.getSound(SoundEffect.CALLING, SoundCategory.PLAYERS,0.45f,1f);
//				Minecraft.getMinecraft().getSoundHandler().playSound(nowCallSound);
				RightClickItem.soundsPlaying.get(SoundEffect.CALLING).setPlaying(true);
				callUI = new CallingUI(split1[0],split1[1], CallingUI.CallState.CALL_WAITING);
				MinecraftForge.EVENT_BUS.register(callUI);
			}
			else if (message.data.charAt(2) == '2') {
				if (nowCallSound!=null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}
				if (callUI!=null) MinecraftForge.EVENT_BUS.unregister(callUI);
				callUI = new CallingUI(split1[0],split1[1], CallingUI.CallState.PUBLIC_CALL);
				MinecraftForge.EVENT_BUS.register(callUI);
			}
			else if (message.data.charAt(2) == '3') {
				if (nowCallSound!=null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}
				if (callUI!=null&&(callUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE)||callUI.getState().equals(CallingUI.CallState.PUBLIC_CALL))) {
					MinecraftForge.EVENT_BUS.unregister(callUI);
					callUI = null;
				}

				if (callUI==null) {
					callUI = new CallingUI(split1[0], split1[1], CallingUI.CallState.PUBLIC_ANSWER_PHONE);
					MinecraftForge.EVENT_BUS.register(callUI);
				}
			}
			return;
		}
		else if (code.equals(PacketList.ACCEPT_CALLING.recogCode)) {
			String[] split1 = message.data.substring(3).split("/");
			if (message.data.charAt(2) == '2') {/*
				if (nowCallSound!=null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}*/
				RightClickItem.soundsPlaying.forEach((a,b) -> {
					b.setPlaying(false);
				});
				if (callUI!=null) MinecraftForge.EVENT_BUS.unregister(callUI);
				callUI = new CallingUI(split1[0],split1[1], CallingUI.CallState.PUBLIC_CALL);
				MinecraftForge.EVENT_BUS.register(callUI);
			}
			else {/*
				if (nowCallSound != null) {
					Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
					nowCallSound = null;
				}*/
				RightClickItem.soundsPlaying.forEach((a,b) -> {
					b.setPlaying(false);
				});
				if (callUI != null) MinecraftForge.EVENT_BUS.unregister(callUI);
				if (split1.length>=3) {
					String[] users = split1[2].split(",");
					callUI = new CallingUI(split1[0], split1[1], CallingUI.CallState.CALLING,Arrays.asList(users));
				}
				else {
					callUI = new CallingUI(split1[0], split1[1], CallingUI.CallState.CALLING);
				}
				MinecraftForge.EVENT_BUS.register(callUI);
			}
		}
		else if (code.equals(PacketList.DENY_CALLING.recogCode)) {
			/*if (nowCallSound!=null) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(nowCallSound);
				nowCallSound = null;
			}*/
			RightClickItem.soundsPlaying.forEach((a,b) -> {
				b.setPlaying(false);
			});
			if (callUI!=null) {
				MinecraftForge.EVENT_BUS.unregister(callUI);
				callUI = null;
			}
		}
		else if (code.equalsIgnoreCase(PacketList.MUSIC_PLAY.recogCode)) {
			String[] sp = message.data.substring(2).split("}}");
			for (int i = 0; i < RightClickItem.soundHashMap.size(); i++) {
				RightClickItem.SoundPlaying b = (RightClickItem.SoundPlaying) RightClickItem.soundHashMap.values().toArray()[i];
				if (sp[0].contains((String) RightClickItem.soundHashMap.keySet().toArray()[i])) {RightClickItem.setId(b.getId());return;}
			}
			/*ISound c = Sound.getSound(SoundEffect.getSound(sp[0]),Main.LIFESTORE_BACKGROUND_MUSIC, Float.parseFloat(sp[1]));
					System.out.println(c + " / " + sound + " / " + message.data);
					if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(c)) Minecraft.getMinecraft().getSoundHandler().stopSound(c);
					if (sound!=null&&sound.getSoundLocation().equals(c.getSoundLocation())) return;
					else if (sound!=null) {
						Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
					}
					if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(c)) {
						Minecraft.getMinecraft().getSoundHandler().stopSound(c);
					}
					RightClickItem.sounds = SoundEffect.getSound(sp[0]);
					RightClickItem.bgMusic = c;
					Minecraft.getMinecraft().getSoundHandler().playSound(c);*/
			/*if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(c)) Minecraft.getMinecraft().getSoundHandler().stopSound(c);
			if (sound!=null&&sound.getSoundLocation().equals(c.getSoundLocation())) return;
			else if (sound!=null) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
			}
			if (Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(c)) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(c);
				Minecraft.getMinecraft().getSoundHandler().stopSounds();
			}
			RightClickItem.sounds = SoundEffect.getSound(sp[0]);
			RightClickItem.bgMusic = c;
			Minecraft.getMinecraft().getSoundHandler().playSound(c);*/
		}
		else if (code.equalsIgnoreCase(PacketList.MUSIC_STOP.recogCode)) {
			RightClickItem.setId(-1);
			/*ISound sound = RightClickItem.bgMusic;
			if (sound!=null) {
				Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
				RightClickItem.bgMusic = null;
			}*/
		}
		else if (code.equalsIgnoreCase(PacketList.CHAT_CLEAR.recogCode)) {
			Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(false);
		}
		else if (code.equalsIgnoreCase(PacketList.BUSSTATION.recogCode)) {
			Minecraft.getMinecraft().displayGuiScreen(new BusStationUI());
		}
	}

}
