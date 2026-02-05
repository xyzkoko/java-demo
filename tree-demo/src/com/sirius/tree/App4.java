package com.sirius.tree;

import com.sirius.tree.domain.TreeNode;
import lombok.Getter;

/**
 * 红黑树
 * 1.每个节点不是红色就是黑色的；
 * 2.根节点总是黑色的；
 * 3.如果节点是红色的，则它的子节点必须是黑色的（反之不一定）,(也就是从每个叶子到根的所有路径上不能有两个连续的红色节点)；
 * 4.从根节点到叶节点或空子节点的每条路径，必须包含相同数目的黑色节点（即相同的黑色高度）。
 * 5.(新插入的节点颜色总是红色的)
 */
public class App4 {

    public static void main(String[] args) {
        Tree tree = new Tree();
        tree.insert(3);
        tree.insert(4);
        tree.insert(9);
        tree.insert(1);
        tree.insert(0);
        tree.insert(2);
        tree.insert(8);
        tree.insert(7);
        tree.insert(14);
        tree.insert(12);
        tree.insert(18);
        tree.insert(17);
        System.out.println("最大:" + tree.findMax());
        System.out.println("最小:" + tree.findMin());
        tree.infixOrder(tree.root);
    }

    static class Tree {

        @Getter
        private TreeNode<Integer> root;

        // 查找
        public TreeNode<Integer> find(Integer data) {
            TreeNode<Integer> rootNode = this.root;
            while (rootNode != null) {
                if (data.equals(rootNode.getData())) {
                    return rootNode;
                }
                if (data < rootNode.getData()) {
                    rootNode = rootNode.getLeftNode();
                } else {
                    rootNode = rootNode.getRightNode();
                }
            }
            return null;
        }

        // 插入数据
        public int insert(Integer data) {
            TreeNode<Integer> insertTreeNode = new TreeNode<>(data);
            TreeNode<Integer> rootNode = this.root;
            if (rootNode == null) {
                insertTreeNode.setBlack();
                this.root = insertTreeNode;
                return 1;
            }
            // 找到插入位置
            TreeNode<Integer> parentNode = null;
            while (rootNode != null) {
                parentNode = rootNode;
                if (data.equals(rootNode.getData())) {
                    return 0;
                }
                if (data < rootNode.getData()) {
                    rootNode = rootNode.getLeftNode();
                } else {
                    rootNode = rootNode.getRightNode();
                }
            }
            insertTreeNode.setParentNode(parentNode);
            // 修改父节点
            if (data > parentNode.getData()) {
                parentNode.setRightNode(insertTreeNode);
            } else {
                parentNode.setLeftNode(insertTreeNode);
            }
            // 红黑树修正
            this.insertFixUp(insertTreeNode);
            return 1;
        }

        // 红黑树修正
        public void insertFixUp(TreeNode<Integer> treeNode) {
            // 定义父节点和祖父节点
            TreeNode<Integer> parentNode, gparentNode;
            // 需要修正的条件：父节点存在，且父节点的颜色是红色
            while (((parentNode = treeNode.getParentNode()) != null) && parentNode.isRed()) {
                // 获得祖父节点
                gparentNode = parentNode.getParentNode();
                // 若父节点是祖父节点的左子节点，下面的else相反
                if (parentNode == gparentNode.getLeftNode()) {
                    // 获得叔叔节点
                    TreeNode<Integer> uncleNode = gparentNode.getRightNode();
                    // case1:叔叔节点也是红色
                    if (uncleNode != null && uncleNode.isRed()) {
                        // 把父节点和叔叔节点涂黑,把祖父节点涂红
                        parentNode.setBlack();
                        uncleNode.setBlack();
                        gparentNode.setRed();
                        // 把位置放到祖父节点处
                        treeNode = gparentNode;
                        continue;
                    }
                    // case2:叔叔节点是黑色，且当前节点是右子节点
                    if (treeNode == parentNode.getRightNode()) {
                        // 从父节点出左旋
                        leftRotate(parentNode);
                        // 然后将父节点和自己调换一下，为下面右旋做准备
                        TreeNode<Integer> tmp = parentNode;
                        parentNode = treeNode;
                        treeNode = tmp;
                    }
                    // case3:叔叔节点是黑色，且当前节点是左子节点
                    parentNode.setBlack();
                    gparentNode.setRed();
                    rightRotate(gparentNode);
                } else { // 若父节点是祖父节点的右子节点，与上面的情况完全相反，本质是一样的
                    TreeNode<Integer> uncleNode = gparentNode.getLeftNode();
                    // case1:叔叔节点也是红色的
                    if (uncleNode != null && uncleNode.isRed()) {
                        parentNode.setBlack();
                        uncleNode.setBlack();
                        gparentNode.setRed();
                        treeNode = gparentNode;
                        continue;
                    }
                    // case2:叔叔节点是黑色的，且当前节点是左子节点
                    if (treeNode == parentNode.getLeftNode()) {
                        rightRotate(parentNode);
                        TreeNode<Integer> tmp = parentNode;
                        parentNode = treeNode;
                        treeNode = tmp;
                    }
                    // case3:叔叔节点是黑色的，且当前节点是右子节点
                    parentNode.setBlack();
                    gparentNode.setRed();
                    leftRotate(gparentNode);
                }
            }
            // 将根节点设置为黑色
            this.root.setBlack();
        }

        /**
         * 左旋
         * 示意图：对节点x进行左旋
         * * * * *  p                       p
         * * * * * /                       /
         * * * *  x                       y
         * * *  / \                     / \
         * * * lx  y      ----->       x  ry
         * * * *  / \                 / \
         * * * * ly ry               lx ly
         * <p>
         * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
         * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
         * 3. 将y的左子节点设为x，将x的父节点设为y
         */
        private void leftRotate(TreeNode<Integer> treeNode) {
            // 1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
            TreeNode<Integer> rightNode = treeNode.getRightNode();
            TreeNode<Integer> leftNode = rightNode.getLeftNode();
            treeNode.setRightNode(leftNode);
            if (leftNode != null) {
                leftNode.setParentNode(treeNode);
            }
            // 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
            TreeNode<Integer> parentNode = treeNode.getParentNode();
            rightNode.setParentNode(parentNode);
            if (parentNode != null) {
                if (parentNode.getLeftNode() == treeNode) {
                    parentNode.setLeftNode(rightNode);
                } else {
                    parentNode.setRightNode(rightNode);
                }
            } else {
                this.root = rightNode;
            }
            // 3. 将y的左子节点设为x，将x的父节点设为y
            rightNode.setLeftNode(treeNode);
            treeNode.setParentNode(rightNode);
        }

        /**
         * 右旋
         * 示意图：对节点y进行右旋
         * * * * *  p                   p
         * * * * * /                   /
         * * * *  y                   x
         * * *  / \                 / \
         * * * x  ry   ----->      lx  y
         * *  / \                     / \
         * lx  rx                   rx ry
         * <p>
         * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
         * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
         * 3. 将x的右子节点设为y，将y的父节点设为x
         */
        private void rightRotate(TreeNode<Integer> treeNode) {
            // 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
            TreeNode<Integer> leftNode = treeNode.getLeftNode();
            TreeNode<Integer> rightNode = leftNode.getRightNode();
            treeNode.setLeftNode(rightNode);
            if (rightNode != null) {
                rightNode.setParentNode(treeNode);
            }
            // 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
            TreeNode<Integer> parentNode = treeNode.getParentNode();
            leftNode.setParentNode(parentNode);
            if (parentNode != null) {
                if (parentNode.getLeftNode() == treeNode) {
                    parentNode.setLeftNode(leftNode);
                } else {
                    parentNode.setRightNode(leftNode);
                }
            } else {
                this.root = leftNode;
            }
            // 3. 将x的右子节点设为y，将y的父节点设为x
            leftNode.setRightNode(treeNode);
            treeNode.setParentNode(leftNode);
        }

        //中序遍历
        public void infixOrder(TreeNode<Integer> treeNode) {
            if (treeNode != null) {
                infixOrder(treeNode.getLeftNode());
                System.out.println(treeNode);
                infixOrder(treeNode.getRightNode());
            }
        }

        //前序遍历
        public void preOrder(TreeNode<Integer> treeNode) {
            if (treeNode != null) {
                System.out.println(treeNode);
                preOrder(treeNode.getLeftNode());
                preOrder(treeNode.getRightNode());
            }
        }

        //后序遍历
        public void postOrder(TreeNode<Integer> treeNode) {
            if (treeNode != null) {
                postOrder(treeNode.getLeftNode());
                postOrder(treeNode.getRightNode());
                System.out.println(treeNode);
            }
        }

        //找到最大值
        public TreeNode<Integer> findMax() {
            TreeNode<Integer> treeNode = this.root;
            TreeNode<Integer> maxNode = treeNode;
            while (treeNode != null) {
                maxNode = treeNode;
                treeNode = treeNode.getRightNode();
            }
            return maxNode;
        }

        //找到最小值
        public TreeNode<Integer> findMin() {
            TreeNode<Integer> treeNode = this.root;
            TreeNode<Integer> minNode = treeNode;
            while (treeNode != null) {
                minNode = treeNode;
                treeNode = treeNode.getLeftNode();
            }
            return minNode;
        }
    }
}
