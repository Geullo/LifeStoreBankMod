package com.geullo.bank.UI;

import com.geullo.bank.Packet;
import com.geullo.bank.PacketList;
import com.geullo.bank.Render;
import com.geullo.bank.util.Sound;
import com.geullo.bank.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class Phone extends GuiScreen {
    private double[] bgSize = new double[2],bgPos = new double[2],iconSize = new double[2],iconPos = new double[4];
    private int mouseOverColor = 0xffbfbfbf;

    public static boolean bankOpened = false;

    public Phone() {
    }

    @Override
    public void initGui() {
        initBg();
        initIcon();
    }

    private void initBg() {
        bgSize[0] = (width/3.4);
        bgSize[1] = (height/1.12);
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/2);
    }

    private void initIcon() {
        iconSize[0] = bgSize[0]/ 3/2.15;
        iconSize[1] = bgSize[1]/ 7.2;
        iconPos[0] = bgPos[0] + (iconSize[0]/1.26);
        iconPos[1] = bgPos[1] + (iconSize[1]);
        iconPos[2] = (iconPos[0] + iconSize[0]) + iconSize[0]/3;
        iconPos[3] = iconPos[1];
    }

    @Override
    public void updateScreen() {}

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Render.bindTexture(new ResourceLocation("bank","phone/phone_background_2.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);
        Render.bindTexture(new ResourceLocation("bank","phone/icons/call.png"));
        if (Utils.mouseBetweenIcon(mouseX,mouseY,iconPos[0],iconPos[1],iconSize[0],iconSize[1])) Render.setColor(mouseOverColor);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(iconPos[0],iconPos[1],iconSize[0],iconSize[1]);
        if (bankOpened) {
            Render.bindTexture(new ResourceLocation("bank", "phone/icons/bank.png"));
            if (Utils.mouseBetweenIcon(mouseX, mouseY, iconPos[2], iconPos[3], iconSize[0], iconSize[1])) Render.setColor(mouseOverColor);
            else Render.setColor(0xffffffff);
            Render.drawTexturedRect(iconPos[2], iconPos[3], iconSize[0], iconSize[1]);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (Utils.mouseBetweenIcon(mouseX,mouseY,iconPos[0],iconPos[1],iconSize[0],iconSize[1])) {
            Minecraft.getMinecraft().displayGuiScreen(new PhoneCellPhoneUI(this));
            Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            return;
        }
        else if (bankOpened&&Utils.mouseBetweenIcon(mouseX,mouseY,iconPos[2],iconPos[3],iconSize[0],iconSize[1])) {
            Packet.sendMessage(PacketList.OPEN_BANK_UI.recogCode+"/1");
            Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            return;
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(null);
        }
        super.keyTyped(typedChar,keyCode);
    }
}
