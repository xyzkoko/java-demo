package com.sirius.design.statedemo;

/**
 * 状态模式 - 状态接口（State）
 *
 * <p>定义状态相关的所有行为方法。不同的具体状态类实现这些方法，
 * 从而使同一行为在不同状态下表现不同。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>声明与状态相关的行为方法</li>
 *   <li>作为所有具体状态类的统一接口</li>
 * </ul>
 *
 * <h3>设计要点：</h3>
 * <p>状态接口中的方法应该覆盖所有可能的行为。
 * 每个具体状态类根据自己的特点实现这些行为。</p>
 *
 * @see HappyState 高兴状态
 * @see HungryState 饥饿状态
 * @see TiredState 疲惫状态
 */
public interface State {

    /**
     * 吃东西行为
     * 不同状态下吃的表现不同
     */
    void eat();

    /**
     * 睡觉行为
     * 不同状态下睡的表现不同
     */
    void sleep();

    /**
     * 玩耍行为
     * 不同状态下玩的表现不同
     */
    void play();
}
