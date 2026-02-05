package cn.com.sirius.juc;

import java.util.Iterator;
import java.util.Vector;

/**
 * 集合遍历时删除元素的正确与错误方式演示
 *
 * <p>在Java中，遍历集合时删除元素是一个常见的陷阱。
 * 使用不当会抛出ConcurrentModificationException异常。</p>
 *
 * <h3>ConcurrentModificationException产生原因：</h3>
 * <p>Java集合使用modCount（修改计数器）来检测结构性修改。
 * Iterator在创建时会记录当时的modCount值（expectedModCount），
 * 每次调用next()时会检查modCount是否变化，如果变化则抛出异常。
 * 这是"快速失败（fail-fast）"机制。</p>
 *
 * <h3>四种遍历删除方式对比：</h3>
 * <table border="1">
 *   <tr><th>方式</th><th>结果</th><th>原因</th></tr>
 *   <tr><td>foreach + 集合.remove()</td><td>异常</td><td>foreach底层是Iterator，集合remove会改变modCount</td></tr>
 *   <tr><td>Iterator + iterator.remove()</td><td>成功</td><td>iterator.remove()会同步更新expectedModCount</td></tr>
 *   <tr><td>Iterator + 集合.remove()</td><td>异常</td><td>集合remove改变modCount，但expectedModCount不变</td></tr>
 *   <tr><td>普通for循环 + 集合.remove()</td><td>成功</td><td>不使用Iterator，没有modCount检查</td></tr>
 * </table>
 *
 * <h3>最佳实践：</h3>
 * <ul>
 *   <li>单线程遍历删除：使用Iterator的remove()方法</li>
 *   <li>Java 8+：使用removeIf()方法，如 list.removeIf(x -> x == 3)</li>
 *   <li>多线程场景：使用CopyOnWriteArrayList等并发集合</li>
 * </ul>
 *
 * @see java.util.ConcurrentModificationException
 * @see java.util.Iterator#remove()
 */
public class VectorDemo {

    /**
     * 方式1：foreach + 集合.remove() - 会抛异常
     *
     * <p>foreach语法糖底层使用Iterator实现，但调用的是集合的remove方法，
     * 会导致modCount和expectedModCount不一致，抛出ConcurrentModificationException。</p>
     *
     * @param vector 待操作的Vector集合
     * @throws java.util.ConcurrentModificationException 总是抛出
     */
    private static void test1(Vector<Integer> vector) {
        for (Integer i : vector) {
            if (i == 3) {
                vector.remove(i);  // 错误：在迭代过程中调用集合的remove
            }
        }
    }

    /**
     * 方式2：Iterator + iterator.remove() - 正确方式
     *
     * <p>Iterator的remove()方法会在删除元素后同步更新expectedModCount，
     * 因此不会触发ConcurrentModificationException。</p>
     *
     * @param vector 待操作的Vector集合
     */
    private static void test2(Vector<Integer> vector) {
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i == 3) {
                iterator.remove();  // 正确：使用迭代器的remove方法
            }
        }
    }

    /**
     * 方式3：Iterator + 集合.remove() - 会抛异常
     *
     * <p>虽然使用了Iterator遍历，但调用的是集合的remove方法，
     * modCount被修改但expectedModCount没有同步，下次next()时会抛异常。</p>
     *
     * @param vector 待操作的Vector集合
     * @throws java.util.ConcurrentModificationException 总是抛出
     */
    private static void test3(Vector<Integer> vector) {
        Iterator<Integer> iterator = vector.iterator();
        while (iterator.hasNext()) {
            Integer i = iterator.next();
            if (i == 3) {
                vector.remove(i);  // 错误：混用Iterator遍历和集合的remove
            }
        }
    }

    /**
     * 方式4：普通for循环 + 集合.remove() - 可行但有陷阱
     *
     * <p>普通for循环不使用Iterator，因此没有modCount检查。
     * 但要注意：删除元素后索引会变化，可能导致漏删。
     * 建议从后向前遍历，或在删除后i--来避免这个问题。</p>
     *
     * @param vector 待操作的Vector集合
     */
    private static void test4(Vector<Integer> vector) {
        for (int i = 0; i < vector.size(); i++) {
            if (vector.get(i) == 3) {
                vector.remove(i);
                // 注意：如果有连续的3，这里会漏删
                // 修复方法：删除后执行 i-- 或从后向前遍历
            }
        }
    }

    /**
     * 主方法 - 演示遍历删除的异常情况
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        final Vector<Integer> vector = new Vector<>();
        vector.add(1);
        vector.add(2);
        vector.add(3);

        // 运行test1会抛出ConcurrentModificationException
        // 如需正常运行，请改为调用test2或test4
        test1(vector);
        System.out.println(vector);
    }


}
