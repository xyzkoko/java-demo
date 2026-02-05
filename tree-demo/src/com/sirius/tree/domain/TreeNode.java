package com.sirius.tree.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 红黑树节点类
 * 支持泛型数据存储，包含红黑节点属性
 */
@Getter
@Setter
public class TreeNode<T> {

    private T data;
    private Color color;
    private boolean isDelete;
    private TreeNode<T> parentNode;
    private TreeNode<T> leftNode;
    private TreeNode<T> rightNode;

    public TreeNode() {
    }

    public TreeNode(T data) {
        this.data = data;
        this.color = Color.RED;
    }

    public boolean isRed(){
        return this.color.equals(Color.RED);
    }

    public boolean isBlack(){
        return this.color.equals(Color.BLACK);
    }

    public void setRed(){
        this.color = Color.RED;
    }

    public void setBlack(){
        this.color = Color.BLACK;
    }

    private enum Color {
        RED,
        BLACK;
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "data=" + data +
                ", color=" + color +
                ", isDelete=" + isDelete +
                ", parentNode=" + (parentNode == null ? null : parentNode.getData()) +
                ", leftNode=" + (leftNode == null ? null : leftNode.getData()) +
                ", rightNode=" + (rightNode == null ? null : rightNode.getData()) +
                '}';
    }
}
