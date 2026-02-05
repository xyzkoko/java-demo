package com.sirius.design.templatemode;

/**
 * 模板方法模式 - 具体实现类（ConcreteClass）
 *
 * <p>继承抽象模板类，实现其中的抽象方法。
 * 子类只关注需要定制的步骤，其他步骤使用父类的默认实现。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>实现父类中声明的抽象方法</li>
 *   <li>可选择性地重写具体方法（如eat、play）</li>
 *   <li>不应该重写模板方法（action）</li>
 * </ul>
 *
 * <h3>扩展其他动物：</h3>
 * <p>如果需要添加其他动物（如Dog），只需：</p>
 * <ol>
 *   <li>创建新类继承Animal</li>
 *   <li>实现call()方法返回该动物的叫声</li>
 * </ol>
 *
 * @see Animal 抽象模板类
 */
public class Cat extends Animal {

    /**
     * 实现抽象方法 - 猫的叫声
     * 这是算法中猫特有的行为
     */
    @Override
    public void call() {
        System.out.println("喵喵叫");
    }
}
