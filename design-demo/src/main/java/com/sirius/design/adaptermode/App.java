package com.sirius.design.adaptermode;

/**
 * 适配器模式 在不改变原来类的基础上修改方法的实现
 */
public class App {

    public static void main(String[] args) {
        study(new StudyPHP());
        // 通过适配器改变原来的方法内容
        study(new StudyJava());
    }

    private static void study(StudyPHP studyPHP) {
        studyPHP.action();
    }
}
