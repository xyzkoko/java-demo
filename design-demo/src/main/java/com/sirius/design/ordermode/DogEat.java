package com.sirius.design.ordermode;

/**
 * 命令模式 - 具体命令（ConcreteCommand）- 狗吃
 *
 * <p>具体命令类封装了接收者和要执行的动作。
 * 实现Command接口，在execute()中调用接收者的方法。</p>
 *
 * <h3>核心职责：</h3>
 * <ul>
 *   <li>持有接收者的引用</li>
 *   <li>实现execute()方法，委托给接收者执行</li>
 *   <li>封装"狗吃东西"这一特定请求</li>
 * </ul>
 *
 * @see Order 命令接口
 * @see Dog 接收者
 */
public class DogEat implements Order {

    /**
     * 命令的接收者
     */
    private Dog dog;

    /**
     * 构造方法，绑定接收者
     *
     * @param dog 命令的接收者-狗
     */
    public DogEat(Dog dog) {
        this.dog = dog;
    }

    /**
     * 执行命令
     * 委托给接收者Dog执行eat()操作
     */
    @Override
    public void execute() {
        this.dog.eat();
    }
}
