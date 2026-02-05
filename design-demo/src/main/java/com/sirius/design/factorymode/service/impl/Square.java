package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Shape;

/**
 * 正方形实现类
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println(this);
    }
}
