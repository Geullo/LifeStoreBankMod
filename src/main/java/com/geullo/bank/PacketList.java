package com.geullo.bank;

public enum PacketList {
    GET_MONEY( "00"),
    GET_PLAYER_JOB("01"),
    GET_PLAYER_JOB_POINT("02"),
    GET_TOWN_CONTRIBUTE("03"),
    OPEN_BANK_UI("04"),
    SEND_MONEY("05"),
    SEND_POINT("06"),
    OPEN_SHOP_UI("07"),
    SELL_ITEM("08"),
    BUY_ITEM("09"),
    ITEM_ADD("10"),
    OPEN_COIN_CHANGE_UI("11"),
    COIN_CHANGE("12"),
    GET_CUSTOM_NAME("13"),
    GET_CONTRIBUTION("14"),
    ADD_PLAYER_LIST("15"),
    ADD_JOB_POINT("16"),
    CALL_PLAYER("17"),
    WARP_STATION("18"),
    ADD_PHONE_BOOK("19"),
    OPEN_PHONE_BOOK("20"),
    PHONE_BANK_OPEN("21"),
    ADD_FAVOR("22"),
    GET_STORE_LEVEL("23"),
    SHOW_FAVOR("24"),
    CALLING_PLAYER("25"),
    ACCEPT_CALLING("26"),
    DENY_CALLING("27"),
    MUSIC_PLAY("29"),
    MUSIC_STOP("30"),
    CHAT_CLEAR("31"),
    BUSSTATION("35")
    ;
    public String recogCode;
    PacketList(String recogCode){
        this.recogCode = recogCode;
    }
}
