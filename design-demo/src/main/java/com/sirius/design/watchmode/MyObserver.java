package com.sirius.design.watchmode;

import java.util.Observable;
import java.util.Observer;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * 观察者模式(订阅者)
 */
public class MyObserver implements Observer {
    private BiConsumer<Observable, Object> biConsumer;

    /**
     * 注册观察者
     *
     * @param subject    主题
     * @param biConsumer 执行函数
     */
    public MyObserver(Subject subject, BiConsumer<Observable, Object> biConsumer) {
        subject.addObserver(this);
        this.biConsumer = biConsumer;
    }

    @Override
    public void update(Observable o, Object arg) {
        biConsumer.accept(o, arg);
    }
}
