package com.sirius.design.strategymode;

/**
 * 玩WOW游戏策略实现
 */
public class PlayWOW implements IPlay {
    @Override
    public void action(String name) {
        System.out.println(name + "在玩WOW");
    }
}
