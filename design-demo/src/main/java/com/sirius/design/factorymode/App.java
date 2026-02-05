package com.sirius.design.factorymode;

import com.sirius.design.factorymode.service.Color;
import com.sirius.design.factorymode.service.Shape;

/**
 * 抽象工厂模式（Abstract Factory Pattern）演示入口
 *
 * <p>抽象工厂模式提供一个创建一系列相关或相互依赖对象的接口，
 * 而无需指定它们具体的类。它是工厂方法模式的升级版。</p>
 *
 * <h3>模式结构：</h3>
 * <pre>
 * FactoryProducer（工厂生产者）
 *       |
 *       v
 * AbstractFactory（抽象工厂）
 *    /        \
 *   v          v
 * ShapeFactory  ColorFactory（具体工厂）
 *   |            |
 *   v            v
 * Shape产品族   Color产品族
 * </pre>
 *
 * <h3>核心角色：</h3>
 * <ul>
 *   <li><b>AbstractFactory</b>：抽象工厂，声明创建产品的方法</li>
 *   <li><b>ShapeFactory/ColorFactory</b>：具体工厂，实现创建具体产品</li>
 *   <li><b>Shape/Color</b>：抽象产品，定义产品接口</li>
 *   <li><b>Circle/Red等</b>：具体产品，实现产品接口</li>
 *   <li><b>FactoryProducer</b>：工厂生产者，根据类型返回对应工厂</li>
 * </ul>
 *
 * <h3>与工厂方法的区别：</h3>
 * <ul>
 *   <li>工厂方法：一个工厂创建一种产品</li>
 *   <li>抽象工厂：一个工厂创建一个产品族（多种相关产品）</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>系统需要独立于产品的创建、组合和表示</li>
 *   <li>系统需要由多个产品系列中的一个来配置</li>
 *   <li>需要强调一系列相关产品对象的设计以便联合使用</li>
 * </ul>
 *
 * @see AbstractFactory 抽象工厂
 * @see ShapeFactory 形状工厂
 * @see ColorFactory 颜色工厂
 */
public class App {
    public static void main(String[] args) {
        // 获取形状工厂
        AbstractFactory shapeFactory = FactoryProducer.getFactory("shape");
        // 通过形状工厂创建圆形产品
        Shape shape = shapeFactory.getShape("circle");
        shape.draw();  // 输出: 画圆形

        // 获取颜色工厂
        AbstractFactory colorFactory = FactoryProducer.getFactory("color");
        // 通过颜色工厂创建红色产品
        Color color = colorFactory.getColor("red");
        color.fill();  // 输出: 填充红色
    }
}
