package com.sirius.design.strategymode;

/**
 * 玩CS游戏策略实现
 */
public class PlayCS implements IPlay {
    @Override
    public void action(String name) {
        System.out.println(name + "在玩CS");
    }
}
