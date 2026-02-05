package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Color;

/**
 * 红色实现类
 */
public class Red implements Color {
    @Override
    public void fill() {
        System.out.println(this);
    }
}
