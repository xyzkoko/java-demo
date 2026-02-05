package com.sirius.design.statedemo;

/**
 * 状态模式 - 上下文类（Context）
 *
 * <p>上下文类持有当前状态的引用，并将所有行为委托给当前状态对象处理。
 * 状态的转换由具体状态类在执行行为时完成。</p>
 *
 * <h3>核心职责：</h3>
 * <ul>
 *   <li>维护当前状态的引用</li>
 *   <li>提供状态切换的方法</li>
 *   <li>将行为请求委托给当前状态对象</li>
 *   <li>持有所有可能状态的实例（避免重复创建）</li>
 * </ul>
 *
 * <h3>状态实例管理：</h3>
 * <p>本例中，Cat预先创建了所有状态实例，状态切换时直接引用，
 * 避免频繁创建对象。这是状态模式的常见优化方式。</p>
 *
 * <h3>状态转换逻辑：</h3>
 * <pre>
 * 当前状态      执行行为      下一状态
 * ─────────────────────────────────
 * Happy       play()       Tired
 * Tired       sleep()      Hungry
 * Hungry      eat()        Happy
 * </pre>
 *
 * @see State 状态接口
 * @see HappyState 高兴状态
 * @see HungryState 饥饿状态
 * @see TiredState 疲惫状态
 */
public class Cat {

    /**
     * 高兴状态实例（预创建，避免重复创建）
     */
    public HappyState happyState = new HappyState(this);

    /**
     * 饥饿状态实例（预创建，避免重复创建）
     */
    public HungryState hungryState = new HungryState(this);

    /**
     * 疲惫状态实例（预创建，避免重复创建）
     */
    public TiredState tiredState = new TiredState(this);

    /**
     * 当前状态引用
     * 初始状态为高兴状态
     */
    private State state = new HappyState(this);

    /**
     * 设置当前状态
     * 由具体状态类调用，完成状态转换
     *
     * @param state 新的状态
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 吃东西
     * 委托给当前状态对象处理
     */
    public void eat() {
        state.eat();
    }

    /**
     * 玩耍
     * 委托给当前状态对象处理
     */
    public void play() {
        state.play();
    }

    /**
     * 睡觉
     * 委托给当前状态对象处理
     */
    public void sleep() {
        state.sleep();
    }
}
