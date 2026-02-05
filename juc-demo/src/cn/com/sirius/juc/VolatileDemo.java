package cn.com.sirius.juc;

/**
 * volatile关键字演示类
 *
 * <p>volatile是Java提供的轻量级同步机制，用于保证变量的可见性和禁止指令重排序。
 * 它不能保证原子性，因此不能替代锁。</p>
 *
 * <h3>volatile的两大特性：</h3>
 * <ol>
 *   <li><b>可见性</b>：对volatile变量的写操作会立即刷新到主内存，
 *       读操作会从主内存读取最新值，保证所有线程看到一致的值</li>
 *   <li><b>禁止指令重排序</b>：通过内存屏障防止编译器和CPU对volatile变量
 *       相关的指令进行重排序优化</li>
 * </ol>
 *
 * <h3>volatile不保证原子性：</h3>
 * <p>volatile只能保证单次读/写的原子性，复合操作（如i++）不是原子的。
 * 需要原子性时应使用synchronized或Atomic类。</p>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li><b>状态标志</b>：如本例中的inited标志，用于线程间状态通知</li>
 *   <li><b>双重检查锁定（DCL）单例模式</b>：防止instance引用的指令重排序</li>
 *   <li><b>独立观察</b>：定期发布观察结果供其他线程使用</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>使用volatile修饰的inited标志实现线程间的状态通知：</p>
 * <ul>
 *   <li>线程A在5秒后将inited设置为true</li>
 *   <li>线程B轮询等待inited变为true后继续执行</li>
 *   <li>如果不使用volatile，线程B可能永远看不到inited的变化（CPU缓存问题）</li>
 * </ul>
 *
 * @see java.util.concurrent.atomic.AtomicBoolean 需要原子操作时的替代方案
 */
public class VolatileDemo {

    /**
     * volatile修饰的状态标志
     * 保证多线程环境下的可见性
     */
    volatile boolean inited = false;

    /**
     * 主方法 - 演示volatile的可见性
     *
     * <p>启动两个线程：</p>
     * <ul>
     *   <li>设置线程：5秒后将inited设置为true</li>
     *   <li>等待线程：轮询检查inited，变为true后打印完成信息</li>
     * </ul>
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        final VolatileDemo volatileDemo = new VolatileDemo();

        // 设置线程：负责修改inited状态
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 模拟初始化操作耗时5秒
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 设置状态为已初始化
                // 由于volatile的可见性，其他线程能立即看到这个变化
                volatileDemo.inited = true;
            }
        }).start();

        // 等待线程：轮询检查inited状态
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 轮询等待inited变为true
                // 如果inited不是volatile，可能因为CPU缓存导致永远读取到false
                while (!volatileDemo.inited) {
                    System.out.println("等待一次");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // inited为true，退出循环
                System.out.println("完成了");
            }
        }).start();
    }
}
