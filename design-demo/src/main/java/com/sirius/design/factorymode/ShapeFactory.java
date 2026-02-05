package com.sirius.design.factorymode;

import com.sirius.design.factorymode.service.Color;
import com.sirius.design.factorymode.service.Shape;
import com.sirius.design.factorymode.service.impl.Circle;
import com.sirius.design.factorymode.service.impl.Rectangle;
import com.sirius.design.factorymode.service.impl.Square;

/**
 * 形状工厂
 * 负责创建形状产品族（Circle、Rectangle、Square）
 */
public class ShapeFactory extends AbstractFactory {
    @Override
    Color getColor(String color) {
        return null;
    }

    @Override
    Shape getShape(String shape) {
        switch (shape) {
            case "circle":
                return new Circle();
            case "rectangle":
                return new Rectangle();
            case "square":
                return new Square();
            default:
                return null;
        }
    }
}
