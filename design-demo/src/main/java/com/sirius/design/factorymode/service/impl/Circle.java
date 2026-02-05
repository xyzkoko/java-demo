package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Shape;

/**
 * 圆形实现类
 */
public class Circle implements Shape {
    @Override
    public void draw() {
        System.out.println(this);
    }
}
