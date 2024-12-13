package com.geullo.bank.UI;

import com.geullo.bank.Bank;
import com.geullo.bank.Packet;
import com.geullo.bank.PacketList;
import com.geullo.bank.Render;
import com.geullo.bank.proxy.ClientProxy;
import com.geullo.bank.util.Sound;
import com.geullo.bank.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.geullo.bank.util.Utils.mouseBetweenIcon;
import static com.geullo.bank.util.Utils.shiftClicked;

public class ATMUI extends GuiScreen {
    private double[] bgPos = new double[2],bgSize = new double[2],playerSize=new double[2],playerPosX,playerPosY,
            selectPlayerSize = new double[2],selectPlayerPos = new double[2],
            inputBoxSize = new double[2],inputBoxPos =  new double[2],
            sendBtnSize = new double[2],sendBtnPos = new double[2],
            showCase = new double[4],
            scrollBarBgSize = new double[2],scrollBarBgPos = new double[2],
            scrollBarSize = new double[4],scrollBarPos = new double[4],
            cancelBtnPos = new double[2],cancelBtnSize = new double[2]
            ;
    public Bank bank = new Bank(0,0);
    public double gapX,gapY,scrollGapY,slotFirstSavePos,playersAllSize,slotFakeListPos;
    public int amt;
    public String targetPlayer = "",amtMoney = "";
    public List<String> players = new ArrayList<>();
    public List<BufferedImage> playersImage = new ArrayList<>();
    private boolean scrollClicked = false,mouseGrabbed = false;
    public void addPlayer(String player){
        try {
            players.add(player);
            playersImage.add(Render.getPlayerHead(player));
            playerPosX = new double[players.size() + 3];
            playerPosY = new double[players.size() + 3];
            initSettings();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public ATMUI(boolean sendPackets) {
        Packet.sendMessage(PacketList.GET_MONEY.recogCode);
        players.add("d7297");
        players.add("Daju_");
        players.add("samsik23");
        players.add("RuTaeY");
        players.add("Huchu95");
        players.add("KonG7");
        players.add("Seoneng");
        players.add("Noonkkob");
        playerPosX = new double[players.size()+3];
        playerPosY = new double[players.size()+3];
        if (sendPackets) Packet.sendMessage(PacketList.OPEN_BANK_UI.recogCode);
    }

    @Override
    public void initGui() {
        initBg();
        initSettings();
    }
    public void initSettings(){
        initPlayer(true);
        bgSize[0] = ((playerSize[0]*(players.size()/2))+(gapX*(players.size()/2)))+(gapX*16);
        bgSize[1] = ((playerSize[1]*(players.size()/2))+(gapY*(players.size()/2)));
        bgPos [0]  = (width/2) - (bgSize[0]/2);
        bgPos [1]  = (height/2) - (bgSize[1]/2);
        initSelect();
        showCase[0] = playerPosX[0]-0.001;
        showCase[1] = playerPosY[0]-0.001;
        showCase[2] = (playerSize[0]*(players.size()/2))+(gapX*(players.size()/2-1)) + 0.001;
        showCase[3] = (playerSize[1]*(players.size()/(players.size()/2)))+(gapY*(players.size()/(players.size()/2))-1) + 0.001;
        initScrollBar(true);
        initCancelBtn();
    }

    @Override
    public void updateScreen() {}

    private void initBg() {
        bgSize[0] = (width/2.66);
        bgSize[1] = (height/1.59);
        bgPos [0]  = (width/2d) - (bgSize[0]/2);
        bgPos [1]  = (height/2d) - (bgSize[1]/2);
    }

    private void initPlayer(boolean firstSet){
        if (firstSet) {
            playerSize[0] = bgSize[0] / 3.8 / 1.2;
            playerSize[1] = bgSize[1] / 3.48 / 1.2;
            gapX = (playerSize[0] / 4) / 2.3;
            gapY = (playerSize[1] / 3) / 3.3;
            playerPosX[0] = ((width/2)-(gapX/2))-(((playerSize[0]*(players.size()/2/2))+(gapX*1.35)));
            playerPosY[0] = bgPos[1]+(gapY*13);
        }
        initPlayers();
        slotFirstSavePos = playerPosY[0];
    }
    private void initPlayers(){
        for (int i=0;i<players.size();i++){
            if (i != 0){
                if (i%(players.size()/2)==0) {
                    playerPosX[i] = playerPosX[0];
                    playerPosY[i] = (playerPosY[i-1]+playerSize[1])+gapY;
                }else{
                    playerPosX[i] = (playerPosX[i - 1] + playerSize[0]) + gapX;
                    playerPosY[i] = playerPosY[i-1];
                }
            }
        }
    }
    private void initSelect(){
        selectPlayerSize[0] = playerSize[0];
        selectPlayerSize[1] = playerSize[1];
        inputBoxSize[0] = selectPlayerSize[0]*2.45;
        inputBoxSize[1] = playerSize[1]/2.15;
        sendBtnSize[0] = selectPlayerSize[0];
        sendBtnSize[1] = selectPlayerSize[1]/3.5;

        inputBoxPos[0] = bgPos[0] + ((bgSize[0]/2)-(inputBoxSize[0]/2));
        inputBoxPos[1] = bgPos[1] + ((bgSize[1]/2) - (inputBoxSize[1]/2));

        selectPlayerPos[0] = bgPos[0]+((bgSize[0]/2)-(selectPlayerSize[0]/2));
        selectPlayerPos[1] = (inputBoxPos[1]-gapY)-selectPlayerSize[1];

        sendBtnPos[0] = bgPos[0]+((bgSize[0]/2)-(sendBtnSize[0]/2));
        sendBtnPos[1] = (inputBoxPos[1]+inputBoxSize[1])+gapY;
    }

    private void initScrollBar(boolean first){
        scrollBarBgPos[0] = (playerPosX[(players.size()/2)-1] + playerSize[0]) + gapX;
        scrollBarBgPos[1] = playerPosY[0];
        scrollBarBgSize[0] = sendBtnSize[0]/4.5d;
        scrollBarBgSize[1] = (playerPosY[(players.size())-1]-playerPosY[0])+playerSize[1];
        scrollBarPos[0] = scrollBarBgPos[0]+gapX/2.6;
        scrollBarPos[1] = scrollBarBgPos[1]+gapY/2;
        scrollBarSize[0] = scrollBarBgSize[0]-gapX/1.3;
        scrollBarSize[1] = scrollBarBgSize[1]-gapY;
        slotFakeListPos = ((showCase[1]+showCase[3])-playersAllSize)-slotFirstSavePos;
        if (first) {
            scrollBarPos[2] = scrollBarPos[0];
            scrollBarPos[3] = scrollBarPos[1];
            scrollBarSize[2] = scrollBarSize[0];
            playersAllSize = (playerPosY[players.size()-1]+playerSize[1])-playerPosY[0];
            scrollBarSize[3] = Utils.percent(scrollBarSize[1],Utils.percentPartial(playersAllSize, playersAllSize-(playersAllSize-showCase[3])));
        }
    }

    private void initCancelBtn(){
        cancelBtnSize[0] = sendBtnSize[0]/3.8/1.25;
        cancelBtnSize[1] = sendBtnSize[1]/1.45;
        cancelBtnPos[0] = (bgPos[0]+bgSize[0])-cancelBtnSize[0]*3;
        cancelBtnPos[1] = bgPos[1]+cancelBtnSize[1]*2.55;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        drawAtm(mouseX,mouseY);
    }
    protected void drawAtm(int mouseX, int mouseY) {
        if (!mouseGrabbed) {
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            mouseGrabbed = true;
        }
        drawATMUI(mouseX,mouseY);
        drawSendMoney(mouseX,mouseY);
    }
    protected void drawATMUI(int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation("bank","atm/default_background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);
        drawSkin(mouseX,mouseY);
        scrollBarDraw(mouseX,mouseY);
    }
    protected void drawSendMoney(int mouseX, int mouseY) {
        if (!targetPlayer.equals("")) {
            drawSendMoneyb(mouseX,mouseY);
            drawSendMoneya(mouseX,mouseY);
        }
    }
    protected void drawSendMoneyb(int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation("bank", "atm/default_background.png"));
        Render.setColor(0xD9000000);
        Render.drawTexturedRect(bgPos[0], bgPos[1], bgSize[0], bgSize[1]);
    }
    protected void drawSendMoneya(int mouseX, int mouseY) {
        if (targetPlayer.equals("TOWN")) Render.bindTexture(new ResourceLocation("bank", "icon_town.png"));
        else Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("bank", "skins/" + targetPlayer + ".png"));
        Render.setColor(0xffffffff);
        if (targetPlayer.equals("TOWN")) Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1], selectPlayerSize[0], selectPlayerSize[1]);
        else {
            Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],selectPlayerSize[0],selectPlayerSize[1],0.125,0.125,0.25,0.25);
            Render.drawTexturedRect(selectPlayerPos[0], selectPlayerPos[1],selectPlayerSize[0],selectPlayerSize[1],0.625,0.125,0.75,0.25);
        }
        Render.bindTexture(new ResourceLocation("bank", "atm/input_box.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(inputBoxPos[0], inputBoxPos[1], inputBoxSize[0], inputBoxSize[1]);

        Render.bindTexture(new ResourceLocation("bank", "atm/send_btn.png"));
        if (Utils.mouseBetweenIcon(mouseX,mouseY,sendBtnPos[0],sendBtnPos[1],sendBtnSize[0],sendBtnSize[1])) Render.setColor(0xff555555);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(sendBtnPos[0], sendBtnPos[1], sendBtnSize[0], sendBtnSize[1]);
        drawMoney(amtMoney);
        Render.bindTexture(new ResourceLocation("bank","atm/cancel_btn.png"));
        if (Utils.mouseBetweenIcon(mouseX,mouseY,cancelBtnPos[0],cancelBtnPos[1],cancelBtnSize[0],cancelBtnSize[1])) Render.setColor(0xff555555);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(cancelBtnPos[0],cancelBtnPos[1],cancelBtnSize[0],cancelBtnSize[1]);
    }
    private void scrollBarDraw(int mouseX, int mouseY){
        Render.bindTexture(new ResourceLocation("bank","atm/default_scroll_background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(scrollBarBgPos[0],scrollBarBgPos[1],scrollBarBgSize[0],scrollBarBgSize[1]);
        if (scrollClicked){
            double ny = mouseY - scrollGapY;
            double a = scrollBarPos[1]+scrollBarSize[1];
            if (ny>scrollBarPos[1]&&ny+scrollBarSize[3]<a) scrollBarPos[3] = ny;
            else if (ny<=scrollBarPos[1]) scrollBarPos[3] = scrollBarPos[1];
            else if (ny+scrollBarSize[3]>=a) scrollBarPos[3] = a-scrollBarSize[3];
            double scrollCursor = scrollBarPos[3]-scrollBarPos[1];
            slotFakeListPos = ((showCase[1]+showCase[3])-playersAllSize)-slotFirstSavePos;
            playerPosY[0] = Utils.percent(slotFakeListPos,Utils.percentPartial(scrollBarSize[1]-scrollBarSize[3],scrollCursor))+slotFirstSavePos;
            initPlayers();
        }
        Render.bindTexture(new ResourceLocation("bank","atm/scroll_bar.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(scrollBarPos[2],scrollBarPos[3],scrollBarSize[2],scrollBarSize[3]);
        if ((scrollClicked||Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]))&&targetPlayer.equals(""))
        {
            Render.setColor(0x80000000);
            Render.drawTexturedRect(scrollBarPos[2], scrollBarPos[3], scrollBarSize[2], scrollBarSize[3]);
        }
    }

    private void drawMoney(String money) {
        String a;
        try {
            amt = Integer.parseInt(money);
            a = amtMoney.equals("") ? money : translate(amt);
        }catch (NumberFormatException e){
            amt = 0;
            a = translate(0);
        }
        Render.drawString((a) + "", (float) ((inputBoxPos[0] + inputBoxSize[0]) - gapX*4), (float) (inputBoxPos[1] + ((inputBoxSize[1] / 2) - (((int) (inputBoxSize[1] / 1.03) / 2.8)/1.85))), (int) (inputBoxSize[0] / 6), (int) (inputBoxSize[1] / 1.03), -1, 0xffffff);
    }

    private void drawSkin(int mouseX,int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int scale = computeGuiScale();
        GL11.glScissor((int) ((showCase[0]) * scale),
                (int) (Minecraft.getMinecraft().displayHeight - (showCase[1] + showCase[3]) * scale),
                (int) (showCase[2] * scale),
                (int) (showCase[3] * scale));
        for (int i=0;i<players.size();i++){
            String img;
            if (i<players.size()) {
                img = "skins/"+players.get(i) + ".png";
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("bank",img));
                Render.setColor(0xffffffff);
            }else {
                double percent = Utils.percentPartial(1000000,bank.avillMoney);
                img = percent<100?"icon_town.png":"icon_town.png";
                Render.bindTexture(new ResourceLocation("bank",img));
                Render.setColor(percent<100?0xffffffff:0xff7a7a7a);
            }
            if (players.size()>i) {
                Render.drawTexturedRect(playerPosX[i], playerPosY[i], playerSize[0], playerSize[1], 0.125, 0.125, 0.25, 0.25);
                Render.drawTexturedRect(playerPosX[i], playerPosY[i], playerSize[0], playerSize[1], 0.625, 0.125, 0.75, 0.25);
            }
            else Render.drawTexturedRect(playerPosX[i],playerPosY[i],playerSize[0],playerSize[1]);
            if (targetPlayer.equals("")&&mouseBetweenIcon(mouseX,mouseY,playerPosX[i],playerPosY[i],playerSize[0],playerSize[1])
                    &&mouseBetweenIcon(mouseX,mouseY,showCase[0], showCase[1],showCase[2],showCase[3])
            )
            {
                Render.setColor(0x80000000);
                if (players.size()>i) Render.drawTexturedRect(playerPosX[i], playerPosY[i], playerSize[0], playerSize[1], 0.125, 0.125, 0.25, 0.25);
                else Render.drawTexturedRect(playerPosX[i],playerPosY[i],playerSize[0],playerSize[1]);
            }
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
        for (int i=0;i<players.size();i++) {
            if (i>=players.size()&&mouseBetweenIcon(mouseX,mouseY,playerPosX[i],playerPosY[i],playerSize[0],playerSize[1])&&mouseBetweenIcon(mouseX,mouseY,showCase[0], showCase[1],showCase[2],showCase[3])) {
                List<String> a = new ArrayList<>();
                //TODO 모금 멘트 변경 상시 필요
                double percent = Utils.percentPartial(1000000,bank.avillMoney);
                if (percent<100) {
                    a.add("현재 박물관 오픈을 위한 모금 진행중!");
                    if (percent>=70) a.add("§f남은 기부 금액 : §6§l"+String.format("%.2f",100d-Utils.percentPartial(1000000,bank.avillMoney))+ "%");
                }
                else {
                    a.add("모금이 종료되었습니다.");
                }
                Render.drawTooltip(a,mouseX,mouseY);
            }
        }
    }
    public int computeGuiScale() {
        ScaledResolution sr = new ScaledResolution(mc);
        return sr.getScaleFactor();
    }
    @Override
    public void handleMouseInput() throws IOException {
        if ("".equals(targetPlayer)) {
            int amt = shiftClicked() ? 9 : 7;
            if (playerPosY[0] + checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel())) * amt <= (showCase[1] + showCase[3]) - playersAllSize) {
                playerPosY[0] = (showCase[1] + showCase[3]) - playersAllSize;
            } else if (playerPosY[0] + checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel())) * amt >= showCase[1])
                playerPosY[0] = showCase[1];
            else playerPosY[0] += checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel())) * amt;
            if (scrollBarPos[3] != 0) {
                slotFakeListPos = ((showCase[1] + showCase[3]) - playersAllSize) - slotFirstSavePos;
                double b = Utils.percent(scrollBarSize[1] - scrollBarSize[3], Utils.percentPartial(slotFakeListPos, playerPosY[0] - slotFirstSavePos));
                scrollBarPos[3] = scrollBarPos[1] + b;
            }
            initPlayers();
        }
        super.handleMouseInput();
    }

    public void closeSendMenu(){
        if (!targetPlayer.equals("")){
            targetPlayer = "";
            amtMoney = "";
        }
        else mc.displayGuiScreen(null);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode== Keyboard.KEY_ESCAPE) closeSendMenu();
        else {
            if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
                sendMoney();
                return;
            }
            if (Keyboard.isKeyDown(Keyboard.KEY_0) || Keyboard.isKeyDown(Keyboard.KEY_1) || Keyboard.isKeyDown(Keyboard.KEY_2) || Keyboard.isKeyDown(Keyboard.KEY_3) || Keyboard.isKeyDown(Keyboard.KEY_4) || Keyboard.isKeyDown(Keyboard.KEY_5) || Keyboard.isKeyDown(Keyboard.KEY_6) || Keyboard.isKeyDown(Keyboard.KEY_7) || Keyboard.isKeyDown(Keyboard.KEY_8) || Keyboard.isKeyDown(Keyboard.KEY_9) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD0) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD1) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD2) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD3) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD4) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD5) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD6) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD7) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD8) || Keyboard.isKeyDown(Keyboard.KEY_NUMPAD9) || Keyboard.isKeyDown(Keyboard.KEY_BACK)
            ) {
                if (keyCode==Keyboard.KEY_BACK||keyCode==Keyboard.KEY_DELETE) {
                    if (amtMoney.length()>=1) amtMoney = amtMoney.substring(0, amtMoney.length() - 1);
                    return;
                }
                if (amtMoney.length()+1<=9) amtMoney += typedChar;
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (!targetPlayer.equals("")){
            if (mouseBetweenIcon(mouseX,mouseY,sendBtnPos[0],sendBtnPos[1],sendBtnSize[0],sendBtnSize[1])){
                sendMoney();
                Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            }
            else if (mouseBetweenIcon(mouseX,mouseY,cancelBtnPos[0],cancelBtnPos[1],cancelBtnSize[0],cancelBtnSize[1])){
                closeSendMenu();
                Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            }
        }
        else {
            drawA(mouseX,mouseY,mouseButton);
        }
    }
    protected void drawA(int mouseX, int mouseY, int mouseButton) {
        if (Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[2], scrollBarPos[3], scrollBarSize[2], scrollBarSize[3])) {
            scrollGapY = mouseY - scrollBarPos[3];
            scrollClicked = true;
            return;
        }
        for (int i=0;i<players.size();i++){
            if (mouseBetweenIcon(mouseX,mouseY,playerPosX[i],playerPosY[i],playerSize[0],playerSize[1])
                    &&mouseBetweenIcon(mouseX,mouseY,showCase[0], showCase[1],showCase[2],showCase[3])){
                if (i<players.size()) {
                    targetPlayer = players.get(i);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                } else {
                    double percent = Utils.percentPartial(1000000,bank.avillMoney);
                    targetPlayer = percent<100?"TOWN":"";
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                }
            }
        }
    }
    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (scrollClicked) scrollClicked=false;
        super.mouseReleased(mouseX,mouseY,state);
    }

    public String translate(int money){
        NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.KOREA);
        return nf.format(money).replaceAll("￦","");
    }

    private int checkListFromEndToEnd(int n){
        switch (n){
            case -1:
                if ((playerPosY[players.size()]+playerSize[1])  <= showCase[1]+showCase[3]) return 0;
                break;
            case 1:
                if ((playerPosY[0]>=showCase[1])) return 0;
                break;
        }
        return n;
    }
    public void sendMoney(){
        if (amtMoney.equals("")) {
            ITextComponent iTextComponent = new TextComponentString("§4[ §c§l! §4]§f 1000원 보다 작은수를 송금할수 없습니다.");
            mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
            return;
        }
        if (amt<1000) {
            ITextComponent iTextComponent = new TextComponentString("§4[ §c§l! §4]§f 1000원 보다 작은수를 송금할수 없습니다.");
            mc.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
            return;
        }
        bank.sendMoney(targetPlayer,amt);
        targetPlayer = "";
        amtMoney = "" ;
        mc.displayGuiScreen(null);
    }
}
