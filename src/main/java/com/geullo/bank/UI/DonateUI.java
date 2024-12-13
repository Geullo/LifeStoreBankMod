package com.geullo.bank.UI;

import com.geullo.bank.Render;
import com.geullo.bank.util.Reference;
import com.geullo.bank.util.Utils;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class DonateUI extends ATMUI {
    private double[] bg = new double[4],btn = new double[4];
    public DonateUI(String name,boolean sendPackets) {
        super(sendPackets);
    }

    @Override
    public void initGui() {
        super.initGui();
        bg[0] = getWP(462,true);
        bg[1] = getWP(109,false);
        bg[2] = getWP(997,true);
        bg[3] = getWP(624,false);

        btn[0] = getWP(786,true);
        btn[1] = getWP(733,false);
        btn[2] = getWP(349,true);
        btn[3] = getWP(100,false);
    }
    private double getWP(double wp,boolean isWX) {
        return (wp/(isWX?1920d:1080d))*(isWX?width:height);
    }

    @Override
    protected void drawATMUI(int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"donation/town_bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"donation/donation.png"));
        if (getDonatePercent()<=0) Render.setColor(0xff7a7a7a);
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[0],btn[1],btn[2],btn[3])) Render.setColor(0xff7a7a7a);
        else if (!targetPlayer.equals("")) Render.setColor(0xff7a7a7a);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(btn[0],btn[1],btn[2],btn[3]);
        if (getDonatePercent()<=30&&Utils.mouseBetweenIcon(mouseX,mouseY,btn[0],btn[1],btn[2],btn[3])&&getDonatePercent()>0) {
            List<String> c = new ArrayList<>();
            c.add("§f현재 남은 금액 : §6§l"+String.format("%.2f %%",getDonatePercent()));
            Render.drawTooltip(c,mouseX,mouseY);
        }

    }
    private double getDonatePercent() {
        return 100-((bank.avillMoney/3000000d)*100);
    }

    @Override
    protected void drawSendMoneyb(int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation("bank", "donation/phone_bg.png"));
        Render.setColor(0xD9000000);
        Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);
    }

    @Override
    protected void drawA(int mouseX, int mouseY, int mouseButton) {
        if (getDonatePercent()>0&&Utils.mouseBetweenIcon(mouseX,mouseY,btn[0],btn[1],btn[2],btn[3])&&targetPlayer.equals("")) {
            targetPlayer = "TOWN";
        }
    }
}
