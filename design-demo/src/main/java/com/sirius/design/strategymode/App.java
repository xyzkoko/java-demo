package com.sirius.design.strategymode;

/**
 * 策略模式（Strategy Pattern）演示入口
 *
 * <p>策略模式定义了一系列算法，并将每个算法封装起来，使它们可以相互替换。
 * 策略模式让算法的变化独立于使用算法的客户端。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 * Context（上下文）           Strategy（策略接口）
 *     People      --------->   IStudy / IPlay
 *                                  |
 *                    +-------------+-------------+
 *                    |             |             |
 *                StudyJava    StudyPHP      PlayCS    PlayWOW
 *                （具体策略）
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>Strategy（策略接口）</b>：{@link IStudy}、{@link IPlay}，定义算法的公共接口</li>
 *   <li><b>ConcreteStrategy（具体策略）</b>：{@link StudyJava}、{@link PlayCS}等，实现具体算法</li>
 *   <li><b>Context（上下文）</b>：{@link People}，持有策略引用，可动态切换</li>
 * </ul>
 *
 * <h3>与继承的对比：</h3>
 * <ul>
 *   <li>继承：行为在编译时确定，难以改变</li>
 *   <li>策略：行为在运行时通过组合确定，灵活可变</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>需要在运行时动态选择算法</li>
 *   <li>有多种类似的行为，只是具体实现不同</li>
 *   <li>需要避免使用多重条件选择语句（if-else/switch）</li>
 * </ul>
 *
 * @see People 上下文类
 * @see IStudy 学习策略接口
 * @see IPlay 游戏策略接口
 */
public class App {

    public static void main(String[] args) {
        // 创建上下文对象（张三）
        People people1 = new People("张三");
        // 动态设置策略：玩CS、学Java
        people1.setPlay(new PlayCS()).setStudy(new StudyJava());
        people1.play();   // 输出: 张三玩CS
        people1.study();  // 输出: 张三学Java

        // 创建另一个上下文对象（李四）
        People people2 = new People("李四");
        // 动态设置不同的策略：玩WOW、学PHP
        people2.setStudy(new StudyPHP()).setPlay(new PlayWOW());
        people2.play();   // 输出: 李四玩WOW
        people2.study();  // 输出: 李四学PHP
    }
}
