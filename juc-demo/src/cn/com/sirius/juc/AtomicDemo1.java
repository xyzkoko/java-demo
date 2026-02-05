package cn.com.sirius.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

import static java.lang.Thread.sleep;

/**
 * Atomic原子类演示 - 基本类型原子操作
 *
 * <p>java.util.concurrent.atomic包提供了一系列原子类，用于在多线程环境下进行
 * 无锁的线程安全操作。底层基于CAS（Compare-And-Swap）机制实现。</p>
 *
 * <h3>CAS原理：</h3>
 * <p>CAS包含三个操作数：内存位置V、预期原值A、新值B。
 * 只有当V的值等于A时，才会将V的值更新为B，否则不做任何操作。
 * 这是一个原子操作，由CPU指令保证。</p>
 *
 * <h3>常用原子类分类：</h3>
 * <ul>
 *   <li><b>基本类型</b>：AtomicInteger、AtomicLong、AtomicBoolean</li>
 *   <li><b>数组类型</b>：AtomicIntegerArray、AtomicLongArray、AtomicReferenceArray</li>
 *   <li><b>引用类型</b>：AtomicReference、AtomicStampedReference（解决ABA问题）</li>
 *   <li><b>字段更新器</b>：AtomicIntegerFieldUpdater、AtomicLongFieldUpdater</li>
 *   <li><b>累加器（JDK8+）</b>：LongAdder、LongAccumulator（高并发性能更好）</li>
 * </ul>
 *
 * <h3>本示例对比四种方式的线程安全性：</h3>
 * <table border="1">
 *   <tr><th>类型</th><th>线程安全</th><th>说明</th></tr>
 *   <tr><td>Long</td><td>否</td><td>++操作非原子，会丢失更新</td></tr>
 *   <tr><td>volatile Long</td><td>否</td><td>volatile只保证可见性，++仍非原子</td></tr>
 *   <tr><td>AtomicLong</td><td>是</td><td>CAS保证原子性</td></tr>
 *   <tr><td>LongAdder</td><td>是</td><td>分段累加，高并发性能更好</td></tr>
 * </table>
 *
 * @see java.util.concurrent.atomic.AtomicLong
 * @see java.util.concurrent.atomic.LongAdder
 */
public class AtomicDemo1 {

    /**
     * 普通Long - 线程不安全
     * ++操作包含读取、加1、写入三步，不是原子操作
     */
    private static Long aLong = 0L;

    /**
     * AtomicLong - 线程安全
     * 使用CAS实现原子的incrementAndGet操作
     */
    private static AtomicLong atomicLong = new AtomicLong(0);

    /**
     * LongAdder - 线程安全（JDK8+）
     * 采用分段累加策略，在高并发下性能优于AtomicLong
     * 适合统计计数场景，不适合需要精确实时值的场景
     */
    private static LongAdder longAdder = new LongAdder();

    /**
     * volatile Long - 线程不安全
     * volatile只保证可见性，不保证原子性
     * volatileLong++仍然不是原子操作
     */
    private static volatile Long volatileLong = 0L;

    /**
     * CountDownLatch：等待5000个任务全部完成
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(5000);

    /**
     * Semaphore：限制同时执行的任务数为200
     */
    private static Semaphore semaphore = new Semaphore(200);

    /**
     * 主方法 - 对比不同类型在并发下的表现
     *
     * <p>启动5000个任务，每个任务对四个变量各加1，
     * 预期结果应该都是5000，但线程不安全的变量结果会小于5000。</p>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待被中断
     */
    public static void main(String[] args) throws InterruptedException {
        final ExecutorService executorService = Executors.newCachedThreadPool();

        // 提交5000个任务
        for (int i = 0; i < 5000; i++) {
            executorService.execute(() -> {
                try {
                    // 通过信号量限制并发数
                    semaphore.acquire();

                    // 四种方式各执行一次自增操作
                    aLong++;                      // 线程不安全：可能丢失更新
                    volatileLong++;               // 线程不安全：volatile不保证原子性
                    atomicLong.incrementAndGet(); // 线程安全：CAS原子操作
                    longAdder.increment();        // 线程安全：分段累加

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();
                    countDownLatch.countDown();
                }
            });
        }

        // 等待所有任务完成
        countDownLatch.await();
        executorService.shutdown();

        // 输出结果对比
        // AtomicLong和LongAdder应该是5000
        // Long和volatileLong通常小于5000（丢失了部分更新）
        System.out.println("AtomicLong:" + atomicLong.get());     // 5000（线程安全）
        System.out.println("Long:" + aLong);                       // <5000（线程不安全）
        System.out.println("LongAdder:" + longAdder);              // 5000（线程安全）
        System.out.println("volatileLong:" + volatileLong);        // <5000（线程不安全）
    }
}
