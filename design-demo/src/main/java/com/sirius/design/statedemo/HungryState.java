package com.sirius.design.statedemo;

/**
 * 状态模式 - 具体状态（ConcreteState）- 饥饿状态
 *
 * <p>饥饿状态是猫睡醒后的状态。在这个状态下，
 * 猫只想吃东西，不想玩也不想睡。</p>
 *
 * <h3>状态特征：</h3>
 * <ul>
 *   <li>eat() - 执行，并转换到Happy状态</li>
 *   <li>sleep() - 不执行（肚子饿）</li>
 *   <li>play() - 不执行（没力气）</li>
 * </ul>
 *
 * <h3>状态转换：</h3>
 * <pre>
 * HungryState --eat()--> HappyState
 * </pre>
 *
 * @see State 状态接口
 * @see Cat 上下文类
 */
public class HungryState implements State {

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
    public HungryState(Cat cat) {
        this.cat = cat;
    }

    /**
     * 吃东西 - 饥饿状态下执行
     * 执行后状态转换为Happy（高兴）
     */
    @Override
    public void eat() {
        System.out.println("√饿了吃饭");
        // 吃饱了，状态转换为高兴状态
        cat.setState(cat.happyState);
    }

    /**
     * 睡觉 - 饥饿状态下不执行
     * 因为肚子饿，睡不着
     */
    @Override
    public void sleep() {
        System.out.println("×饿了不睡");
    }

    /**
     * 玩耍 - 饥饿状态下不执行
     * 因为没力气，不想玩
     */
    @Override
    public void play() {
        System.out.println("×饿了不玩");
    }
}
