package cn.com.sirius.juc;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CountDownLatch（倒计时门闩）演示类
 *
 * <p>CountDownLatch是JUC包中的同步工具类，用于协调多个线程之间的同步。
 * 它允许一个或多个线程等待，直到在其他线程中执行的一组操作完成。</p>
 *
 * <h3>核心概念：</h3>
 * <ul>
 *   <li>初始化时设置计数器值（count）</li>
 *   <li>每次调用countDown()方法，计数器减1</li>
 *   <li>await()方法会阻塞当前线程，直到计数器变为0</li>
 *   <li>计数器不能重置，是一次性的同步工具</li>
 * </ul>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>主线程等待多个子线程完成初始化后再继续执行</li>
 *   <li>实现多个线程同时开始执行（所有线程await同一个CountDownLatch）</li>
 *   <li>统计限定时间内的任务执行次数</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>在5秒超时时间内，统计后台线程执行任务的次数。
 * 通过await(timeout, unit)方法实现带超时的等待。</p>
 *
 * @see java.util.concurrent.CountDownLatch
 * @see java.util.concurrent.CyclicBarrier 可重用的同步屏障
 */
public class CountDownLatchDemo {

    /**
     * 主方法 - 演示CountDownLatch的超时等待功能
     *
     * <p>执行流程：</p>
     * <ol>
     *   <li>创建计数值为100的CountDownLatch</li>
     *   <li>启动守护线程，每100ms提交一个任务到线程池</li>
     *   <li>每个任务执行时递减计数器并统计执行次数</li>
     *   <li>主线程最多等待5秒，然后输出执行次数</li>
     * </ol>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待过程中线程被中断
     */
    public static void main(String[] args) throws InterruptedException {
        // 创建CountDownLatch，初始计数值为100
        // 当计数值降为0时，所有等待的线程将被唤醒
        final CountDownLatch countDownLatch = new CountDownLatch(100);

        // 创建缓存线程池，使用自定义ThreadFactory创建守护线程
        // 守护线程会在主线程结束时自动终止，避免程序无法退出
        final ExecutorService executorService = Executors.newCachedThreadPool(r -> {
            final Thread thread = new Thread(r);
            thread.setDaemon(true);  // 设置为守护线程
            return thread;
        });

        // 原子计数器，用于统计任务执行次数
        // 使用AtomicInteger保证多线程环境下的线程安全
        final AtomicInteger atomicInteger = new AtomicInteger(0);

        // 创建任务生产者线程，持续向线程池提交任务
        final Thread thread = new Thread(() -> {
            while (true) {
                try {
                    // 每100毫秒提交一个任务
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 向线程池提交任务
                executorService.execute(() -> {
                    try {
                        // 检查计数器是否还有剩余
                        if (countDownLatch.getCount() > 0) {
                            // 递减执行次数计数（注意：这里应该是getAndIncrement）
                            atomicInteger.getAndDecrement();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        // 无论任务是否成功，都要递减CountDownLatch计数器
                        // 这是保证await不会永久阻塞的关键
                        countDownLatch.countDown();
                    }
                });
            }
        });
        thread.setDaemon(true);  // 设置为守护线程
        thread.start();

        // 带超时的等待：最多等待5秒或计数器归零
        // 返回true表示计数器归零，false表示超时
        countDownLatch.await(5, TimeUnit.SECONDS);

        // 输出统计结果
        System.out.println("执行了" + atomicInteger.get() + "次");
    }

}
