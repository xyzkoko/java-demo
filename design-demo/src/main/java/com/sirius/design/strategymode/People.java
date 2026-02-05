package com.sirius.design.strategymode;

/**
 * 策略模式 - 上下文类（Context）
 *
 * <p>上下文类是策略模式的核心，它持有策略接口的引用，
 * 通过组合而非继承的方式实现行为的动态切换。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>维护对策略对象的引用</li>
 *   <li>提供设置策略的方法（支持运行时切换）</li>
 *   <li>委托策略对象执行具体算法</li>
 * </ul>
 *
 * <h3>设计特点：</h3>
 * <ul>
 *   <li>使用组合替代继承，更加灵活</li>
 *   <li>setter方法返回this，支持链式调用</li>
 *   <li>可以同时持有多个策略（如本例的学习和游戏策略）</li>
 * </ul>
 *
 * @see IStudy 学习策略接口
 * @see IPlay 游戏策略接口
 */
public class People {

    /**
     * 学习策略引用
     */
    private IStudy iStudy;

    /**
     * 游戏策略引用
     */
    private IPlay iPlay;

    /**
     * 人员名称
     */
    private String name;

    /**
     * 构造方法
     *
     * @param name 人员名称
     */
    public People(String name) {
        this.name = name;
    }

    /**
     * 设置学习策略
     *
     * @param iStudy 学习策略实现
     * @return this，支持链式调用
     */
    public People setStudy(IStudy iStudy) {
        this.iStudy = iStudy;
        return this;
    }

    /**
     * 设置游戏策略
     *
     * @param iPlay 游戏策略实现
     * @return this，支持链式调用
     */
    public People setPlay(IPlay iPlay) {
        this.iPlay = iPlay;
        return this;
    }

    /**
     * 执行学习行为
     * 委托给学习策略对象执行
     */
    public void study() {
        iStudy.action(this.name);
    }

    /**
     * 执行游戏行为
     * 委托给游戏策略对象执行
     */
    public void play() {
        iPlay.action(this.name);
    }
}
