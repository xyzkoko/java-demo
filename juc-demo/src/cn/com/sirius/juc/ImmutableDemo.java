package cn.com.sirius.juc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 不可变（Immutable）集合演示类
 *
 * <p>不可变集合是指创建后内容不能被修改的集合。在多线程环境中，
 * 不可变对象天生是线程安全的，因为它们的状态不会改变。</p>
 *
 * <h3>创建不可变集合的方式：</h3>
 * <ul>
 *   <li><b>Collections.unmodifiableXxx()</b>：包装现有集合为不可修改视图</li>
 *   <li><b>Guava ImmutableXxx</b>：Google Guava库提供的不可变集合</li>
 *   <li><b>Java 9+ List.of()/Set.of()/Map.of()</b>：工厂方法创建不可变集合</li>
 * </ul>
 *
 * <h3>unmodifiable vs Immutable的区别：</h3>
 * <ul>
 *   <li>unmodifiableXxx()返回的是原集合的只读视图，如果原集合被修改，视图也会变化</li>
 *   <li>Guava的ImmutableXxx是真正的不可变，会复制原集合的数据</li>
 *   <li>Java 9+的工厂方法创建的也是真正的不可变集合</li>
 * </ul>
 *
 * <h3>不可变集合的优点：</h3>
 * <ul>
 *   <li>线程安全：无需同步，可以安全地在多线程间共享</li>
 *   <li>安全性：防止意外修改，作为方法返回值更安全</li>
 *   <li>性能：某些场景下性能更好（无需复制防御性拷贝）</li>
 * </ul>
 *
 * <h3>本示例演示：</h3>
 * <p>使用Collections.unmodifiableMap()创建不可修改的Map，
 * 尝试修改时会抛出UnsupportedOperationException。</p>
 *
 * @see java.util.Collections#unmodifiableMap(Map)
 * @see java.util.Collections#unmodifiableList(java.util.List)
 */
public class ImmutableDemo {

    /**
     * 原始可变Map
     */
    private static Map<Integer, Object> map = new HashMap<>();

    /**
     * 主方法 - 演示不可修改集合的行为
     *
     * <p>执行流程：</p>
     * <ol>
     *   <li>向原始Map添加元素</li>
     *   <li>将Map包装为不可修改视图</li>
     *   <li>尝试向不可修改Map添加元素（会抛出异常）</li>
     * </ol>
     *
     * @param args 命令行参数（未使用）
     * @throws UnsupportedOperationException 尝试修改不可变集合时抛出
     */
    public static void main(String[] args) {
        // 向原始Map添加元素
        map.put(0, 0);

        // 将Map包装为不可修改视图
        // 注意：这只是一个视图包装，原始map如果在其他地方被引用仍可修改
        // 最佳实践：不要保留对原始map的引用
        map = Collections.unmodifiableMap(map);

        // 尝试修改不可变Map - 会抛出UnsupportedOperationException
        // java.lang.UnsupportedOperationException
        map.put(1, 1);

        // 这行代码不会执行，因为上面会抛出异常
        System.out.println(map);
    }
}
