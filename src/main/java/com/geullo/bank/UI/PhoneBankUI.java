package com.geullo.bank.UI;

import com.geullo.bank.Render;
import com.geullo.bank.util.Sound;
import com.geullo.bank.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import static com.geullo.bank.util.Utils.mouseBetweenIcon;
import static com.geullo.bank.util.Utils.shiftClicked;

public class PhoneBankUI extends ATMUI{
    private final String sender;
    private final int size;
    private final GuiScreen parentScreen;
    private boolean scrollClicked = false,mousegrabbed = false;
    private double slotAllSizeH,scrollGapY,scrollCursor,slotFakeListPos,slotFirstPos;
    private double[] showCase = new double[4],bgSize = new double[2],bgPos = new double[2],
            playerMoneyBoxPos = new double[2],playerMoneyBoxSize = new double[2],
            playerSlotX,playerSlotY,playerX,playerY,sendPlayerBtnX,sendPlayerBtnY,playerNameX,playerNameY,
            playerSlotSize = new double[2], playerSize = new double[2], sendPlayerBtnSize = new double[2], playerNameSize = new double[2],
            scrollBarBgSize = new double[2], scrollBarBgPos = new double[2], scrollBarSize = new double[2], scrollBarPos = new double[3], scrollBarSaveData = new double[5],
            inputBoxPos = new double[2],inputBoxSize = new double[2],sendBtnPos = new double[2],sendBtnSize = new double[2],
            cancelBtnPos = new double[2],cancelBtnSize = new double[2]
    ;
    public PhoneBankUI(String sender, GuiScreen parentScreen){
        super(true);
        size = players.size()+2;
        playerSlotX = new double[size];
        playerSlotY = new double[size];
        playerX = new double[size];
        playerY = new double[size];
        sendPlayerBtnX = new double[size];
        sendPlayerBtnY = new double[size];
        playerNameX = new double[size];
        playerNameY = new double[size];
        this.parentScreen = parentScreen;
        this.sender = sender;
    }

    @Override
    public void initGui() {
        initBg();
        initSendWindow();
        initPlayerSlot(true);
    }
    private void initSendWindow(){
        inputBoxSize[0] = bgSize[0]/1.15;
        inputBoxSize[1] = bgSize[1]/4/2;
        sendBtnSize[0] = inputBoxSize[0]/2;
        sendBtnSize[1] = inputBoxSize[1]/2;

        sendBtnPos[0] = bgPos[0]+((bgSize[0]/2)-(sendBtnSize[0]/2));
        sendBtnPos[1] = (bgPos[1]+((bgSize[1]/2)-(sendBtnSize[1])));

        inputBoxPos[0] = bgPos[0]+((bgSize[0]/2)-(inputBoxSize[0]/2));
        inputBoxPos[1] = (sendBtnPos[1]-gapY)-inputBoxSize[1];
    }
    private void initShowCase(){
        showCase[0] = playerSlotX[0] - 0.001;
        showCase[1] = playerSlotY[0] - 0.001;
        showCase[2] = playerSlotSize[0] + 0.001;
        showCase[3] = (playerSlotY[4]-playerSlotY[0])-gapY/1.01;
    }
    private void initBg(){
        bgSize[0] = (width/3.4);
        bgSize[1] = (height/1.12);
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/2);
    }
    private void initPlayerSlots(){
        for (int i=0;i<players.size()+1;i++) {
            if (i != 0) {
                playerSlotX[i] = playerSlotX[0];
                playerSlotY[i] = (playerSlotY[i - 1] + playerSlotSize[1]) + (gapY*1.35);
                playerX[i] = playerX[0];
                playerY[i] = playerSlotY[i] + ((playerSlotSize[1] / 2) - (playerSize[1] / 2));
                sendPlayerBtnX[i] = sendPlayerBtnX[0];
                sendPlayerBtnY[i] = playerSlotY[i] + ((playerSlotSize[1] / 2) - (sendPlayerBtnSize[1] / 2));
                playerNameX[i] = playerNameX[0];
                playerNameY[i] = playerSlotY[i]+((playerSlotSize[1]/2)-(gapX/1.05));
            }
            else{
                playerX[i] = playerSlotX[0]+(gapX/1.45);
                playerY[i] = playerSlotY[0]+((playerSlotSize[1]/2) - (playerSize[1]/2));
                sendPlayerBtnX[i] = ((playerSlotX[0]+playerSlotSize[0])-sendPlayerBtnSize[0])-(gapX/3.22);
                sendPlayerBtnY[i] = playerSlotY[0]+((playerSlotSize[1]/2) - (sendPlayerBtnSize[1]/2));
                playerNameX[i] = (playerX[0]+playerSize[0])+(gapX);
                playerNameY[i] = playerSlotY[0]+((playerSlotSize[1]/2)-(gapX/1.05));
            }
        }
    }
    private void initPlayerSlot(boolean firstSet){
        if (firstSet) {
            playerSlotSize[0] = bgSize[0] / 1.235;
            playerSlotSize[1] = bgSize[1] / 2.63 / 2.86/1.15;
            playerSize[0] = playerSlotSize[0] / 4 / 1.25;
            playerSize[1] = playerSlotSize[1] / 1.12;
            sendPlayerBtnSize[0] = playerSlotSize[0] / 3.3 / 1.5;
            sendPlayerBtnSize[1] = playerSlotSize[1] / 1.09;
            playerNameSize[0] = playerSlotSize[0] / 2.3 / 2.38 / 1.26;
            playerNameSize[1] = playerSlotSize[0] / 4.5 / 1.545 / 1.01;

            gapX = playerSize[0] / 5 / 2;
            gapY = playerSize[1] / 3.4 / 4;

            playerSlotX[0] = (bgPos[0] + ((bgSize[0] / 2) - (playerSlotSize[0] / 2))) - (gapX * 1.55);
            playerSlotY[0] = (bgPos[1] + ((bgSize[1] / 2) - (playerSlotSize[1])));
            playerMoneyBoxSize[0] = bgSize[0]/1.05;
            playerMoneyBoxSize[1] = playerSlotSize[1]/1.05;
            playerMoneyBoxPos[0] = sendBtnPos[0];
            playerMoneyBoxPos[1] = sendBtnPos[1]-(sendBtnSize[1]/1.3);
        }
        initPlayerSlots();
        slotAllSizeH = (playerSlotY[players.size()]+playerSlotSize[1])-playerSlotY[0];
        slotFirstPos = playerSlotY[0];
        initShowCase();
        initScrollBar(firstSet);
    }
    public void initScrollBar(boolean first) {
        scrollBarBgSize[0] = (playerMoneyBoxSize[0] - playerSlotSize[0])/4.75/1.15;
        scrollBarBgSize[1] = showCase[3];
        scrollBarBgPos[0] = (playerSlotX[0]+playerSlotSize[0])+(scrollBarBgSize[0]/2);
        scrollBarBgPos[1] = playerSlotY[0];

        scrollBarPos[0] = scrollBarBgPos[0];
        scrollBarPos[1] = scrollBarBgPos[1];
        scrollBarPos[2] = scrollBarPos[1];
        scrollBarSize[0] = scrollBarBgSize[0];
        initScrollData();
        if (first) scrollBarSize[1] = Utils.percent(scrollBarSaveData[2],Utils.percentPartial(slotAllSizeH,slotAllSizeH-(slotAllSizeH-showCase[3])));
    }
    public void initScrollData(){
        scrollBarSaveData[0] = (scrollBarPos[1]+scrollBarBgSize[1])/*-(gapY*1.23)*/;
        scrollBarSaveData[1] = scrollBarPos[1];
        scrollBarSaveData[2] = scrollBarBgSize[1]/*-(gapY*1.23)*/;
        scrollBarSaveData[3] = ((playerSlotSize[1]*players.size())+((gapY*1.35)*(players.size()-1)))-showCase[3];
        scrollBarSaveData[4] = (-(int) (scrollBarSaveData[2]));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!mousegrabbed){
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            mousegrabbed = true;
        }
        Render.bindTexture(new ResourceLocation("bank","phone/bank/default_background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);


        Render.drawString(transYDTV(sender),(float) (bgPos[0]+(gapX*6.65)),(float) ((bgPos[1]+(gapY*15.5))), (int) (bgSize[0]/4/1.35), (int) (bgSize[1]/5/1.75),0,0xffffff);
        drawPlayerSlot(mouseX,mouseY);
        drawMoney(String.valueOf(bank.money),"playerMoney");
        scrollBarDraw(mouseX,mouseY);
        if (!"".equals(targetPlayer)) {
            Render.bindTexture(new ResourceLocation("bank","phone/bank/default_background.png"));
            Render.setColor(0xBF000000);
            Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);

            Render.bindTexture(new ResourceLocation("bank","phone/bank/input_box.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(inputBoxPos[0],inputBoxPos[1],inputBoxSize[0],inputBoxSize[1]);

            Render.bindTexture(new ResourceLocation("bank","phone/bank/send_btn_2.png"));
            if (mouseBetweenIcon(mouseX,mouseY,sendBtnPos[0],sendBtnPos[1],sendBtnSize[0],sendBtnSize[1])&&
                    mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) Render.setColor(0xff555555);
            else Render.setColor(0xffffffff);
            Render.drawTexturedRect(sendBtnPos[0],sendBtnPos[1],sendBtnSize[0],sendBtnSize[1]);

            drawMoney(amtMoney,"input");

            Render.drawString(transYDTV(targetPlayer) + "§f에게 송금 하기", (float) ( inputBoxPos[0]+(inputBoxSize[0]/2)), (float) (inputBoxPos[1]-(inputBoxSize[1]/1.63)), (int) (inputBoxSize[0]/5/1.2), (int) (inputBoxSize[1]/1.3),1,0xffffff);
        }
    }

    private void scrollBarDraw(int mouseX, int mouseY){
        Render.bindTexture(new ResourceLocation("bank","phone/scroll_bar_bg.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(scrollBarBgPos[0], scrollBarBgPos[1], scrollBarBgSize[0], scrollBarBgSize[1]);
        if (scrollClicked){
            double ny = mouseY - scrollGapY;
            if (ny>=scrollBarSaveData[1]&&ny+scrollBarSize[1]<scrollBarSaveData[0]){
                scrollBarPos[1] = ny;
            }else if (ny<=scrollBarSaveData[1]){
                scrollBarPos[1] = scrollBarSaveData[1];
            }else if (ny+scrollBarSize[1]>=scrollBarSaveData[0]){
                scrollBarPos[1] = scrollBarSaveData[0]-scrollBarSize[1];
            }
            scrollCursor = scrollBarPos[1]-scrollBarPos[2];
            slotFakeListPos = ((showCase[1]+showCase[3])-slotAllSizeH)- slotFirstPos;

            playerSlotY[0] = (Utils.percent(slotFakeListPos, Utils.percentPartial(scrollBarSaveData[2] - scrollBarSize[1], scrollCursor)))+ slotFirstPos;
            initPlayerSlots();
        }
        Render.bindTexture(new ResourceLocation("bank","phone/bank/scroll_bar.png"));
        Render.setColor(0xff5e5e5e);
        Render.drawTexturedRect(scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]);
        if (scrollClicked||Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1])) {
            Render.setColor(0x80000000);
            Render.drawTexturedRect(scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]);
        }
    }
    private void drawMoney(String money,String type){
        String a;
        try
        {
            amt = Integer.parseInt(money);
            a = money.equals("") ? money : translate(amt);
        }
        catch (NumberFormatException e)
        {
            amt = 0;
            a = translate(0);
        }
        if (type.equals("input"))
        {
            Render.drawString(a, (float) ((inputBoxPos[0] + inputBoxSize[0]) - (gapX*9)), (float) (inputBoxPos[1] + ((inputBoxSize[1] / 2) - (((int) (inputBoxSize[1] / 1.03) / 2.8)/1.5))), (int) (inputBoxSize[0] / 4.5), (int) (inputBoxSize[1] / 1.03), -1, 0xffffff);
        }
        else if (type.equals("playerMoney"))
        {
            Render.drawString(a, (float) (sendBtnPos[0]+(sendBtnSize[0]*1.12)), (float) (sendBtnPos[1]-(sendBtnSize[1]*2.85)), (int) (playerMoneyBoxSize[0] / 4.9), (int) (playerMoneyBoxSize[1] / 1.09), -1, 0xffffff);
        }
    }
    private void drawPlayerSlot(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int scale = computeGuiScale();
        GL11.glScissor((int) ((showCase[0]) * scale), (int) (Minecraft.getMinecraft().displayHeight - (showCase[1] + showCase[3]) * scale), (int) (showCase[2] * scale), (int) (showCase[3] * scale));
        for (int i=0;i<players.size()+1;i++) {
            Render.bindTexture(new ResourceLocation("bank","phone/bank/slot.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(playerSlotX[i],playerSlotY[i],playerSlotSize[0],playerSlotSize[1]);
            if (i==players.size()){
                Render.bindTexture(new ResourceLocation("bank", "phone/bank/skin/phone_town.png"));
            }else {
                String a = players.get(i);
                Render.bindTexture(new ResourceLocation("bank", "phone/bank/skin/phone_" + a.toLowerCase() + ".png"));
            }
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(playerX[i],playerY[i],playerSize[0],playerSize[1]);
            Render.drawString("§l"+(i==players.size()?"마을회관":transYDTV(players.get(i))),(float) playerNameX[i],(float) playerNameY[i], (int) playerNameSize[0],(int) playerNameSize[1],0,0xffffff);
            Render.bindTexture(new ResourceLocation("bank","phone/bank/send_btn_1.png"));
            Render.setColor(0xffffffff);
            Render.drawTexturedRect(sendPlayerBtnX[i], sendPlayerBtnY[i], sendPlayerBtnSize[0], sendPlayerBtnSize[1]);
            if (mouseBetweenIcon(mouseX,mouseY,sendPlayerBtnX[i],sendPlayerBtnY[i],sendPlayerBtnSize[0],sendPlayerBtnSize[1])&&"".equals(targetPlayer)) {
                Render.setColor(0x80000000);
                Render.drawTexturedRect(sendPlayerBtnX[i], sendPlayerBtnY[i], sendPlayerBtnSize[0], sendPlayerBtnSize[1]);
            }
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) {
            mc.displayGuiScreen(parentScreen);
            return;
        }
        super.keyTyped(typedChar,keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX,mouseY,mouseButton);

        if (!"".equals(targetPlayer)){
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
            if (Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1])) {
                scrollGapY = mouseY - scrollBarPos[1];
                scrollClicked = true;
                Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                return;
            }
            for (int i = 0; i < players.size() + 1; i++) {
                if (mouseBetweenIcon(mouseX, mouseY, sendPlayerBtnX[i], sendPlayerBtnY[i], sendPlayerBtnSize[0], sendPlayerBtnSize[1])&&
                        mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) {
                    targetPlayer = players.size() == i ? "TOWN" : players.get(i);
                    Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                }
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (!scrollClicked&&Integer.signum(Mouse.getEventDWheel())!=0) {
            if ("".equals(targetPlayer)) {
                int amt = shiftClicked()?9:7;
                if (checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()), amt)!=0) {
                    playerSlotY[0] += checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()),amt) * amt;
                }
                else if (Integer.signum(Mouse.getEventDWheel()) == -1) {
                    playerSlotY[0] = (showCase[1]+showCase[3]) - slotAllSizeH;
                }
                else if (Integer.signum(Mouse.getEventDWheel()) == 1) {
                    playerSlotY[0] = slotFirstPos;
                }
                slotFakeListPos = ((showCase[1]+showCase[3])-slotAllSizeH) - showCase[1];
                scrollBarPos[1] = scrollBarPos[2]+(Utils.percent(scrollBarSaveData[2]-scrollBarSize[1],Utils.percentPartial(slotFakeListPos,playerSlotY[0]- slotFirstPos)));
                scrollCursor = scrollBarPos[1]-scrollBarPos[2];
            }
        }
        initPlayerSlots();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (scrollClicked) scrollClicked=false;
    }

    private int checkListFromEndToEnd(int n,int a){
        switch (n){
            case -1:
                if ((playerSlotY[players.size()]+playerSlotSize[1])+(n*a)  <= showCase[1]+showCase[3]) return 0;
                break;
            case 1:
                if ((playerSlotY[0]+(n*a)>=slotFirstPos)) return 0;
                break;
        }
        return n;
    }
    protected String transYDTV(String nick) {
        return "d7297".equalsIgnoreCase(nick) ? "양띵" :
                "samsik23".equalsIgnoreCase(nick) ? "삼식" :
                "RuTaeY".equalsIgnoreCase(nick) ? "루태" :
                "Huchu95".equalsIgnoreCase(nick) ? "후추" :
                "KonG7".equalsIgnoreCase(nick) ? "콩콩" :
                "Noonkkob".equalsIgnoreCase(nick) ? "눈꽃" :
                "Daju_".equalsIgnoreCase(nick) ? "다주" :
                "Seoneng".equalsIgnoreCase(nick) ? "서넹" :
                "TOWN".equalsIgnoreCase(nick) ? "마을회관" :
                        nick;
    }

}
