package com.sirius.design.dacadedemo;

/**
 * 外观模式 - 子系统组件2（SubSystem）
 *
 * <p>子系统组件是实际完成业务功能的类。
 * 它们各自独立，不知道外观类的存在。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>实现特定的业务功能</li>
 *   <li>可被外观类调用，也可被客户端直接调用</li>
 * </ul>
 *
 * @see Cats 外观类
 */
public class Cat02 {

    /**
     * 猫2的叫声
     * 由外观类Cats协调调用
     */
    public void call() {
        System.out.println("喵喵");
    }
}
