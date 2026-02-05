package cn.com.sirius.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock（邮戳锁）演示类
 *
 * <p>StampedLock是JDK8引入的新型读写锁，相比ReentrantReadWriteLock性能更好，
 * 特别是在读多写少的场景下。它引入了乐观读的概念，进一步提升并发性能。</p>
 *
 * <h3>三种锁模式：</h3>
 * <ul>
 *   <li><b>写锁（writeLock）</b>：独占锁，与ReentrantReadWriteLock的写锁类似</li>
 *   <li><b>悲观读锁（readLock）</b>：共享锁，与ReentrantReadWriteLock的读锁类似</li>
 *   <li><b>乐观读锁（tryOptimisticRead）</b>：无锁操作，读取后需要验证数据是否被修改</li>
 * </ul>
 *
 * <h3>与ReentrantReadWriteLock的区别：</h3>
 * <ul>
 *   <li>StampedLock支持乐观读，性能更好</li>
 *   <li>StampedLock不支持重入</li>
 *   <li>StampedLock不支持Condition条件变量</li>
 *   <li>StampedLock写锁不会阻塞乐观读</li>
 * </ul>
 *
 * <h3>乐观读使用模式：</h3>
 * <pre>{@code
 * long stamp = lock.tryOptimisticRead();  // 获取乐观读戳记
 * // 读取数据...
 * if (!lock.validate(stamp)) {            // 验证期间是否有写操作
 *     stamp = lock.readLock();            // 验证失败，升级为悲观读锁
 *     try {
 *         // 重新读取数据...
 *     } finally {
 *         lock.unlockRead(stamp);
 *     }
 * }
 * }</pre>
 *
 * <h3>本示例演示：</h3>
 * <p>使用StampedLock的写锁保护计数器，1000个线程安全累加。</p>
 *
 * @see java.util.concurrent.locks.StampedLock
 * @see java.util.concurrent.locks.ReentrantReadWriteLock
 */
public class StampedLockDemo {

    /**
     * StampedLock实例
     * 注意：StampedLock不可重入，同一线程重复获取会死锁
     */
    private static StampedLock stampedLock = new StampedLock();

    /**
     * 倒计时门闩：等待所有任务完成
     */
    private static CountDownLatch countDownLatch = new CountDownLatch(1000);

    /**
     * 共享计数器
     */
    private static int count;

    /**
     * 主方法 - 演示StampedLock的写锁用法
     *
     * <p>writeLock()返回一个stamp（戳记），解锁时必须传入相同的stamp。
     * 这种设计可以检测锁的配对使用，避免误释放。</p>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待被中断
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    // 获取写锁，返回戳记（stamp）
                    // stamp是一个long值，用于后续解锁和验证
                    final long stamp = stampedLock.writeLock();
                    try {
                        // 临界区：写操作
                        count++;
                    } finally {
                        // 解锁时必须传入获取锁时返回的stamp
                        // 如果stamp不匹配会抛出IllegalMonitorStateException
                        stampedLock.unlock(stamp);
                        countDownLatch.countDown();
                    }
                }
            });
        }
        countDownLatch.await();
        System.out.println(count);  // 输出1000
    }
}
