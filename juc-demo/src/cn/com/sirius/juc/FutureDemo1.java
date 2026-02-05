package cn.com.sirius.juc;

import java.util.concurrent.*;

/**
 * Future异步任务演示 - ExecutorService.submit()方式
 *
 * <p>Future是JUC包中用于表示异步计算结果的接口。
 * 它提供了检查计算是否完成、等待计算完成、获取计算结果等方法。</p>
 *
 * <h3>Future的核心方法：</h3>
 * <ul>
 *   <li><b>get()</b>：阻塞等待任务完成并返回结果</li>
 *   <li><b>get(timeout, unit)</b>：带超时的等待</li>
 *   <li><b>isDone()</b>：判断任务是否完成</li>
 *   <li><b>cancel(mayInterruptIfRunning)</b>：取消任务</li>
 *   <li><b>isCancelled()</b>：判断任务是否被取消</li>
 * </ul>
 *
 * <h3>Callable vs Runnable：</h3>
 * <ul>
 *   <li>Callable可以返回结果，Runnable不能</li>
 *   <li>Callable可以抛出受检异常，Runnable不能</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>通过ExecutorService.submit()提交Callable任务，获取Future对象，
 * 主线程可以继续执行其他操作，最后通过Future.get()获取异步结果。</p>
 *
 * @see java.util.concurrent.Future
 * @see java.util.concurrent.Callable
 * @see java.util.concurrent.ExecutorService#submit(Callable)
 */
public class FutureDemo1 {

    /**
     * 主方法 - 演示Future的基本用法
     *
     * <p>执行流程：</p>
     * <ol>
     *   <li>提交Callable任务到线程池，立即返回Future对象</li>
     *   <li>主线程继续执行其他操作（do some things）</li>
     *   <li>调用future.get()等待并获取任务结果</li>
     * </ol>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待被中断
     * @throws ExecutionException 如果任务执行时抛出异常
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();

        // 提交Callable任务，返回Future对象
        // submit()方法是非阻塞的，会立即返回
        Future<Boolean> future = executorService.submit(new MyCallable());

        // 主线程继续执行其他操作，不需要等待任务完成
        System.out.println("do some things...");
        Thread.sleep(2000);

        // get()方法会阻塞直到任务完成
        // 如果任务已完成，立即返回结果
        // 如果任务未完成，阻塞等待
        if (future.get()) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
    }

    /**
     * 实现Callable接口的异步任务
     * Callable是泛型接口，泛型参数指定返回值类型
     */
    static class MyCallable implements Callable<Boolean> {

        /**
         * 任务执行方法
         *
         * @return 任务执行结果
         * @throws Exception 可以抛出任何异常，会被包装在ExecutionException中
         */
        @Override
        public Boolean call() throws Exception {
            System.out.print("busy...");
            // 模拟耗时操作
            Thread.sleep(5000);
            System.out.println("finish!");
            return true;
        }
    }
}
