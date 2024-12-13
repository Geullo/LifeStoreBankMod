package com.geullo.bank.Keybind;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class A {
    private int tick = 0;
    private CallingKey callingKey;
    public A(CallingKey callingKey) {
        this.callingKey = callingKey;
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void b(TickEvent.ClientTickEvent e) {
        if (tick>=50) {
            callingKey.pkLen--;
            MinecraftForge.EVENT_BUS.unregister(this);
        }
        tick++;
    }
}
