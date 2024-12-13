package com.geullo.bank;

public class Bank {
    public Integer money,avillMoney;
    public Bank(Integer money,Integer avillMoney){
        this.money = money;
        this.avillMoney = avillMoney;
    }
    public void sendMoney(String target,int amt){
        Packet.sendMessage(PacketList.SEND_MONEY.recogCode+"-!"+convertYD(target)+"-!"+amt);
    }
    public void sendPoint(String target,int amt){
        Packet.sendMessage(PacketList.SEND_POINT.recogCode+"-!"+convertYD(target)+"-!"+amt);
    }
    private String convertYD(String name){
        return "d7297".equals(name)?"양띵": "samsik23".equals(name)?"삼식":
                "RuTaeY".equals(name)?"루태": "KonG7".equals(name)?"콩콩":
                "Huchu95".equals(name)?"후추": "Seoneng".equals(name)?"서넹":
                "Daju_".equals(name)?"다주": "Noonkkob".equals(name)?"눈꽃":name;
    }
}
