package com.geullo.bank.UI;

import com.geullo.bank.Keybind.RegisterKeybind;
import com.geullo.bank.Render;
import com.geullo.bank.util.Reference;
import com.ibm.icu.impl.BOCU;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CallingUI extends GuiScreen {
    public String target,sender;
    private int displayTime = 0,displayTick=0;
    private HashMap<String,String> icons = new HashMap();
    private double[] bg = new double[4],skin = new double[4],accept = new double[4],deny = new double[4],str = new double[4],userSize = new double[2],userX,userY,crownY = new double[2];
    private double width, height ;
    private CallState state;
    private List<String> callMember = new ArrayList<>();
    public CallingUI(String sender,String target,CallState state) {
        this.sender = sender;
        this.target = target;
        this.state = state;
        icons.put("none","call/none.png");
        icons.put("마을회관","icon_town.png");
        icons.put("카페","icon_cafe.png");
        icons.put("우체국","icon_post_office.png");
        icons.put("부동산","icon_real_estate.png");
        icons.put("버스킹","icon_busking.png");
        icons.put("운영자의집","icon_manager_house.png");
    }
    public CallingUI(String sender,String target,CallState state,List<String> callMember) {
        this(sender,target,state);
        this.callMember = callMember;
        userX = new double[callMember.size()+1];
        userY = new double[callMember.size()+1];
    }
    public void setState(CallState state) {
        this.state = state;
    }
    public CallState getState() {
        return state;
    }

    public void init(int displayTick,int displayTime) {
        Minecraft mc = Minecraft.getMinecraft();
        ScaledResolution sc = new ScaledResolution(mc);
        double width = sc.getScaledWidth_double(), height = sc.getScaledHeight_double();
        if (this.width!=width || this.height!=height) {
            bg[2] = width / 4.24 / 1.35;
            bg[3] = height / 4.27 / 1.5;
            bg[0] = width - bg[2]-(bg[2]/10/3);
            bg[1] = height - bg[3]-(bg[3]/4/1.22);
            skin[2] = bg[2]/3.32/1.12;
            skin[3] = bg[3]/1.57/1.12;
            skin[0] = bg[0]+(skin[2]/5);
            skin[1] = bg[1]+(skin[3]/1.93);
            accept[2] = skin[2]*1.65;
            accept[3] = (bg[2]/3.45/2.55);
            accept[0] = skin[0]+skin[2]+(skin[2]/3.5);
            accept[1] = skin[1]+accept[3]/12.5;
            if (state.equals(CallState.PUBLIC_ANSWER_PHONE)) accept[1]= accept[1]+(accept[3]/2)+(skin[2]/6.5/2);
            deny[2] = accept[2];
            deny[3] = accept[3];
            deny[0] = accept[0];
            if (state.equals(CallState.ANSWER_PHONE)) deny[1] = accept[1]+accept[3]+(skin[2]/6.5/2);
            else deny[1] = skin[1]+((skin[3]/2)-(deny[3]/2));
            str[2] = skin[2]/1.82;
            str[3] = skin[3]/1.84;
            if (state.equals(CallState.CALL_WAITING)) str[0] = skin[0] + skin[2] + (skin[2]/8)/2;
            else str[0] = skin[0] + skin[2] - skin[2]/7.55;
            str[1] = bg[1] + (skin[3] / 4.85/2.1);
            for (int i = 0; i < callMember.size(); i++) {
                if (i==0) {
                    userX[i] = bg[0]+getPercent(30,1920d,width);
                    userY[i] = bg[1] - getPercent(28,1080d,height);
                    crownY[i] = userY[i]- getPercent(28,1080d,height);
                    continue;
                }
                userX[i] = userX[i-1] + getPercent(28,1920d,width);
                userY[i] = userY[i-1];
            }
            userSize[0] = (26/1920d)*width;
            userSize[1] = (26/1080d)*height;
        }

    }
    private double getPercent(double x, double y, double z) {
        return ((x/y)*z);
    }

    @SubscribeEvent
    public void renderCallingUI(RenderGameOverlayEvent e) {
        if (e.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
            int frame = Minecraft.getMinecraft().gameSettings.limitFramerate;
            displayTick++;
            if (frame%displayTick==0) {displayTime++;displayTick=0;}
            init(displayTick,displayTime);
            GlStateManager.pushMatrix();
            if (state.equals(CallState.ANSWER_PHONE)) Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"call/background.png"));
            else if (state.equals(CallState.CALL_WAITING)) Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"call/waiting.png"));
            else if (state.equals(CallState.CALLING)) Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"call/calling_background.png"));
            else if (state.equals(CallState.PUBLIC_CALL)) Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"call/public_background.png"));
            else if (state.equals(CallState.PUBLIC_ANSWER_PHONE)) Render.bindTexture(new ResourceLocation(Reference.MOD_ID,"call/public_answer_background.png"));

            Render.setColor(0xffffffff);
            Render.drawTexturedRect(bg[0],bg[1],bg[2],bg[3]);
            if (state.equals(CallState.CALLING)&&!callMember.isEmpty()) {
                Render.bindTexture(new ResourceLocation("bank", "call/user.png"));
                for (int i = 0; i < callMember.size(); i++) {
                    Render.setColor(getColorYD(callMember.get(i)));
                    Render.drawTexturedRect(userX[i],userY[i],userSize[0],userSize[1]);
                }
                Render.bindTexture(new ResourceLocation("bank", "call/crown.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(userX[0],crownY[0],userSize[0],userSize[1]);
            }
            String name = state.equals(CallState.CALL_WAITING)||state.equals(CallState.PUBLIC_ANSWER_PHONE)||(state.equals(CallState.PUBLIC_CALL))?target:sender;
            String[] c = {""};
            if (icons.keySet().stream().anyMatch(a->{
                if (name.contains(a)) {
                    c[0] = a;
                    return true;
                }
                return false;
            })) {
                Render.bindTexture(new ResourceLocation("bank", icons.get(c[0])));
            }
            else {
                Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("bank", "skins/" + transYDTV(name) + ".png"));
            }
            Render.setColor(0xffffffff);
            if (name.equals("none")||isSpecial(name)) Render.drawTexturedRect(skin[0],skin[1],skin[2],skin[3]);
            else {
                Render.drawTexturedRect(skin[0],skin[1],skin[2],skin[3],0.125,0.125,0.25,0.25);
                Render.drawTexturedRect(skin[0],skin[1],skin[2],skin[3],0.625,0.125,0.75,0.25);
            }
            if (state.equals(CallState.ANSWER_PHONE)||state.equals(CallState.CALL_WAITING)||state.equals(CallState.PUBLIC_ANSWER_PHONE)) {
                if (state.equals(CallState.ANSWER_PHONE)||state.equals(CallState.CALL_WAITING)) {
                    Render.bindTexture(new ResourceLocation("bank", "call/calling.png"));
                    Render.setColor(0x80ffffff);
                    Render.drawTexturedRect(skin[0], skin[1], skin[2], skin[3]);
                }
                if (state.equals(CallState.ANSWER_PHONE)||state.equals(CallState.PUBLIC_ANSWER_PHONE)) {
                    Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "call/accept.png"));
                    Render.setColor(0xffffffff);
                    Render.drawTexturedRect(accept[0], accept[1], accept[2], accept[3]);
                    Render.drawString("§f§l"+(RegisterKeybind.callingAccept.getDisplayName().equals("RBRACKET")?"] ":RegisterKeybind.callingAccept.getDisplayName())+"§7키", (float) (accept[0]+(accept[2]/2.55)), (float) (accept[1]+(accept[3]/4.95)), (int) (str[2]/1.15), (int) ((int) str[3]/1.15),0,0x000000);
                }
            }
            if (!state.equals(CallState.PUBLIC_ANSWER_PHONE)) {
                Render.bindTexture(new ResourceLocation(Reference.MOD_ID, "call/deny.png"));
                Render.setColor(0xffffffff);
                Render.drawTexturedRect(deny[0], deny[1], deny[2], deny[3]);
                Render.drawString("§f§l" + (RegisterKeybind.callingDeny.getDisplayName().equals("LBRACKET") ? "[ " : RegisterKeybind.callingDeny.getDisplayName()) + "§7키", (float) (deny[0] + (deny[2] / 2.55)), (float) (deny[1] + (deny[3] / 4.95)), (int) (str[2] / 1.15), (int) ((int) str[3] / 1.15), 0, 0xFFFFFF);
            }
            if (!state.equals(CallState.PUBLIC_CALL)&&!state.equals(CallState.PUBLIC_ANSWER_PHONE)) Render.drawString(name.equals("none")?"$%^#":name, (float) str[0], (float) str[1], (int) str[2], (int) str[3],0,0x000000);
            GlStateManager.popMatrix();
        }
    }
    private boolean isSpecial(String name) {
        return icons.keySet().stream().anyMatch(name::contains);
    }
    protected String transYDTV(String nick) {
        return  "양띵".equalsIgnoreCase(nick) ? "d7297" : "삼식".equalsIgnoreCase(nick) ? "samsik23" :
                "루태".equalsIgnoreCase(nick) ? "RuTaeY" : "후추".equalsIgnoreCase(nick) ? "Huchu95" :
                "콩콩".equalsIgnoreCase(nick) ? "KonG7" : "눈꽃".equalsIgnoreCase(nick) ? "Noonkkob" :
                "다주".equalsIgnoreCase(nick) ? "Daju_" : "서넹".equalsIgnoreCase(nick) ? "Seoneng" : "none";
    }
    protected int getColorYD(String nick) {
        return  "양띵".equalsIgnoreCase(nick) ? 0xfff42e06 : "삼식".equalsIgnoreCase(nick) ? 0xff9f633e :
                "루태".equalsIgnoreCase(nick) ? 0xff0a9890 : "후추".equalsIgnoreCase(nick) ? 0xff202023 :
                "콩콩".equalsIgnoreCase(nick) ? 0xff0ae912 : "눈꽃".equalsIgnoreCase(nick) ? 0xffffffff :
                "다주".equalsIgnoreCase(nick) ? 0xffaa23e8 : "서넹".equalsIgnoreCase(nick) ? 0xfffe8dd2 : 0xffffffff;
    }

    public static enum CallState{
        ANSWER_PHONE("통화 받는중"),
        CALLING("통화중"),
        CALL_WAITING("통화 거는중"),
        PUBLIC_CALL("공공기관 통화중"),
        PUBLIC_ANSWER_PHONE("공공기관 입장 통화")
        ;
        CallState(String kr){
            
        }

    }
}
