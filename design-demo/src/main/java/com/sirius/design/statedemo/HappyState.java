package com.sirius.design.statedemo;

/**
 * 状态模式 - 具体状态（ConcreteState）- 高兴状态
 *
 * <p>高兴状态是猫吃饱后的状态。在这个状态下，
 * 猫想玩耍，但不想吃也不想睡。</p>
 *
 * <h3>状态特征：</h3>
 * <ul>
 *   <li>eat() - 不执行（已经吃饱）</li>
 *   <li>sleep() - 不执行（精力充沛）</li>
 *   <li>play() - 执行，并转换到Tired状态</li>
 * </ul>
 *
 * <h3>状态转换：</h3>
 * <pre>
 * HappyState --play()--> TiredState
 * </pre>
 *
 * @see State 状态接口
 * @see Cat 上下文类
 */
public class HappyState implements State {

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
    public HappyState(Cat cat) {
        this.cat = cat;
    }

    /**
     * 吃东西 - 高兴状态下不执行
     * 因为刚吃饱，不饿
     */
    @Override
    public void eat() {
        System.out.println("×吃过了不饿");
    }

    /**
     * 睡觉 - 高兴状态下不执行
     * 因为精力充沛，不困
     */
    @Override
    public void sleep() {
        System.out.println("×睡过了不睡");
    }

    /**
     * 玩耍 - 高兴状态下执行
     * 执行后状态转换为Tired（疲惫）
     */
    @Override
    public void play() {
        System.out.println("√高兴了就玩");
        // 玩累了，状态转换为疲惫状态
        cat.setState(cat.tiredState);
    }
}
