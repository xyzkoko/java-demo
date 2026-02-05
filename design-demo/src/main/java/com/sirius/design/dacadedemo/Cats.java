package com.sirius.design.dacadedemo;

/**
 * 外观模式 - 外观类（Facade）
 *
 * <p>外观类是外观模式的核心，它封装了子系统的复杂性，
 * 对外提供一个简单统一的接口。</p>
 *
 * <h3>核心职责：</h3>
 * <ul>
 *   <li>持有子系统组件的引用</li>
 *   <li>将客户端请求委派给适当的子系统对象</li>
 *   <li>协调子系统组件之间的协作</li>
 *   <li>对外屏蔽子系统的复杂性</li>
 * </ul>
 *
 * <h3>设计特点：</h3>
 * <ul>
 *   <li>外观类不参与子系统的具体业务逻辑</li>
 *   <li>只是作为子系统的"代理"或"门面"</li>
 *   <li>客户端可以直接访问子系统（如果需要）</li>
 * </ul>
 *
 * <h3>实际应用示例：</h3>
 * <ul>
 *   <li>电脑开机：一键启动CPU、内存、硬盘等组件</li>
 *   <li>Spring的JdbcTemplate：封装了JDBC的复杂操作</li>
 *   <li>SLF4J：统一日志外观，屏蔽底层日志框架差异</li>
 * </ul>
 *
 * @see Cat01 子系统组件1
 * @see Cat02 子系统组件2
 * @see Cat03 子系统组件3
 */
public class Cats {

    /**
     * 子系统组件1
     */
    private Cat01 cat01;

    /**
     * 子系统组件2
     */
    private Cat02 cat02;

    /**
     * 子系统组件3
     */
    private Cat03 cat03;

    /**
     * 默认构造方法
     * 内部创建所有子系统组件实例
     */
    public Cats() {
        this.cat01 = new Cat01();
        this.cat02 = new Cat02();
        this.cat03 = new Cat03();
    }

    /**
     * 带参构造方法
     * 支持依赖注入，便于测试
     *
     * @param cat01 子系统组件1
     * @param cat02 子系统组件2
     * @param cat03 子系统组件3
     */
    public Cats(Cat01 cat01, Cat02 cat02, Cat03 cat03) {
        this.cat01 = cat01;
        this.cat02 = cat02;
        this.cat03 = cat03;
    }

    /**
     * 统一的外观方法
     * 协调多个子系统组件完成复杂操作
     */
    public void call() {
        // 按顺序调用各子系统组件
        this.cat01.call();
        this.cat02.call();
        this.cat03.call();
    }
}
