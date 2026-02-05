package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Color;

/**
 * 蓝色实现类
 */
public class Blue implements Color {
    @Override
    public void fill() {
        System.out.println(this);
    }
}
