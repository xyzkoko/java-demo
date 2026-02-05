package com.sirius.design.adaptermode;

/**
 * 适配器类（Adapter）
 * 继承被适配类，重写方法以适配新需求
 */
public class StudyJava extends StudyPHP {

    public void action() {
        System.out.print("现在学习Java 不再");
        super.action();
    }
}
