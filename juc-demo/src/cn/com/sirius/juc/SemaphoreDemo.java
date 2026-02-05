package cn.com.sirius.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore（信号量）演示类
 *
 * <p>Semaphore是JUC包中的同步工具类，用于控制同时访问特定资源的线程数量。
 * 它通过协调各个线程，以保证合理的使用公共资源。</p>
 *
 * <h3>核心概念：</h3>
 * <ul>
 *   <li>信号量维护了一组许可证（permits）</li>
 *   <li>acquire()：获取许可，如果没有可用许可则阻塞</li>
 *   <li>release()：释放许可，增加可用许可数量</li>
 *   <li>tryAcquire()：尝试获取许可，可设置超时时间，不会阻塞</li>
 * </ul>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>数据库连接池：限制同时使用的连接数</li>
 *   <li>限流：控制接口的并发访问量</li>
 *   <li>资源池管理：如打印机、停车位等有限资源的管理</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>使用两个信号量控制并发：</p>
 * <ul>
 *   <li>semaphore(10)：每次获取5个许可，最多允许2个任务同时执行</li>
 *   <li>semaphore2(10)：使用tryAcquire尝试获取，演示非阻塞获取方式</li>
 * </ul>
 *
 * @see java.util.concurrent.Semaphore
 */
public class SemaphoreDemo {

    /**
     * 主信号量：初始10个许可
     * 每个任务需要获取5个许可，因此最多同时执行2个任务
     */
    private static Semaphore semaphore = new Semaphore(10);

    /**
     * 辅助信号量：演示tryAcquire的使用
     */
    private static Semaphore semaphore2 = new Semaphore(10);


    /**
     * 主方法 - 演示Semaphore的acquire和tryAcquire用法
     *
     * <p>程序会持续运行，不断提交任务到线程池，
     * 通过信号量控制同时执行的任务数量。</p>
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        while (true) {
            try {
                // tryAcquire：尝试在1秒内获取1个许可
                // 返回true表示获取成功，false表示超时未获取到
                // 特点：非阻塞，适合需要快速失败的场景
                if (semaphore2.tryAcquire(1, TimeUnit.SECONDS)) {
                    System.out.println("线程" + Thread.currentThread().getName() + "尝试获取到许可");
                }

                // acquire：获取5个许可，如果许可不足则阻塞等待
                // 这里每次获取5个，信号量总共10个，所以最多2个任务同时执行
                semaphore.acquire(5);

                // 提交任务到线程池执行
                executorService.submit(() -> {
                    try {
                        System.out.println("线程" + Thread.currentThread().getName() + "执行");
                        // 模拟任务执行耗时5秒
                        Thread.sleep(5 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        // 释放许可（必须在finally中确保释放）
                        // 注意：获取多少个许可，就要释放多少个
                        semaphore.release(5);
                        semaphore2.release();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
