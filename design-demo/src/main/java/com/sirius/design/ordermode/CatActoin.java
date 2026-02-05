package com.sirius.design.ordermode;

/**
 * 命令模式 - 具体命令（ConcreteCommand）- 猫跳
 *
 * <p>具体命令类封装了接收者和要执行的动作。
 * 实现Command接口，在execute()中调用接收者的方法。</p>
 *
 * <h3>核心职责：</h3>
 * <ul>
 *   <li>持有接收者的引用</li>
 *   <li>实现execute()方法，委托给接收者执行</li>
 *   <li>封装"猫跳"这一特定请求</li>
 * </ul>
 *
 * <h3>命令封装的好处：</h3>
 * <p>调用者只需调用execute()，无需知道是哪个接收者、
 * 执行什么操作，实现了完全解耦。</p>
 *
 * @see Order 命令接口
 * @see Cat 接收者
 */
public class CatActoin implements Order {

    /**
     * 命令的接收者
     */
    private Cat cat;

    /**
     * 构造方法，绑定接收者
     *
     * @param cat 命令的接收者-猫
     */
    public CatActoin(Cat cat) {
        this.cat = cat;
    }

    /**
     * 执行命令
     * 委托给接收者Cat执行action()操作
     */
    @Override
    public void execute() {
        this.cat.action();
    }
}
