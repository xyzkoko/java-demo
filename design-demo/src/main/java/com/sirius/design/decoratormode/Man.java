package com.sirius.design.decoratormode;

/**
 * 装饰器模式 - 具体组件（ConcreteComponent）
 *
 * <p>实现组件接口的原始对象，定义了基本的行为。
 * 这是被装饰的对象，装饰器会在此基础上添加新功能。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>实现组件接口定义的核心方法</li>
 *   <li>提供基础功能，不关心装饰器的存在</li>
 * </ul>
 *
 * <h3>与装饰器的关系：</h3>
 * <p>具体组件是装饰的起点，装饰器通过持有组件引用，
 * 在调用原始方法的基础上添加额外行为。</p>
 *
 * @see Human 组件接口
 * @see DecoratorMan 装饰器
 */
public class Man implements Human {

    /**
     * 展示原始名称信息
     */
    @Override
    public void name() {
        System.out.println("我是人类");
    }

    /**
     * 展示原始年龄信息
     */
    @Override
    public void age() {
        System.out.println("我28岁");
    }
}
