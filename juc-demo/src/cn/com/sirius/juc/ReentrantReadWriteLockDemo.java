package cn.com.sirius.juc;

import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * ReentrantReadWriteLock（读写锁）演示类
 *
 * <p>读写锁是一种特殊的锁机制，它允许多个读操作同时进行，但写操作是互斥的。
 * 适用于读多写少的场景，可以显著提高并发性能。</p>
 *
 * <h3>锁的兼容性矩阵：</h3>
 * <table border="1">
 *   <tr><th></th><th>读锁</th><th>写锁</th></tr>
 *   <tr><td>读锁</td><td>兼容（共享）</td><td>互斥</td></tr>
 *   <tr><td>写锁</td><td>互斥</td><td>互斥</td></tr>
 * </table>
 *
 * <h3>核心规则：</h3>
 * <ul>
 *   <li>读锁（共享锁）：多个线程可以同时持有读锁</li>
 *   <li>写锁（排他锁）：同一时间只能有一个线程持有写锁</li>
 *   <li>读写互斥：有读锁时不能获取写锁，有写锁时不能获取读锁</li>
 *   <li>锁降级：持有写锁时可以获取读锁（然后释放写锁），但不能锁升级</li>
 * </ul>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>缓存系统：读操作频繁，写操作较少</li>
 *   <li>配置中心：配置读取频繁，修改较少</li>
 *   <li>数据字典：数据相对稳定，查询频繁</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>通过4个线程演示读写锁的互斥关系：</p>
 * <ol>
 *   <li>线程1、2同时获取读锁（读读共享）</li>
 *   <li>线程3等待写锁（读写互斥）</li>
 *   <li>线程4等待读锁（写读互斥）</li>
 * </ol>
 *
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 * @see java.util.concurrent.locks.StampedLock 更高性能的读写锁（JDK8+）
 */
public class ReentrantReadWriteLockDemo {

    /**
     * 共享数据：使用TreeMap存储键值对
     */
    private static Map<String, String> map = new TreeMap<>();

    /**
     * 读写锁实例
     * 可通过构造参数设置公平性：new ReentrantReadWriteLock(true)
     */
    private static ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    /**
     * 读锁：多个线程可以同时持有
     */
    private static Lock readLock = reentrantReadWriteLock.readLock();

    /**
     * 写锁：同一时间只能有一个线程持有
     */
    private static Lock writeLock = reentrantReadWriteLock.writeLock();

    /**
     * 读取操作 - 获取所有键
     *
     * <p>使用读锁保护读取操作，多个线程可以同时读取。
     * 在持有读锁期间，写操作会被阻塞。</p>
     */
    public static void getKeys() {
        // 获取读锁
        readLock.lock();
        try {
            Set<String> keySet1 = map.keySet();
            System.out.println(Thread.currentThread().getName() + ": " + keySet1);
            // 模拟读操作耗时
            Thread.sleep(5 * 1000);
            // 再次读取，验证读锁期间数据一致性
            Set<String> keySet2 = map.keySet();
            System.out.println(Thread.currentThread().getName() + ": " + keySet2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            readLock.unlock();
        }
    }

    /**
     * 写入操作 - 添加键值对
     *
     * <p>使用写锁保护写入操作。获取写锁时，必须等待所有读锁和写锁释放。
     * 持有写锁期间，其他读写操作都会被阻塞。</p>
     *
     * @param key   键
     * @param value 值
     */
    public static void put(String key, String value) {
        // 获取写锁（独占锁）
        writeLock.lock();
        try {
            map.put(key, value);
            // 模拟写操作耗时
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放写锁
            writeLock.unlock();
        }
    }

    /**
     * 主方法 - 演示读写锁的互斥关系
     *
     * <p>执行顺序和预期行为：</p>
     * <ol>
     *   <li>T=0s：线程1获取读锁，开始读取</li>
     *   <li>T=1s：线程2获取读锁成功（读读共享），开始读取</li>
     *   <li>T=2s：线程3请求写锁，被阻塞（读写互斥）</li>
     *   <li>T=3s：线程4请求读锁，被阻塞（有写锁等待，防止写饥饿）</li>
     *   <li>T=5s：线程1释放读锁</li>
     *   <li>T=6s：线程2释放读锁，线程3获取写锁</li>
     *   <li>T=11s：线程3释放写锁，线程4获取读锁</li>
     * </ol>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果线程被中断
     */
    public static void main(String[] args) throws InterruptedException {
        // 线程1：获取读锁
        new Thread(ReentrantReadWriteLockDemo::getKeys, "1").start();
        Thread.sleep(1000);

        // 线程2：也获取读锁（读锁可以多线程共享）
        new Thread(ReentrantReadWriteLockDemo::getKeys, "2").start();
        Thread.sleep(1000);

        // 线程3：请求写锁（读锁没释放前无法获取写锁，会阻塞等待）
        new Thread(() -> put("1", "1")).start();
        Thread.sleep(1000);

        // 线程4：请求读锁（写锁等待中，为防止写饥饿，新的读请求也会等待）
        new Thread(ReentrantReadWriteLockDemo::getKeys, "3").start();
    }

}
