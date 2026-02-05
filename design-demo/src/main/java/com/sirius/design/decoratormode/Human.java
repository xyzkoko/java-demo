package com.sirius.design.decoratormode;

/**
 * 装饰器模式 - 组件接口（Component）
 *
 * <p>定义了被装饰对象和装饰器的公共接口。
 * 具体组件和装饰器都必须实现此接口，保证透明性。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>定义对象的核心行为方法</li>
 *   <li>作为具体组件和装饰器的共同类型</li>
 *   <li>使客户端可以一致地处理装饰前后的对象</li>
 * </ul>
 *
 * <h3>设计要点：</h3>
 * <p>组件接口应该尽量轻量，只定义核心方法。
 * 装饰器通过实现相同接口来保证与原对象的兼容性。</p>
 *
 * @see Man 具体组件实现
 * @see DecoratorMan 装饰器实现
 */
public interface Human {

    /**
     * 展示名称信息
     */
    void name();

    /**
     * 展示年龄信息
     */
    void age();
}
