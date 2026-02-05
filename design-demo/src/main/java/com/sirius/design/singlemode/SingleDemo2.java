package com.sirius.design.singlemode;

/**
 * 单例模式 - 枚举方式（最安全）
 *
 * <p>使用枚举实现单例是《Effective Java》作者Josh Bloch推荐的方式，
 * 也是实现单例的最佳实践。</p>
 *
 * <h3>枚举单例的优势：</h3>
 * <ul>
 *   <li><b>线程安全</b>：JVM保证枚举实例的创建是线程安全的</li>
 *   <li><b>防止反射攻击</b>：枚举类不能通过反射创建实例</li>
 *   <li><b>防止序列化破坏</b>：枚举的序列化由JVM保证，反序列化后仍是同一实例</li>
 *   <li><b>代码简洁</b>：无需考虑双重检查、volatile等复杂问题</li>
 * </ul>
 *
 * <h3>与其他方式对比：</h3>
 * <ul>
 *   <li>静态内部类（{@link SingleDemo1}）：可被反射和序列化破坏</li>
 *   <li>双重检查锁：代码复杂，需要volatile关键字</li>
 *   <li>枚举方式：最简单、最安全、最推荐</li>
 * </ul>
 *
 * <h3>使用方式：</h3>
 * <pre>
 * SingleDemo2.INSTANCE.doSomething();
 * </pre>
 *
 * @see SingleDemo1 静态内部类方式单例
 */
public enum SingleDemo2 {

    /**
     * 单例实例
     * 枚举常量在类加载时由JVM创建，保证全局唯一
     */
    INSTANCE;

    /**
     * 单例对象的业务方法
     *
     * <p>枚举单例可以像普通类一样定义方法和属性。</p>
     */
    public void doSomething() {
        System.out.println("doSomething");
    }
}

/**
 * 枚举单例测试类
 */
class App2 {
    public static void main(String[] args) {
        // 通过INSTANCE访问单例实例并调用方法
        SingleDemo2.INSTANCE.doSomething();
    }
}

