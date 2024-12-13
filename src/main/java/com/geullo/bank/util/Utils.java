package com.geullo.bank.util;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;

public class Utils {
    public static boolean mouseBetweenIcon(double mouseX,double mouseY,double x, double y, double width, double height){
        return (mouseX >= x&&mouseY >= y&&mouseX <= x + width&&mouseY <= y + height);
    }
    public static boolean mouseBetweenIcon(int mouseX,int mouseY,int x, int y, int width, int height){
        return (mouseX >= x&&mouseY >= y&&mouseX <= x + width&&mouseY <= y + height);
    }
    public static double percentPartial(double total,double partial){
        return partial/total*100;
    }

    public static double percent(double total,double percentage){
        return total*percentage/100;
    }
    public static void saveOnlineImage(String downloadUrl,File pasteFile) throws IOException{
        InputStream is = null;
        try {
            URL url = new URL(downloadUrl);
            URLConnection con = url.openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            con.getInputStream();
            is = con.getInputStream();
            if (pasteFile.exists()) pasteFile.delete();
            Files.copy(is, pasteFile.toPath());
        }finally {
            if (is!=null) {
                is.close();
            }
        }
    }
    public static boolean shiftClicked(){
        return Keyboard.isKeyDown(Keyboard.KEY_RSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
    }
}
