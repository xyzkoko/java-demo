package com.sirius.design.factorymode;

import com.sirius.design.factorymode.service.Color;
import com.sirius.design.factorymode.service.Shape;
import com.sirius.design.factorymode.service.impl.Blue;
import com.sirius.design.factorymode.service.impl.Green;
import com.sirius.design.factorymode.service.impl.Red;

/**
 * 颜色工厂
 * 负责创建颜色产品族（Red、Green、Blue）
 */
public class ColorFactory extends AbstractFactory {
    @Override
    Color getColor(String color) {
        switch (color) {
            case "blue":
                return new Blue();
            case "red":
                return new Red();
            case "green":
                return new Green();
            default:
                return null;
        }
    }

    @Override
    Shape getShape(String shape) {
        return null;
    }
}
