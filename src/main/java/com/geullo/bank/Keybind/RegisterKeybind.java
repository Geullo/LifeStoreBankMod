package com.geullo.bank.Keybind;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
@SideOnly(Side.CLIENT)
public class RegisterKeybind {
    public static final String KeyCategory = "§f휴대폰";
    public static KeyBinding callingAccept = new KeyBinding("§a통화 수락", Keyboard.KEY_RBRACKET,KeyCategory);
    public static KeyBinding callingDeny = new KeyBinding("§c통화 거절", Keyboard.KEY_LBRACKET,KeyCategory);
}
