package com.geullo.bank.Keybind;


import com.geullo.bank.Message;
import com.geullo.bank.Packet;
import com.geullo.bank.PacketList;
import com.geullo.bank.UI.CallingUI;
import com.geullo.bank.UI.DonateUI;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class CallingKey {
    public boolean KEY_PRESSED = false,KEY_PRESSED2 = false;
    private CallingKey instance;
    public int pkLen = 0;

    private Minecraft mc = Minecraft.getMinecraft();
    public CallingKey(){
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void c(PlayerInteractEvent.RightClickBlock e) {
        if (e.getWorld().getBlockState(e.getPos()).getBlock().getRegistryName().toString().equals("cocricotmod:postbox_yellow"))
            Minecraft.getMinecraft().displayGuiScreen(new DonateUI("",true));
    }
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void b(InputEvent.KeyInputEvent e) {
        if (Message.getCallUI()!=null) {
            if (RegisterKeybind.callingAccept.isKeyDown()) {
                if (!KEY_PRESSED) {
                    KEY_PRESSED = true;
                    CallingUI callingUI = Message.getCallUI();
                    if (!(callingUI.getState().equals(CallingUI.CallState.ANSWER_PHONE)||callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE))) return;
                    if (pkLen>=4) return;
                    Packet.sendMessage(PacketList.ACCEPT_CALLING.recogCode+"1"+(!callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE)?callingUI.sender:callingUI.target));
                    pkLen++;
                    MinecraftForge.EVENT_BUS.register(new A(this));
                }
            } else if (!RegisterKeybind.callingAccept.isKeyDown()) {
                if (KEY_PRESSED) KEY_PRESSED = false;
            }
            if (RegisterKeybind.callingDeny.isKeyDown()) {
                if (!KEY_PRESSED2) {
                    KEY_PRESSED2 = true;
                    CallingUI callingUI = Message.getCallUI();
                    if ((callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE))) return;
                    if (pkLen>=4) return;
                    Packet.sendMessage(PacketList.DENY_CALLING.recogCode+"1"+callingUI.sender);
                    pkLen++;
                    MinecraftForge.EVENT_BUS.register(new A(this));
                }
            } else if (!RegisterKeybind.callingDeny.isKeyDown()) {
                if (KEY_PRESSED2) KEY_PRESSED2 = false;
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void b(GuiScreenEvent.KeyboardInputEvent e) {
        if (Message.getCallUI()!=null) {
            if (RegisterKeybind.callingAccept.isKeyDown()) {
                if (!KEY_PRESSED) {
                    KEY_PRESSED = true;
                    CallingUI callingUI = Message.getCallUI();
                    if (!(callingUI.getState().equals(CallingUI.CallState.ANSWER_PHONE)||callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE))) return;
                    if (pkLen>=4) return;
                    Packet.sendMessage(PacketList.ACCEPT_CALLING.recogCode+"1"+(!callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE)?callingUI.sender:callingUI.target));
                    pkLen++;
                    MinecraftForge.EVENT_BUS.register(new A(this));
                }
            } else if (!RegisterKeybind.callingAccept.isKeyDown()) {
                if (KEY_PRESSED) KEY_PRESSED = false;
            }
            if (RegisterKeybind.callingDeny.isKeyDown()) {
                if (!KEY_PRESSED2) {
                    KEY_PRESSED2 = true;
                    CallingUI callingUI = Message.getCallUI();
                    if (pkLen>=4) return;
                    if (callingUI.getState().equals(CallingUI.CallState.PUBLIC_ANSWER_PHONE)) return;
                    Packet.sendMessage(PacketList.DENY_CALLING.recogCode+"1"+callingUI.sender);
                    pkLen++;
                    MinecraftForge.EVENT_BUS.register(new A(this));
                }
            } else if (!RegisterKeybind.callingDeny.isKeyDown()) {
                if (KEY_PRESSED2) KEY_PRESSED2 = false;
            }
        }
    }

}
