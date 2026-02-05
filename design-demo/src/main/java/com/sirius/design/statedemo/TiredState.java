package com.sirius.design.statedemo;

/**
 * 状态模式 - 具体状态（ConcreteState）- 疲惫状态
 *
 * <p>疲惫状态是猫玩耍后的状态。在这个状态下，
 * 猫只想睡觉，不想吃也不想玩。</p>
 *
 * <h3>状态特征：</h3>
 * <ul>
 *   <li>eat() - 不执行（太累了）</li>
 *   <li>sleep() - 执行，并转换到Hungry状态</li>
 *   <li>play() - 不执行（没力气）</li>
 * </ul>
 *
 * <h3>状态转换：</h3>
 * <pre>
 * TiredState --sleep()--> HungryState
 * </pre>
 *
 * @see State 状态接口
 * @see Cat 上下文类
 */
public class TiredState implements State {

    /**
     * 上下文引用
     * 用于状态转换时调用cat.setState()
     */
    private Cat cat;

    /**
     * 构造方法，绑定上下文
     *
     * @param cat 上下文对象
     */
    public TiredState(Cat cat) {
        this.cat = cat;
    }

    /**
     * 吃东西 - 疲惫状态下不执行
     * 因为太累了，不想吃
     */
    @Override
    public void eat() {
        System.out.println("×累了不吃");
    }

    /**
     * 睡觉 - 疲惫状态下执行
     * 执行后状态转换为Hungry（饥饿）
     */
    @Override
    public void sleep() {
        System.out.println("√累了睡觉");
        // 睡醒后会饿，状态转换为饥饿状态
        cat.setState(cat.hungryState);
    }

    /**
     * 玩耍 - 疲惫状态下不执行
     * 因为没力气，不想玩
     */
    @Override
    public void play() {
        System.out.println("×累了不玩");
    }
}
