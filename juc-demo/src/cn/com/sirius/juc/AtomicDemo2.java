package cn.com.sirius.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.LongAdder;

/**
 * Atomic原子类演示 - 引用类型和字段更新器
 *
 * <p>除了基本类型的原子类，JUC还提供了引用类型原子类和字段更新器，
 * 用于更复杂场景下的原子操作。</p>
 *
 * <h3>AtomicReference - 原子引用：</h3>
 * <p>可以原子地更新对象引用。常用于实现无锁数据结构，
 * 但要注意ABA问题（可使用AtomicStampedReference解决）。</p>
 *
 * <h3>AtomicIntegerFieldUpdater - 字段更新器：</h3>
 * <p>用于原子地更新某个类的指定volatile字段，而不需要修改原有类的代码。
 * 适用于需要对已有类的字段进行原子操作的场景。</p>
 *
 * <h3>字段更新器的使用限制：</h3>
 * <ul>
 *   <li>目标字段必须是volatile修饰的</li>
 *   <li>目标字段不能是static的</li>
 *   <li>目标字段不能是final的</li>
 *   <li>字段的可见性需要与更新器的访问权限匹配</li>
 * </ul>
 *
 * <h3>CAS操作说明：</h3>
 * <p>compareAndSet(expect, update)：当前值等于expect时更新为update，返回true；
 * 否则不更新，返回false。</p>
 *
 * <h3>本示例演示：</h3>
 * <ul>
 *   <li>AtomicReference的CAS操作：值从0->2->4</li>
 *   <li>AtomicIntegerFieldUpdater的CAS操作：字段值从0->2->4</li>
 * </ul>
 *
 * @see java.util.concurrent.atomic.AtomicReference
 * @see java.util.concurrent.atomic.AtomicIntegerFieldUpdater
 * @see java.util.concurrent.atomic.AtomicStampedReference 解决ABA问题
 */
public class AtomicDemo2 {

    /**
     * AtomicReference - 原子引用
     * 可以原子地更新Integer对象的引用
     */
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(0);

    /**
     * AtomicIntegerFieldUpdater - 字段更新器
     * 用于原子更新AtomicDemo2类中名为"filed"的int字段
     */
    private static AtomicIntegerFieldUpdater<AtomicDemo2> atomicIntegerFieldUpdater =
            AtomicIntegerFieldUpdater.newUpdater(AtomicDemo2.class, "filed");

    /**
     * 目标字段：必须用volatile修饰，且不能是static
     * 字段更新器通过反射访问此字段进行原子操作
     */
    private volatile int filed;

    /**
     * 主方法 - 演示CAS操作的条件更新特性
     *
     * <p>CAS操作只有在当前值等于预期值时才会更新，
     * 这个特性可以用来实现乐观锁、无锁队列等数据结构。</p>
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        // ========== Part 1: AtomicReference演示 ==========
        // 初始值为0

        // CAS(0->2)：当前值是0，等于expect，更新成功，值变为2
        atomicReference.compareAndSet(0, 2);

        // CAS(1->3)：当前值是2，不等于expect(1)，更新失败，值仍为2
        atomicReference.compareAndSet(1, 3);

        // CAS(2->4)：当前值是2，等于expect，更新成功，值变为4
        atomicReference.compareAndSet(2, 4);

        // CAS(3->5)：当前值是4，不等于expect(3)，更新失败，值仍为4
        atomicReference.compareAndSet(3, 5);

        System.out.println(atomicReference.get());  // 输出: 4

        // ========== Part 2: AtomicIntegerFieldUpdater演示 ==========
        AtomicDemo2 atomicDemo2 = new AtomicDemo2();
        // atomicDemo2.filed初始值为0

        // CAS(0->2)：filed是0，等于expect，更新成功
        atomicIntegerFieldUpdater.compareAndSet(atomicDemo2, 0, 2);

        // CAS(1->3)：filed是2，不等于expect(1)，更新失败
        atomicIntegerFieldUpdater.compareAndSet(atomicDemo2, 1, 3);

        // CAS(2->4)：filed是2，等于expect，更新成功
        atomicIntegerFieldUpdater.compareAndSet(atomicDemo2, 2, 4);

        // CAS(3->5)：filed是4，不等于expect(3)，更新失败
        atomicIntegerFieldUpdater.compareAndSet(atomicDemo2, 3, 5);

        System.out.println(atomicDemo2.filed);  // 输出: 4
    }
}
