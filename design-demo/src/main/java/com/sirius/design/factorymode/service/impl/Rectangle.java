package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Shape;

/**
 * 矩形实现类
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println(this);
    }
}
