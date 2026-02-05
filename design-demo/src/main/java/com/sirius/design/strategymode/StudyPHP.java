package com.sirius.design.strategymode;

/**
 * 学习PHP策略实现
 */
public class StudyPHP implements IStudy {
    @Override
    public void action(String name) {
        System.out.println(name + "学习PHP");
    }
}
