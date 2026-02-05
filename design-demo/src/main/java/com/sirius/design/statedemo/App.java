package com.sirius.design.statedemo;

/**
 * 状态模式（State Pattern）演示入口
 *
 * <p>状态模式允许对象在内部状态改变时改变它的行为，
 * 对象看起来好像修改了它的类。核心思想是将状态封装成独立的类。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 *      Context（上下文）           State（状态接口）
 *          Cat         ------>       State
 *           |                          |
 *      持有当前状态           +--------+--------+
 *      委托状态处理           |        |        |
 *                        Happy    Hungry    Tired
 *                        State    State     State
 *                       （具体状态）
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Context（上下文）</b>：{@link Cat}，持有当前状态，将行为委托给状态对象</li>
 *   <li><b>State（状态接口）</b>：{@link State}，定义状态相关的行为接口</li>
 *   <li><b>ConcreteState（具体状态）</b>：{@link HappyState}、{@link HungryState}、{@link TiredState}</li>
 * </ul>
 *
 * <h3>状态转换图：</h3>
 * <pre>
 *   HappyState --play()--> TiredState
 *   TiredState --sleep()--> HungryState
 *   HungryState --eat()--> HappyState
 * </pre>
 *
 * <h3>状态模式 vs if-else/switch：</h3>
 * <table border="1">
 *   <tr><th>特性</th><th>状态模式</th><th>if-else/switch</th></tr>
 *   <tr><td>扩展性</td><td>新增状态只需添加类</td><td>需修改所有条件分支</td></tr>
 *   <tr><td>可维护性</td><td>状态逻辑集中在各状态类</td><td>逻辑分散在条件中</td></tr>
 *   <tr><td>符合OCP</td><td>是</td><td>否</td></tr>
 * </table>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>对象的行为依赖于它的状态</li>
 *   <li>需要在运行时根据状态改变行为</li>
 *   <li>代码中包含大量与对象状态相关的条件语句</li>
 *   <li>状态机实现（如订单状态、工作流）</li>
 * </ul>
 *
 * @see Cat 上下文类
 * @see State 状态接口
 * @see HappyState 高兴状态
 * @see HungryState 饥饿状态
 * @see TiredState 疲惫状态
 */
public class App {

    public static void main(String[] args) {
        // 创建上下文对象，初始状态为HappyState
        Cat cat = new Cat();

        // 初始状态：Happy（高兴）
        cat.eat();    // ×吃过了不饿（高兴状态不吃）
        cat.play();   // √高兴了就玩 -> 状态变为Tired
        cat.sleep();  // √累了睡觉 -> 状态变为Hungry
        cat.play();   // ×饿了不玩（饥饿状态不玩）
        cat.eat();    // √饿了吃饭 -> 状态变为Happy
        cat.sleep();  // ×睡过了不睡（高兴状态不睡）
        cat.sleep();  // ×睡过了不睡
        cat.eat();    // ×吃过了不饿
        cat.play();   // √高兴了就玩 -> 状态变为Tired
    }
}
