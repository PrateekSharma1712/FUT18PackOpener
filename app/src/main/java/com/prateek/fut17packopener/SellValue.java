package com.prateek.fut17packopener;

/**
 * Created by prateek on 20/8/17.
 */

public enum SellValue {

    BASIC_75(100),
    BASIC_76(120),
    BASIC_77(140),
    BASIC_78(160),
    BASIC_79(180),
    BASIC_80(200),
    BASIC_81(220),
    BASIC_82(240),
    BASIC_83(260),
    BASIC_84(280),
    BASIC_85(300),
    BASIC_86(350),
    BASIC_87(400),
    BASIC_88(450),
    BASIC_89(500),
    BASIC_90(550),
    BASIC_91(600),
    BASIC_92(650),
    BASIC_93(700),
    BASIC_94(750),
    BASIC_95(800),
    BASIC_96(850),
    BASIC_97(900),
    BASIC_98(950),
    BASIC_99(1000);

    private long coins;

    SellValue(long coins) {
        this.coins = coins;
    }

    public long coins() {
        return coins;
    }

}
