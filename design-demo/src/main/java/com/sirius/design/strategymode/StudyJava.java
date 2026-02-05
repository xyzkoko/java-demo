package com.sirius.design.strategymode;

/**
 * 学习Java策略实现
 */
public class StudyJava implements IStudy {
    @Override
    public void action(String name) {
        System.out.println(name + "学习Java");
    }
}
