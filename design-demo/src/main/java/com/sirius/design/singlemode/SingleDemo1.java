package com.sirius.design.singlemode;

/**
 * 单例模式 - 静态内部类方式（推荐）
 *
 * <p>单例模式确保一个类只有一个实例，并提供全局访问点。
 * 静态内部类方式是实现单例的最佳实践之一。</p>
 *
 * <h3>单例模式的实现方式：</h3>
 * <table border="1">
 *   <tr><th>方式</th><th>线程安全</th><th>懒加载</th><th>防反射</th><th>防序列化</th></tr>
 *   <tr><td>饿汉式</td><td>是</td><td>否</td><td>否</td><td>否</td></tr>
 *   <tr><td>懒汉式（同步方法）</td><td>是</td><td>是</td><td>否</td><td>否</td></tr>
 *   <tr><td>双重检查锁（DCL）</td><td>是</td><td>是</td><td>否</td><td>否</td></tr>
 *   <tr><td><b>静态内部类（本例）</b></td><td>是</td><td>是</td><td>否</td><td>否</td></tr>
 *   <tr><td>枚举（{@link SingleDemo2}）</td><td>是</td><td>否</td><td>是</td><td>是</td></tr>
 * </table>
 *
 * <h3>静态内部类实现原理：</h3>
 * <ul>
 *   <li>外部类加载时，内部类不会被加载（懒加载）</li>
 *   <li>调用getInstance()时才加载内部类并创建实例</li>
 *   <li>JVM保证类加载的线程安全性（无需同步）</li>
 *   <li>既保证了懒加载，又保证了线程安全</li>
 * </ul>
 *
 * <h3>适用场景：</h3>
 * <ul>
 *   <li>配置管理器</li>
 *   <li>日志记录器</li>
 *   <li>数据库连接池</li>
 *   <li>线程池</li>
 * </ul>
 *
 * @see SingleDemo2 枚举方式单例
 */
public class SingleDemo1 {

    /**
     * 私有构造方法，防止外部实例化
     */
    private SingleDemo1() {
    }

    /**
     * 静态内部类持有单例实例
     *
     * <p>内部类在首次使用时才会被加载，此时创建单例实例。
     * 由JVM保证类加载的线程安全性。</p>
     */
    private static class SingleDemo1Hold {
        private static SingleDemo1 singleDemo1 = new SingleDemo1();
    }

    /**
     * 获取单例实例
     *
     * <p>首次调用时会触发内部类加载，创建单例实例。
     * 后续调用直接返回已创建的实例。</p>
     *
     * @return 单例实例
     */
    public static SingleDemo1 getInstance() {
        return SingleDemo1Hold.singleDemo1;
    }

}

/**
 * 静态内部类单例测试类
 */
class App1 {
    public static void main(String[] args) {
        SingleDemo1 singleDemo1 = SingleDemo1.getInstance();
        System.out.println(singleDemo1);
    }
}
