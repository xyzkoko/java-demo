package com.sirius.design.dacadedemo;

/**
 * 外观模式（Facade Pattern）演示入口
 *
 * <p>外观模式为子系统中的一组接口提供一个统一的高层接口，
 * 使得子系统更容易使用。它隐藏了系统的复杂性。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 *        Client（客户端）
 *             |
 *        Facade（外观）
 *           Cats
 *             |
 *    +--------+--------+
 *    |        |        |
 *  Cat01    Cat02    Cat03
 * （子系统组件）
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Facade（外观类）</b>：{@link Cats}，提供统一接口，协调子系统</li>
 *   <li><b>SubSystem（子系统）</b>：{@link Cat01}、{@link Cat02}、{@link Cat03}，实现具体功能</li>
 * </ul>
 *
 * <h3>外观模式的优点：</h3>
 * <ul>
 *   <li>简化客户端调用，隐藏子系统复杂性</li>
 *   <li>解耦客户端与子系统</li>
 *   <li>更好的分层结构</li>
 *   <li>符合迪米特法则（最少知识原则）</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>为复杂子系统提供简单接口</li>
 *   <li>分层系统中的层间接口</li>
 *   <li>遗留系统的封装</li>
 *   <li>Spring的JdbcTemplate就是外观模式的典型应用</li>
 * </ul>
 *
 * @see Cats 外观类
 * @see Cat01 子系统组件1
 * @see Cat02 子系统组件2
 * @see Cat03 子系统组件3
 */
public class App {

    public static void main(String[] args) {
        // 客户端只需与外观类交互，无需了解子系统的复杂性
        Cats cats = new Cats();
        // 外观类内部协调多个子系统组件完成操作
        cats.call();  // 输出: 喵 + 喵喵 + 喵喵喵
    }
}
