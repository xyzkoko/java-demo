package com.sirius.design.templatemode;

/**
 * 模板方法模式 - 抽象模板类（AbstractClass）
 *
 * <p>定义算法的骨架（模板方法），将某些步骤延迟到子类实现。
 * 这样既保证了算法的整体结构不变，又允许子类定制特定步骤。</p>
 *
 * <h3>类中方法的分类：</h3>
 * <ul>
 *   <li><b>模板方法（action）</b>：定义算法骨架，按顺序调用各步骤方法</li>
 *   <li><b>具体方法（eat、play）</b>：已有默认实现，子类可直接继承使用</li>
 *   <li><b>抽象方法（call）</b>：声明为abstract，强制子类实现</li>
 * </ul>
 *
 * <h3>设计要点：</h3>
 * <ul>
 *   <li>模板方法通常声明为final，防止子类修改算法结构</li>
 *   <li>抽象方法是算法中的"可变部分"，由子类定制</li>
 *   <li>具体方法是算法中的"不变部分"，由父类统一实现</li>
 * </ul>
 *
 * <h3>好莱坞原则：</h3>
 * <p>"不要打电话给我们，我们会打电话给你。"</p>
 * <p>父类调用子类的方法，而不是反过来。控制反转的思想。</p>
 *
 * @see Cat 具体实现类
 */
public abstract class Animal {

    /**
     * 具体方法 - 吃
     * 默认实现，子类可直接使用或重写
     */
    public void eat() {
        System.out.println("会吃");
    }

    /**
     * 具体方法 - 玩
     * 默认实现，子类可直接使用或重写
     */
    public void play() {
        System.out.println("会玩");
    }

    /**
     * 抽象方法 - 叫声
     * 不同动物叫声不同，强制子类实现
     */
    public abstract void call();

    /**
     * 模板方法 - 定义算法骨架
     *
     * <p>按固定顺序调用各步骤方法：eat -> play -> call</p>
     * <p>此方法定义了动物行为的完整流程，子类不应该重写此方法。</p>
     *
     * <p>注意：实际项目中应声明为final防止子类修改：</p>
     * <pre>public final void action() { ... }</pre>
     */
    public void action() {
        this.eat();   // 第一步：吃
        this.play();  // 第二步：玩
        this.call();  // 第三步：叫（由子类实现）
    }
}
