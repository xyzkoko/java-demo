package cn.com.sirius.juc;

import java.util.concurrent.*;

/**
 * Future异步任务演示 - FutureTask方式
 *
 * <p>FutureTask是Future接口的实现类，同时实现了Runnable接口，
 * 因此可以直接交给Thread执行，而不需要通过ExecutorService。</p>
 *
 * <h3>FutureTask的特点：</h3>
 * <ul>
 *   <li>实现了RunnableFuture接口（继承自Runnable和Future）</li>
 *   <li>可以包装Callable或Runnable任务</li>
 *   <li>可以直接提交给Thread执行</li>
 *   <li>任务只会执行一次，重复调用run()不会重新执行</li>
 * </ul>
 *
 * <h3>FutureTask vs Future：</h3>
 * <ul>
 *   <li>Future是接口，FutureTask是具体实现</li>
 *   <li>FutureTask可以直接作为线程任务使用</li>
 *   <li>FutureTask内部管理任务状态和结果</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>使用FutureTask包装Callable任务，直接交给Thread执行，
 * 然后通过FutureTask.get()获取执行结果。</p>
 *
 * @see java.util.concurrent.FutureTask
 * @see java.util.concurrent.RunnableFuture
 */
public class FutureDemo2 {

    /**
     * 主方法 - 演示FutureTask的使用
     *
     * <p>与FutureDemo1的区别：</p>
     * <ul>
     *   <li>不需要创建ExecutorService</li>
     *   <li>FutureTask直接作为Thread的构造参数</li>
     *   <li>更轻量，适合简单的异步任务场景</li>
     * </ul>
     *
     * @param args 命令行参数（未使用）
     * @throws InterruptedException 如果等待被中断
     * @throws ExecutionException 如果任务执行时抛出异常
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 创建FutureTask，包装Callable任务
        // FutureTask构造器也可以接收Runnable和result参数
        FutureTask<Boolean> futureTask = new FutureTask<>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                System.out.print("busy...");
                // 模拟耗时操作
                Thread.sleep(5000);
                System.out.println("finish!");
                return true;
            }
        });

        // FutureTask实现了Runnable接口，可以直接作为Thread的任务
        // 启动新线程执行任务
        new Thread(futureTask).start();

        // 主线程继续执行其他操作
        System.out.println("do some things...");
        Thread.sleep(2000);

        // 通过FutureTask.get()获取任务结果
        // get()会阻塞直到任务完成
        if (futureTask.get()) {
            System.out.println("success");
        } else {
            System.out.println("failure");
        }
    }

}
