package com.sirius.design.templatemode;

/**
 * 模板方法模式（Template Method Pattern）演示入口
 *
 * <p>模板方法模式在一个方法中定义一个算法的骨架，将某些步骤延迟到子类中实现。
 * 子类可以在不改变算法结构的情况下，重新定义算法的某些特定步骤。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 * AbstractClass（抽象类）
 *      Animal
 *        |
 *   templateMethod()    -- 模板方法（final，不可重写）
 *        |
 *   +----+----+
 *   |    |    |
 * eat() play() call()  -- 基本方法
 *  实现   实现   抽象（延迟到子类）
 *        |
 * ConcreteClass（具体类）
 *       Cat
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>AbstractClass（抽象类）</b>：{@link Animal}，定义模板方法和基本方法</li>
 *   <li><b>ConcreteClass（具体类）</b>：{@link Cat}，实现抽象方法</li>
 * </ul>
 *
 * <h3>模板方法中的方法类型：</h3>
 * <table border="1">
 *   <tr><th>方法类型</th><th>说明</th><th>示例</th></tr>
 *   <tr><td>模板方法</td><td>定义算法骨架，调用其他方法</td><td>action()</td></tr>
 *   <tr><td>具体方法</td><td>抽象类已实现，子类直接使用</td><td>eat()、play()</td></tr>
 *   <tr><td>抽象方法</td><td>子类必须实现</td><td>call()</td></tr>
 *   <tr><td>钩子方法</td><td>空实现或默认实现，子类可选重写</td><td>（本例未展示）</td></tr>
 * </table>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>多个子类有共同的行为逻辑，只有部分步骤不同</li>
 *   <li>需要控制子类扩展的情况（防止子类改变算法骨架）</li>
 *   <li>框架设计中的扩展点（如Spring的JdbcTemplate）</li>
 * </ul>
 *
 * @see Animal 抽象模板类
 * @see Cat 具体实现类
 */
public class App {

    public static void main(String[] args) {
        // 使用抽象类引用指向具体子类
        Animal animal = new Cat();
        // 调用模板方法，执行完整的算法流程
        // 输出: 会吃 -> 会玩 -> 喵喵叫
        animal.action();
    }
}
