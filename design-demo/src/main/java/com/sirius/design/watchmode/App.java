package com.sirius.design.watchmode;

import java.util.Observable;

/**
 * 观察者模式（Observer Pattern）演示入口
 *
 * <p>观察者模式定义了对象之间的一对多依赖关系，当一个对象（主题）状态改变时，
 * 所有依赖它的对象（观察者）都会收到通知并自动更新。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 *         Subject（主题/被观察者）
 *               |
 *    +----------+----------+
 *    |          |          |
 * Observer1  Observer2  Observer3
 * （观察者）
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Subject（主题）</b>：{@link Subject}，继承Observable，维护观察者列表</li>
 *   <li><b>Observer（观察者）</b>：{@link MyObserver}，实现Observer接口，接收通知</li>
 * </ul>
 *
 * <h3>JDK内置支持：</h3>
 * <ul>
 *   <li>java.util.Observable：可观察的主题基类</li>
 *   <li>java.util.Observer：观察者接口（已在JDK9标记废弃）</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>事件驱动系统</li>
 *   <li>发布-订阅模式</li>
 *   <li>MVC架构中Model与View的解耦</li>
 *   <li>消息通知系统</li>
 * </ul>
 *
 * @see Subject 主题类
 * @see MyObserver 观察者类
 */
public class App {

    public static void main(String[] args) {
        // 创建可订阅的主题（被观察者）
        Subject subject = new Subject();

        // 创建观察者1，订阅主题，使用Lambda定义回调
        MyObserver myObserver1 = new MyObserver(subject, (o, arg) -> new App().biConsumer1(o, arg));

        // 创建观察者2，订阅同一主题
        MyObserver myObserver2 = new MyObserver(subject, (o, arg) -> new App().biConsumer2(o, arg));

        // 主题发送消息，所有观察者都会收到通知
        subject.setMsg("Sirius");
    }

    /**
     * 观察者1的回调方法
     *
     * @param o   被观察的主题对象
     * @param arg 传递的消息参数
     */
    public void biConsumer1(Observable o, Object arg) {
        System.out.println(this + " 接收 " + o + " 发来消息 " + arg);
    }

    /**
     * 观察者2的回调方法
     *
     * @param o   被观察的主题对象
     * @param arg 传递的消息参数
     */
    public void biConsumer2(Observable o, Object arg) {
        System.out.println(this + " 接收 " + o + " 发来消息 " + arg);
    }
}
