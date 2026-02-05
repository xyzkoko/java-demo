package cn.com.sirius.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock（可重入锁）演示类
 *
 * <p>ReentrantLock是JUC包中的显式锁实现，相比synchronized提供了更灵活的锁控制。
 * "可重入"意味着同一个线程可以多次获取同一把锁而不会死锁。</p>
 *
 * <h3>ReentrantLock vs synchronized：</h3>
 * <table border="1">
 *   <tr><th>特性</th><th>ReentrantLock</th><th>synchronized</th></tr>
 *   <tr><td>锁获取方式</td><td>显式lock()/unlock()</td><td>隐式获取释放</td></tr>
 *   <tr><td>公平性</td><td>可选公平/非公平锁</td><td>只有非公平锁</td></tr>
 *   <tr><td>条件变量</td><td>支持多个Condition</td><td>只有一个等待队列</td></tr>
 *   <tr><td>可中断</td><td>lockInterruptibly()</td><td>不支持</td></tr>
 *   <tr><td>超时获取</td><td>tryLock(timeout)</td><td>不支持</td></tr>
 * </table>
 *
 * <h3>核心特性：</h3>
 * <ul>
 *   <li>可指定公平锁（new ReentrantLock(true)）或非公平锁（默认）</li>
 *   <li>提供Condition类，可以精确控制线程的等待和唤醒</li>
 *   <li>提供lockInterruptibly()，等待锁时可响应中断</li>
 *   <li>提供tryLock()，尝试获取锁，可设置超时时间</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <ol>
 *   <li>基本的lock/unlock用法：1000个线程安全地累加计数器</li>
 *   <li>Condition的使用：线程间的等待/通知机制</li>
 * </ol>
 *
 * @see java.util.concurrent.locks.ReentrantLock
 * @see java.util.concurrent.locks.Condition
 */
public class ReentrantLockDemo {

    /**
     * 可重入锁实例
     * 默认为非公平锁，可通过new ReentrantLock(true)创建公平锁
     */
    private static ReentrantLock reentrantLock = new ReentrantLock();

    /**
     * 用于等待所有任务完成的倒计时门闩
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1000);

    /**
     * 条件变量：用于线程间的等待/通知
     * 一个Lock可以创建多个Condition，实现更精细的线程协调
     */
    private static Condition condition = reentrantLock.newCondition();

    /**
     * 共享计数器：演示锁保护下的线程安全操作
     */
    private static int count;

    /**
     * 主方法 - 演示ReentrantLock的两种典型用法
     *
     * <p>Part 1: 基本锁用法</p>
     * <p>1000个线程并发累加count，使用ReentrantLock保证线程安全</p>
     *
     * <p>Part 2: Condition条件变量</p>
     * <p>演示线程间的等待/通知机制，输出顺序为：1 -> 2 -> 3 -> 4</p>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待被中断
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // ========== Part 1: 基本锁用法 ==========
        // 启动1000个线程，每个线程对count加1
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // 获取锁（阻塞直到获取成功）
                    reentrantLock.lock();
                    try {
                        // 临界区：只有持有锁的线程才能执行
                        count++;
                    } finally {
                        // 必须在finally中释放锁，确保异常时也能释放
                        reentrantLock.unlock();
                        countDownLatch.countDown();
                    }
                }
            });
        }
        // 等待所有任务完成
        countDownLatch.await();
        System.out.println(count);  // 输出1000，证明线程安全

        // ========== Part 2: Condition条件变量 ==========
        // 线程A：等待条件满足
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println(1);  // 第一个输出
                // await()会释放锁并进入等待状态
                // 被唤醒后会重新获取锁
                condition.await();
                System.out.println(4);  // 最后一个输出（等线程B释放锁后）
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();
            }
        }).start();

        // 线程B：发送通知唤醒等待的线程
        new Thread(() -> {
            reentrantLock.lock();
            try {
                System.out.println(2);  // 第二个输出
                // signalAll()唤醒所有在此Condition上等待的线程
                // 注意：被唤醒的线程需要等当前线程释放锁后才能继续执行
                condition.signalAll();
                Thread.sleep(5 * 1000);  // 持有锁5秒
                System.out.println(3);  // 第三个输出
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                reentrantLock.unlock();  // 释放锁后，线程A才能继续
            }
        }).start();
    }

}
