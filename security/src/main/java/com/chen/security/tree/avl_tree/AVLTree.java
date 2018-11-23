package com.chen.security.tree.avl_tree;

/**
 * Created by PengChen on 2018/11/23.
 */

public class AVLTree {

    private TreeNode _root = null;

    TreeNode getRoot() {
        return _root;
    }

    private int height(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return node._height;
    }

    private int max(int p1, int p2) {
        return p1 > p2 ? p1 : p2;
    }

    /**
     * root左孩子的左孩子插入节点后，root失去平衡
     * @param root 旋转点-----最低失衡点
     * @return
     */
    private TreeNode leftLeftRotate(TreeNode root) {
        TreeNode left = root._leftNode;
        root._leftNode = left._rightNode;
        left._rightNode = root;
        root._height = max(height(root._leftNode), height(root._rightNode)) + 1;
        left._height = max(height(left._leftNode), height(left._rightNode)) + 1;
        return left;
    }

    /**
     * root右孩子的右孩子插入节点后，root失去平衡
     * @param root 旋转点-----最低失衡点
     * @return
     */
    private TreeNode rightRightRotate(TreeNode root) {
        TreeNode right = root._rightNode;
        root._rightNode = right._leftNode;
        right._leftNode = root;
        root._height = max(height(root._leftNode), height(root._rightNode)) + 1;
        right._height = max(height(right._leftNode), height(right._rightNode)) + 1;
        return right;
    }

    /**
     * root左孩子的右孩子插入节点后，root失去平衡
     * @param root
     * @return
     */
    private TreeNode leftRightRotate(TreeNode root) {
        rightRightRotate(root._leftNode);
        return leftLeftRotate(root);
    }

    /**
     * root右孩子的左孩子插入节点后，root失去平衡
     * @param root
     * @return
     */
    private TreeNode rightLeftRotate(TreeNode root) {
        leftLeftRotate(root._rightNode);
        return rightRightRotate(root);
    }

    public TreeNode insert(TreeNode root, int key) {
        if (root == null) {
            return new TreeNode(key, null, null, null);
        } else if (root._key > key) {
            root._leftNode = insert(root._leftNode, key);
            int delta = height(root._leftNode) - height(root._rightNode);
            if (delta >= 2 || delta <= -2) {
                if (root._leftNode._key > key) {
                    root = leftLeftRotate(root);
                } else {
                    root = leftRightRotate(root);
                }
            }
        } else if (root._key < key){
            root._rightNode = insert(root._rightNode, key);
            int delta = height(root._rightNode) - height(root._leftNode);
            if (delta >= 2 || delta <= -2) {
                if (root._rightNode._key < key) {
                    root = rightRightRotate(root);
                } else {
                    root = rightLeftRotate(root);
                }
            }
        }
        root._height = max(height(root._leftNode), height(root._rightNode)) + 1;
        return root;
    }

    public void preOrder(TreeNode root) {
        if (root != null) {
//            Log.i("ChenSdk", "root key = " + root._key);
            System.out.println("root key = " + root._key);
            preOrder(root._leftNode);
            preOrder(root._rightNode);
        }
    }

    public void inOrder(TreeNode root) {
        if (root != null) {
            preOrder(root._leftNode);
//            Log.i("ChenSdk", "root key = " + root._key);
            System.out.println("root key = " + root._key);
            preOrder(root._rightNode);
        }
    }

    public void postOrder(TreeNode root) {
        if (root != null) {
            preOrder(root._leftNode);
            preOrder(root._rightNode);
//            Log.i("ChenSdk", "root key = " + root._key);
            System.out.println("root key = " + root._key);
        }
    }
}
