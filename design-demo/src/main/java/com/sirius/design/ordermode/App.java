package com.sirius.design.ordermode;

import java.util.ArrayList;
import java.util.List;

/**
 * 命令模式（Command Pattern）演示入口
 *
 * <p>命令模式将请求封装成对象，使发出请求的对象和执行请求的对象解耦。
 * 支持请求排队、日志记录、撤销操作等功能。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 * Invoker（调用者）        Command（命令接口）       Receiver（接收者）
 *      App       ------>      Order        ------>   Cat / Dog
 *       |                       |
 *  执行命令列表        +--------+--------+
 *                      |        |        |
 *                 CatAction  CatCall  DogCall  DogEat
 *                 （具体命令）
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Command（命令接口）</b>：{@link Order}，定义执行操作的接口</li>
 *   <li><b>ConcreteCommand（具体命令）</b>：{@link CatActoin}、{@link CatCall}等，封装接收者和动作</li>
 *   <li><b>Receiver（接收者）</b>：{@link Cat}、{@link Dog}，执行实际操作的对象</li>
 *   <li><b>Invoker（调用者）</b>：App，持有命令并触发执行</li>
 * </ul>
 *
 * <h3>命令模式的优点：</h3>
 * <ul>
 *   <li>解耦调用者和接收者</li>
 *   <li>容易扩展新命令</li>
 *   <li>支持命令队列和批量执行</li>
 *   <li>支持撤销（Undo）和重做（Redo）操作</li>
 *   <li>支持日志记录和事务</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>GUI按钮和菜单操作</li>
 *   <li>事务处理系统</li>
 *   <li>任务队列和线程池</li>
 *   <li>宏命令（组合多个命令）</li>
 * </ul>
 *
 * @see Order 命令接口
 * @see Cat 接收者-猫
 * @see Dog 接收者-狗
 */
public class App {

    /**
     * 命令列表（命令队列）
     * 存储待执行的命令对象
     */
    private List<Order> list = new ArrayList<>();

    public static void main(String[] args) {
        // 创建调用者
        App app = new App();

        // 创建接收者
        Cat cat = new Cat();
        Dog dog = new Dog();

        // 创建具体命令并加入命令队列
        app.setList(new CatActoin(cat));  // 猫跳命令
        app.setList(new CatCall(cat));    // 猫叫命令
        app.setList(new DogCall(dog));    // 狗叫命令
        app.setList(new DogEat(dog));     // 狗吃命令

        // 批量执行所有命令
        app.executes();
    }

    /**
     * 添加命令到队列
     *
     * @param order 命令对象
     */
    private void setList(Order order) {
        list.add(order);
    }

    /**
     * 执行所有命令
     * 遍历命令队列，依次执行每个命令
     */
    private void executes() {
        for (Order order : list) {
            order.execute();
        }
    }

}
