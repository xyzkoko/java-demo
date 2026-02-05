package com.sirius.design.ordermode;

/**
 * 命令模式 - 命令接口（Command）
 *
 * <p>定义执行命令的统一接口。所有具体命令都必须实现此接口。
 * 调用者通过此接口调用命令，无需知道具体的接收者和执行细节。</p>
 *
 * <h3>职责：</h3>
 * <ul>
 *   <li>声明执行操作的接口</li>
 *   <li>作为调用者和接收者之间的解耦层</li>
 * </ul>
 *
 * <h3>扩展功能：</h3>
 * <p>实际应用中，命令接口还可以定义：</p>
 * <ul>
 *   <li>{@code undo()} - 撤销操作</li>
 *   <li>{@code redo()} - 重做操作</li>
 *   <li>{@code getName()} - 获取命令名称（用于日志）</li>
 * </ul>
 *
 * @see CatActoin 具体命令-猫跳
 * @see CatCall 具体命令-猫叫
 * @see DogCall 具体命令-狗叫
 * @see DogEat 具体命令-狗吃
 */
public interface Order {

    /**
     * 执行命令
     * 具体命令类实现此方法，委托给接收者执行实际操作
     */
    void execute();
}
