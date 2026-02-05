package com.sirius.design.decoratormode;

/**
 * 装饰器模式 - 装饰器类（Decorator）
 *
 * <p>持有组件接口的引用，在调用原始方法的基础上添加新功能。
 * 通过组合而非继承的方式扩展对象功能。</p>
 *
 * <h3>核心特点：</h3>
 * <ul>
 *   <li>实现与具体组件相同的接口（保证透明性）</li>
 *   <li>持有被装饰对象的引用（组合关系）</li>
 *   <li>在调用原始方法前后可添加额外行为</li>
 * </ul>
 *
 * <h3>装饰流程：</h3>
 * <ol>
 *   <li>接收被装饰对象（通过构造器）</li>
 *   <li>实现接口方法时，先调用被装饰对象的方法</li>
 *   <li>在原始方法调用前后添加装饰逻辑</li>
 * </ol>
 *
 * <h3>Java IO中的应用：</h3>
 * <pre>
 * // FileInputStream是具体组件
 * // BufferedInputStream是装饰器
 * InputStream in = new BufferedInputStream(new FileInputStream("file.txt"));
 * </pre>
 *
 * @see Human 组件接口
 * @see Man 被装饰的具体组件
 */
public class DecoratorMan implements Human {

    /**
     * 被装饰的组件对象引用
     * 装饰器通过此引用调用原始方法
     */
    private Human human;

    /**
     * 构造方法，接收被装饰的对象
     *
     * @param human 被装饰的组件对象
     */
    public DecoratorMan(Human human) {
        this.human = human;
    }

    /**
     * 装饰后的name方法
     * 先调用原始方法，再添加装饰行为
     */
    @Override
    public void name() {
        // 调用被装饰对象的原始方法
        human.name();
        // 添加装饰后的新功能
        System.out.println("我是装饰人");
    }

    /**
     * 装饰后的age方法
     * 先调用原始方法，再添加装饰行为
     */
    @Override
    public void age() {
        // 调用被装饰对象的原始方法
        human.age();
        // 添加装饰后的新功能（"伪装"年龄）
        System.out.println("我打扮成18岁");
    }
}
