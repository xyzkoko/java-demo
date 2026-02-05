package com.sirius.design.decoratormode;

/**
 * 装饰器模式（Decorator Pattern）演示入口
 *
 * <p>装饰器模式允许在不改变原有对象结构的情况下，动态地给对象添加新功能。
 * 它是继承关系的一种替代方案，比继承更加灵活。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 *       Component（组件接口）
 *            Human
 *              |
 *    +---------+---------+
 *    |                   |
 * ConcreteComponent   Decorator（装饰器）
 *    Man              DecoratorMan
 *                         |
 *                   持有Component引用
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Component（组件接口）</b>：{@link Human}，定义对象的接口</li>
 *   <li><b>ConcreteComponent（具体组件）</b>：{@link Man}，被装饰的原始对象</li>
 *   <li><b>Decorator（装饰器）</b>：{@link DecoratorMan}，持有组件引用，扩展功能</li>
 * </ul>
 *
 * <h3>装饰器 vs 继承：</h3>
 * <table border="1">
 *   <tr><th>特性</th><th>装饰器模式</th><th>继承</th></tr>
 *   <tr><td>扩展方式</td><td>组合（运行时）</td><td>继承（编译时）</td></tr>
 *   <tr><td>灵活性</td><td>可动态添加/移除功能</td><td>静态固定</td></tr>
 *   <tr><td>类数量</td><td>较少</td><td>可能产生类爆炸</td></tr>
 *   <tr><td>复杂度</td><td>多层装饰可能复杂</td><td>层次清晰</td></tr>
 * </table>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>Java IO流（BufferedInputStream装饰FileInputStream）</li>
 *   <li>动态添加日志、缓存、权限检查等横切关注点</li>
 *   <li>需要动态、透明地扩展对象功能时</li>
 * </ul>
 *
 * @see Human 组件接口
 * @see Man 具体组件
 * @see DecoratorMan 装饰器
 */
public class App {

    public static void main(String[] args) {
        // 创建原始对象（具体组件）
        Human human = new Man();
        // 调用原始方法
        human.name();  // 输出: 我是人类
        human.age();   // 输出: 我28岁

        // 使用装饰器包装原始对象，扩展功能
        DecoratorMan decoratorMan = new DecoratorMan(human);
        // 调用装饰后的方法（原有功能 + 新增功能）
        decoratorMan.name();  // 输出: 我是人类 + 我是装饰人
        decoratorMan.age();   // 输出: 我28岁 + 我打扮成18岁
    }
}
