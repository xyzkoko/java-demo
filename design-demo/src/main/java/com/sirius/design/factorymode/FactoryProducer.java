package com.sirius.design.factorymode;

/**
 * 工厂生产者
 * 根据类型返回对应的具体工厂实例
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        switch (choice) {
            case "color":
                return new ColorFactory();
            case "shape":
                return new ShapeFactory();
            default:
                return null;
        }
    }
}
