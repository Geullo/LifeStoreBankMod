package com.geullo.bank.UI;

import com.geullo.bank.Packet;
import com.geullo.bank.PacketList;
import com.geullo.bank.Render;
import com.geullo.bank.util.Reference;
import com.geullo.bank.util.Utils;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;

public class BusStationUI extends GuiScreen {
    private double[] bg = new double[4],btn = new double[12], btnSz = new double[2];
    boolean once = false;
    @Override
    public void initGui() {
        bg[2] = (getWP(1250,true));
        bg[3] = (getWP(692,false));
        bg[0] = (width/2d)-(bg[2]/2);
        bg[1] = (height/2d)-(bg[3]/2);
        btnSz[0] = getWP(359,true);
        btnSz[1] = getWP(134,false);
        btn[0] = getWP(405,true);
        btn[1] = getWP(435,false);
        btn[2] = getWP(783,true);
        btn[3] = getWP(435,false);
        btn[4] = getWP(1157,true);
        btn[5] = getWP(435,false);
        btn[6] = getWP(405,true);
        btn[7] = getWP(608,false);
        btn[8] = getWP(783,true);
        btn[9] = getWP(608,false);
        btn[10] = getWP(1157,true);
        btn[11] = getWP(608,false);
    }

    private double getWP(double wp, boolean isWX) {
        return (wp/(isWX?1920d:1080d))*(isWX?width:height);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (mc!=null&&mc.mouseHelper!=null&&!once) {
            mc.mouseHelper.ungrabMouseCursor();
            once = true;
        }
        drawDefaultBackground();
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);

        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_1.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[0],btn[1],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[0],btn[1],btnSz[0],btnSz[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_2.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[2],btn[3],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[2],btn[3],btnSz[0],btnSz[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_3.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[4],btn[5],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[4],btn[5],btnSz[0],btnSz[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_4.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[6],btn[7],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[6],btn[7],btnSz[0],btnSz[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_5.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[8],btn[9],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[8],btn[9],btnSz[0],btnSz[1]);
        Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"bus/btn_6.png"));
        Render.setColor(Utils.mouseBetweenIcon(mouseX,mouseY,btn[10],btn[11],btnSz[0],btnSz[1])?0xff808080:0xffffffff);
        Render.drawTexturedRect(btn[10],btn[11],btnSz[0],btnSz[1]);

    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[0],btn[1],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"광산");
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[2],btn[3],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"농장");
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[4],btn[5],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"버스터미널");
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[6],btn[7],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"벌목장");
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[8],btn[9],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"상가거리");
        }
        else if (Utils.mouseBetweenIcon(mouseX,mouseY,btn[10],btn[11],btnSz[0],btnSz[1])) {
            Packet.sendMessage(PacketList.BUSSTATION.recogCode+"햇무리뒷산");
        }
    }
}
