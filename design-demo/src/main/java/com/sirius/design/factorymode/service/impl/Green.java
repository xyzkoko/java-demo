package com.sirius.design.factorymode.service.impl;

import com.sirius.design.factorymode.service.Color;

/**
 * 绿色实现类
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println(this);
    }
}
