package cn.com.sirius.juc;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CyclicBarrier（循环屏障）演示类
 *
 * <p>CyclicBarrier是JUC包中的同步工具类，让一组线程到达一个屏障点时被阻塞，
 * 直到最后一个线程到达屏障点，屏障才会打开，所有被拦截的线程才会继续运行。</p>
 *
 * <h3>与CountDownLatch的区别：</h3>
 * <ul>
 *   <li>CyclicBarrier可重复使用（Cyclic循环），CountDownLatch是一次性的</li>
 *   <li>CyclicBarrier是线程之间相互等待，CountDownLatch是一个或多个线程等待其他线程</li>
 *   <li>CyclicBarrier可以在屏障打开时执行一个回调任务（barrierAction）</li>
 * </ul>
 *
 * <h3>典型应用场景：</h3>
 * <ul>
 *   <li>多线程计算数据，最后合并计算结果</li>
 *   <li>模拟并发测试，让多个线程同时开始执行</li>
 *   <li>多人游戏中等待所有玩家准备就绪后开始游戏</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>模拟赛跑场景，每5个选手准备就绪后同时开跑。
 * 由于有10个选手，屏障会触发两次（可循环使用）。</p>
 *
 * @see java.util.concurrent.CyclicBarrier
 * @see java.util.concurrent.CountDownLatch 一次性倒计时门闩
 */
public class CyclicBarrierDemo {

    /**
     * 创建CyclicBarrier，设置屏障数为5
     * 当5个线程都调用await()后，屏障打开，并优先执行barrierAction回调
     */
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
        @Override
        public void run() {
            // 屏障打开时优先执行的任务（由最后一个到达的线程执行）
            System.out.println("屏障完成,优先执行该方法...");
        }
    });

    /**
     * 主方法 - 启动10个线程模拟赛跑
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果线程被中断
     */
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        // 启动10个选手，每隔1秒启动一个
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            int finalI = i;
            executorService.execute(() -> race(finalI));
        }
    }

    /**
     * 模拟赛跑的选手准备和开跑过程
     *
     * <p>执行流程：</p>
     * <ol>
     *   <li>选手准备（模拟耗时1秒）</li>
     *   <li>选手就位，调用await()等待其他选手</li>
     *   <li>当5个选手都就位后，屏障打开，所有选手同时开跑</li>
     * </ol>
     *
     * @param threadNum 选手编号
     */
    private static void race(int threadNum) {
        try {
            // 模拟选手准备时间
            Thread.sleep(1000);
            System.out.println(threadNum + " is Ready");

            // 调用await()表示当前线程已到达屏障点
            // 线程将阻塞直到所有parties个线程都到达
            // 可选：await(timeout, unit) 设置超时时间
            cyclicBarrier.await();

            // 屏障打开后继续执行
            System.out.println(threadNum + " continue");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
