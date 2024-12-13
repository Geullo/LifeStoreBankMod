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
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.geullo.bank.util.Utils.mouseBetweenIcon;
import static com.geullo.bank.util.Utils.shiftClicked;

public class PhoneCellPhoneUI extends ATMUI {

    private final int size;
    public GuiScreen parentScreen;
    private List<String> phoneNumbers = new ArrayList<>();
    private boolean scrollClicked = false,mousegrabbed = false;
    private double slotAllSizeH,scrollGapY,scrollCursor,slotFakeListPos,slotFirstPos;
    private double[] showCase = new double[4],bgSize = new double[2],bgPos = new double[2],
            playerSlotX,playerSlotY,playerX,playerY, callPlayerBtnX, callPlayerBtnY,playerNameX,playerNameY,
            playerSlotSize = new double[2], playerSize = new double[2], callPlayerBtnSize = new double[2], playerNameSize = new double[2],
            scrollBarBgSize = new double[2], scrollBarBgPos = new double[2], scrollBarSize = new double[2], scrollBarPos = new double[3], scrollBarSaveData = new double[5]
            ;

    public void clearPhoneNumbers() {
        phoneNumbers.clear();
        addPhoneNumber("마을회관");
        initGui();
    }
    public void addPhoneNumbers(List<String> numbers) {
        phoneNumbers.addAll(numbers);
        initGui();
    }
    public void addPhoneNumber(String number) {
        phoneNumbers.add(number);
        initGui();
    }
    public PhoneCellPhoneUI(GuiScreen parentScreen) {
        super(false);
        Packet.sendMessage(PacketList.OPEN_PHONE_BOOK.recogCode);
        size = players.size()+2;
        playerSlotX = new double[size];
        playerSlotY = new double[size];
        playerX = new double[size];
        playerY = new double[size];
        callPlayerBtnX = new double[size];
        callPlayerBtnY = new double[size];
        playerNameX = new double[size];
        playerNameY = new double[size];
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        initBg();
        initSlot(true);
    }

    private void initBg() {
        bgSize[0] = (width/3.4);
        bgSize[1] = (height/1.12);
        bgPos[0] = (width/2) - (bgSize[0]/2);
        bgPos[1] = (height/2) - (bgSize[1]/2);
    }

    private void initShowCase() {
        showCase[0] = playerSlotX[0] - 0.001;
        showCase[1] = playerSlotY[0] - 0.001;
        showCase[2] = playerSlotSize[0] + 0.001;
        showCase[3] = (playerSlotY[6]-playerSlotY[0]-gapY)+0.001;
    }

    private void initSlot(boolean firstSet) {
        if (firstSet) {
            playerSlotSize[0] = bgSize[0]/1.25;
            playerSlotSize[1] = bgSize[1]/6.5/1.82;
            playerSize[0] = playerSlotSize[0]/5/1.55;
            playerSize[1] = playerSlotSize[1]/1.325;
            callPlayerBtnSize[0] = playerSize[0]*1.45;
            callPlayerBtnSize[1] = playerSize[1];
            playerNameSize[0] = playerSize[0]/1.25;
            playerNameSize[1] = playerSize[1]/1.05;
            playerSlotX[0] = (bgPos[0]+(playerSlotSize[0]/4/2.55));
            playerSlotY[0] = bgPos[1]+(playerSlotSize[1]*4.13);
            gapX = playerSize[0]/3/2;
            gapY = playerSize[1]/3/2;
        }
        initSlots();
        slotAllSizeH = (playerSlotY[phoneNumbers.size()==0?phoneNumbers.size():phoneNumbers.size()-1] + playerSlotSize[1]) - playerSlotY[0];
        slotFirstPos = playerSlotY[0];
        initShowCase();
        initScrollBar(firstSet);
    }

    private void initSlots() {
        for (int i=0;i<size;i++) {
            if (i!=0) {
                playerSlotX[i] = playerSlotX[0];
                playerSlotY[i] = (playerSlotY[i-1]+playerSlotSize[1])+gapY;
            }
            playerX[i] = playerSlotX[i] + gapX;
            playerY[i] = playerSlotY[i] + gapY;
            callPlayerBtnX[i] = ((playerSlotX[i] + playerSlotSize[0])-gapX)-callPlayerBtnSize[0];
            callPlayerBtnY[i] = playerY[i];
            playerNameX[i] = (playerSize[0]+playerX[i])+gapX;
            playerNameY[i] = playerY[i]+(playerSize[1]/2-gapY*1.02);
        }
    }

    public void initScrollBar(boolean first) {
        scrollBarBgSize[0] = playerSize[0]/3.65/1.25;
        scrollBarBgSize[1] = showCase[3];
        scrollBarBgPos[0] = (playerSlotX[0]+playerSlotSize[0])+(scrollBarBgSize[0]/2.25);
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
        scrollBarSaveData[3] = ((playerSlotSize[1]*phoneNumbers.size())+((gapY*1.35)*(phoneNumbers.size()-1)))-showCase[3];
        scrollBarSaveData[4] = (-(int) (scrollBarSaveData[2]));
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (!mousegrabbed){
            Minecraft.getMinecraft().mouseHelper.ungrabMouseCursor();
            mousegrabbed = true;
        }
        Render.bindTexture(new ResourceLocation("bank","phone/call/background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(bgPos[0],bgPos[1],bgSize[0],bgSize[1]);

        drawPlayerSlot(mouseX,mouseY);
        scrollBarDraw(mouseX,mouseY);
    }

    private void drawPlayerSlot(int mouseX, int mouseY) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        int scale = computeGuiScale();
        GL11.glScissor((int) ((showCase[0]) * scale), (int) (Minecraft.getMinecraft().displayHeight - (showCase[1] + showCase[3]) * scale), (int) (showCase[2] * scale), (int) (showCase[3] * scale));
        for (int i=0;i<phoneNumbers.size();i+=2) {
            a(i,mouseX,mouseY);
            if (i+1<phoneNumbers.size()) a(i+1,mouseX,mouseY);
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }
    private void a(int i,int mouseX, int mouseY) {
        Render.bindTexture(new ResourceLocation("bank","phone/call/slot_background.png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(playerSlotX[i],playerSlotY[i],playerSlotSize[0],playerSlotSize[1]);
        String a = transYD(phoneNumbers.get(i));
        Render.bindTexture(new ResourceLocation("bank", "phone/call/skin/phone_"+a.toLowerCase()+".png"));
        Render.setColor(0xffffffff);
        Render.drawTexturedRect(playerX[i],playerY[i],playerSize[0],playerSize[1]);
        Render.drawString("§l"+phoneNumbers.get(i),(float) playerNameX[i],(float) playerNameY[i], (int) playerNameSize[0],(int) playerNameSize[1],0,0x000000);
        Render.bindTexture(new ResourceLocation("bank","phone/call/call_btn.png"));
        if (mouseBetweenIcon(mouseX,mouseY,callPlayerBtnX[i],callPlayerBtnY[i],callPlayerBtnSize[0],callPlayerBtnSize[1])&& mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) Render.setColor(0xffbfbfbf);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(callPlayerBtnX[i], callPlayerBtnY[i], callPlayerBtnSize[0], callPlayerBtnSize[1]);
    }

    private void scrollBarDraw(int mouseX, int mouseY){
        if (phoneNumbers.size()<=6) return;
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
            initSlots();
        }
        Render.bindTexture(new ResourceLocation("bank","phone/call/scroll_bar.png"));
        if (scrollClicked||Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1])) Render.setColor(0xff5e5e5e);
        else Render.setColor(0xffffffff);
        Render.drawTexturedRect(scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1]);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_ESCAPE) mc.displayGuiScreen(parentScreen);
        super.keyTyped(typedChar,keyCode);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        if (scrollClicked) scrollClicked=false;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (Utils.mouseBetweenIcon(mouseX,mouseY,scrollBarPos[0], scrollBarPos[1], scrollBarSize[0], scrollBarSize[1])) {
            if (phoneNumbers.size()<=6) return;
            scrollGapY = mouseY - scrollBarPos[1];
            scrollClicked = true;
            Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
            return;
        }
        for (int i = 0; i < phoneNumbers.size(); i++) {
            if (mouseBetweenIcon(mouseX, mouseY, callPlayerBtnX[i], callPlayerBtnY[i], callPlayerBtnSize[0], callPlayerBtnSize[1])&&
                    mouseBetweenIcon(mouseX, mouseY, showCase[0], showCase[1], showCase[2], showCase[3])) {
                targetPlayer = transYDTV(phoneNumbers.get(i));
                Packet.sendMessage(PacketList.CALL_PLAYER.recogCode+"/"+targetPlayer);
                Minecraft.getMinecraft().getSoundHandler().playSound(Sound.getSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.PLAYERS,1f,1f));
                return;
            }
        }
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (!scrollClicked&&Integer.signum(Mouse.getEventDWheel())!=0) {
            if (phoneNumbers.size()<=6) return;
            int amt = shiftClicked() ? 9 : 7;
            if (checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()), amt) != 0) {
                playerSlotY[0] += checkListFromEndToEnd(Integer.signum(Mouse.getEventDWheel()), amt) * amt;
            } else if (Integer.signum(Mouse.getEventDWheel()) == -1) {
                playerSlotY[0] = (showCase[1] + showCase[3]) - slotAllSizeH;
            } else if (Integer.signum(Mouse.getEventDWheel()) == 1) {
                playerSlotY[0] = slotFirstPos;
            }
            slotFakeListPos = ((showCase[1] + showCase[3]) - slotAllSizeH) - showCase[1];
            scrollBarPos[1] = scrollBarPos[2] + (Utils.percent(scrollBarSaveData[2] - scrollBarSize[1], Utils.percentPartial(slotFakeListPos, playerSlotY[0] - slotFirstPos)));
            scrollCursor = scrollBarPos[1] - scrollBarPos[2];
        }
        initSlots();
    }

    private int checkListFromEndToEnd(int n,int a){
        switch (n){
            case -1:
                if ((playerSlotY[phoneNumbers.size()-1]+playerSlotSize[1])+(n*a)  <= showCase[1]+showCase[3]) return 0;
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
                "TOWN".equalsIgnoreCase(nick) ? "마을회관" : nick;
    }
    protected String transYD(String nick) {
        return "양띵".equalsIgnoreCase(nick) ? "d7297" :
                "삼식".equalsIgnoreCase(nick) ? "samsik23" :
                        "루태".equalsIgnoreCase(nick) ? "RuTaeY" :
                                "후추".equalsIgnoreCase(nick) ? "Huchu95" :
                                        "콩콩".equalsIgnoreCase(nick) ? "KonG7" :
                                                "눈꽃".equalsIgnoreCase(nick) ? "Noonkkob" :
                                                        "다주".equalsIgnoreCase(nick) ? "Daju_" :
                                                                "서넹".equalsIgnoreCase(nick) ? "Seoneng" :
                                                                        "마을회관".equalsIgnoreCase(nick) ? "TOWN" : nick;
    }
}
