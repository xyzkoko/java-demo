package cn.com.sirius.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Atomic原子类演示 - AtomicBoolean实现一次性操作
 *
 * <p>AtomicBoolean是原子布尔类型，常用于多线程环境下的状态标记，
 * 特别适合实现"只执行一次"的场景。</p>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li><b>初始化控制</b>：确保某个初始化代码只执行一次</li>
 *   <li><b>一次性资源获取</b>：如获取分布式锁、抢占任务等</li>
 *   <li><b>状态标记</b>：标记某个操作是否已完成</li>
 *   <li><b>开关控制</b>：控制某个功能的开启/关闭</li>
 * </ul>
 *
 * <h3>compareAndSet的原子性保证：</h3>
 * <p>compareAndSet(expect, update)是原子操作，在多线程竞争时，
 * 只有一个线程能成功将值从expect更新为update，其他线程都会失败。
 * 这个特性保证了"只执行一次"的语义。</p>
 *
 * <h3>本示例演示：</h3>
 * <p>200个线程同时竞争执行某段代码，但由于使用了AtomicBoolean的CAS操作，
 * 只有第一个成功将false改为true的线程能执行打印操作，其他线程都会跳过。</p>
 *
 * @see java.util.concurrent.atomic.AtomicBoolean
 */
public class AtomicDemo3 {

    /**
     * 原子布尔标记：用于控制代码只执行一次
     * 初始值为false，第一个成功CAS的线程将其改为true
     */
    private static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    /**
     * 主方法 - 演示AtomicBoolean实现"只执行一次"
     *
     * <p>启动200个线程同时竞争，但只有一个线程能成功执行打印操作。</p>
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        // 提交200个任务，模拟高并发场景
        for (int i = 0; i < 200; i++) {
            executorService.submit(() -> {
                // CAS操作：尝试将false改为true
                // 只有一个线程能成功（返回true），其他线程都返回false
                if (atomicBoolean.compareAndSet(false, true)) {
                    // 只有第一个成功的线程能执行这里
                    // 典型用途：一次性初始化、抢占式任务执行等
                    System.out.println("do some thing");
                }
                // 其他线程会直接跳过if块，不执行任何操作
            });
        }

        executorService.shutdown();
    }
}
