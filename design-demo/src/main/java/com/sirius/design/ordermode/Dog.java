package com.sirius.design.ordermode;

/**
 * 命令模式 - 接收者（Receiver）- 狗
 *
 * <p>接收者是实际执行操作的对象。命令对象持有接收者的引用，
 * 在execute()方法中调用接收者的具体方法来完成操作。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>定义实际执行操作的方法</li>
 *   <li>包含业务逻辑的具体实现</li>
 *   <li>不关心命令模式的结构</li>
 * </ul>
 *
 * <h3>与命令的关系：</h3>
 * <p>一个接收者可以对应多个命令（如Dog对应DogCall和DogEat），
 * 每个命令封装接收者的一个特定操作。</p>
 *
 * @see DogCall 狗叫命令
 * @see DogEat 狗吃命令
 */
public class Dog {

    /**
     * 狗吃东西动作
     * 被DogEat命令调用
     */
    public void eat() {
        System.out.println("吃骨头");
    }

    /**
     * 狗叫动作
     * 被DogCall命令调用
     */
    public void call() {
        System.out.println("汪汪叫");
    }
}
