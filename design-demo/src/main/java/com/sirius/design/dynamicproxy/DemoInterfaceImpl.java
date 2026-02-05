package com.sirius.design.dynamicproxy;

/**
 * 被代理的实现类
 */
public class DemoInterfaceImpl implements DemoInterface {
    @Override
    public void demo1() {
        System.out.println("吃饭");
    }

    @Override
    public String demo2() {
        return "睡觉";
    }

    @Override
    public String demo3(String params) {
        String s = params + " 打豆豆";
        System.out.println(s);
        return s;
    }
}
