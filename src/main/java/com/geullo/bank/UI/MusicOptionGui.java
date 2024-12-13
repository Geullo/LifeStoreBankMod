package com.geullo.bank.UI;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenOptionsSounds;
import net.minecraft.client.settings.GameSettings;

import java.util.Collections;

public class MusicOptionGui extends GuiScreenOptionsSounds {
    private String offDisplayString;
    public MusicOptionGui(GuiScreen parentIn, GameSettings settingsIn) {
        super(parentIn, settingsIn);
    }

    @Override
    public void initGui()
    {
        super.initGui();
        if (!buttonList.isEmpty()) {
            Collections.reverse(buttonList);
            buttonList.forEach(b->{
                if (b.id>=10&&(b.id<200)) {
                    b.x=buttonList.get(buttonList.indexOf(b)+3).x;
                    b.y=buttonList.get(buttonList.indexOf(b)+3).y;
                }
                if (b.id<=9&&b.id>1) {
                    b.x=buttonList.get(buttonList.indexOf(b)+1).x;
                    b.y=buttonList.get(buttonList.indexOf(b)+1).y;
                }/*
                if (b.id==10) {
                    b.enabled = false;
                    b.visible = false;
                }*/
            });
            Collections.reverse(buttonList);
            buttonList.removeIf(button -> (button.id == 8 || button.id == 9 || button.id == 1));
        }
    }
}
