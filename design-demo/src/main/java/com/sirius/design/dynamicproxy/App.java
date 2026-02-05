package com.sirius.design.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 动态代理
 */
public class App {

    public static void main(String[] args) {
        DemoInterface demoInterface = (DemoInterface) Proxy.newProxyInstance(DemoInterfaceImpl.class.getClassLoader(), DemoInterfaceImpl.class.getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("demo3")) {
                    String s = args[0] + " 打不打豆豆呢?";
                    System.out.println(s);
                    return false;
                }
                return method.invoke(new DemoInterfaceImpl(), args);
            }
        });

        demoInterface.demo1();
        System.out.println(demoInterface.demo2());
        Object o = demoInterface.demo3("喵喵");
        System.out.println(o);
    }
}
