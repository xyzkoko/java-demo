package com.sirius.design.factorymode;

import com.sirius.design.factorymode.service.Color;
import com.sirius.design.factorymode.service.Shape;

/**
 * 抽象工厂（Abstract Factory）
 *
 * <p>定义创建产品族的抽象方法，由具体工厂实现。
 * 每个具体工厂负责创建一个产品族中的所有产品。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>声明创建各种抽象产品的方法</li>
 *   <li>由子类实现具体的产品创建逻辑</li>
 * </ul>
 *
 * <h3>实现类：</h3>
 * <ul>
 *   <li>{@link ShapeFactory}：创建Shape产品族</li>
 *   <li>{@link ColorFactory}：创建Color产品族</li>
 * </ul>
 *
 * @see ShapeFactory
 * @see ColorFactory
 */
public abstract class AbstractFactory {

    /**
     * 获取颜色产品
     *
     * @param color 颜色类型（如"red", "green", "blue"）
     * @return Color实例，如果类型不支持则返回null
     */
    abstract Color getColor(String color);

    /**
     * 获取形状产品
     *
     * @param shape 形状类型（如"circle", "rectangle", "square"）
     * @return Shape实例，如果类型不支持则返回null
     */
    abstract Shape getShape(String shape);
}
