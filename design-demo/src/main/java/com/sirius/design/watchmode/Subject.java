package com.sirius.design.watchmode;

import java.util.Observable;

/**
 * 观察者模式 - 主题类（Subject/被观察者）
 *
 * <p>继承JDK的Observable类，作为可被订阅的主题。
 * 当主题状态改变时，通知所有订阅的观察者。</p>
 *
 * <h3>Observable核心方法：</h3>
 * <ul>
 *   <li><b>addObserver()</b>：添加观察者</li>
 *   <li><b>deleteObserver()</b>：移除观察者</li>
 *   <li><b>setChanged()</b>：标记状态已改变（必须调用）</li>
 *   <li><b>notifyObservers()</b>：通知所有观察者</li>
 * </ul>
 *
 * <h3>通知流程：</h3>
 * <ol>
 *   <li>状态改变</li>
 *   <li>调用setChanged()标记变化</li>
 *   <li>调用notifyObservers()发送通知</li>
 *   <li>所有观察者的update()方法被调用</li>
 * </ol>
 *
 * <h3>注意事项：</h3>
 * <p>必须先调用setChanged()，否则notifyObservers()不会通知观察者。
 * 这是Observable的保护机制，防止重复通知。</p>
 *
 * @see java.util.Observable
 * @see MyObserver
 */
public class Subject extends Observable {

    /**
     * 主题的消息内容
     */
    private String msg;

    /**
     * 设置消息并通知所有观察者
     *
     * <p>此方法会触发观察者通知机制：</p>
     * <ol>
     *   <li>更新消息内容</li>
     *   <li>setChanged()标记状态已改变</li>
     *   <li>notifyObservers()通知所有订阅者</li>
     * </ol>
     *
     * @param msg 要发送的消息
     */
    public void setMsg(String msg) {
        this.msg = msg;
        // 必须调用setChanged()，否则notifyObservers()不会生效
        setChanged();
        // 通知所有观察者，传递消息内容
        notifyObservers(msg);
    }


}
