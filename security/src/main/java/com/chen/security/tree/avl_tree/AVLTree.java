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
     * @param root 旋转点-----最低失衡点,调整之后的根节点
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
     * @param root 旋转点-----最低失衡点,调整之后的根节点
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

    public TreeNode delete(TreeNode root, TreeNode node) {
        if (root == null) {
            return null;
        }
        if (node._key < root._key)        //要删除的结点在左子树
        {
            root._leftNode = delete(root._leftNode, node);
            if (height(root._rightNode) - height(root._leftNode) == 2)    //删除导致二叉树失衡
            {
                TreeNode rightNode = root._rightNode;
                if (height(rightNode._leftNode)>height(rightNode._rightNode))
                    root = rightLeftRotate(root);
                else root = rightRightRotate(root);
            }
        }
        else if (node._key > root._key)    //要删除的结点在右子树
        {
            root._rightNode = delete(root._rightNode, node);
            if (height(root._leftNode) - height(root._rightNode) == 2)    //删除导致二叉树失衡
            {
                TreeNode leftNode = root._leftNode;
                if (height(leftNode._leftNode) > height(leftNode._rightNode))
                    root = leftLeftRotate(root);
                else root = leftRightRotate(root);
            }
        }
        else    //找到了要删除的结点
        {
            if (root._leftNode != null && root._rightNode != null)    //结点的左右子树均不为空
            {
                if (height(root._leftNode) > height(root._rightNode))
                {
                /*
                * 如果tree的左子树比右子树高；
                * 则(01)找出tree的左子树中的最大节点
                *  (02)将该最大节点的值赋值给tree。
                *  (03)删除该最大节点。
                * 这类似于用"tree的左子树中最大节点"做"tree"的替身；
                * 采用这种方式的好处是：删除"tree的左子树中最大节点"之后，AVL树仍然是平衡的。
                */

                    TreeNode maxNode = null;//maximus(root._leftNode);
                    root._key = maxNode._key;
                    root._leftNode = delete(root._leftNode, maxNode);
                }
                else
                {
                /*
                 * 如果tree的左子树不比右子树高(即它们相等，或右子树比左子树高1)
                 * 则(01)找出tree的右子树中的最小节点
                 *  (02)将该最小节点的值赋值给tree。
                 *  (03)删除该最小节点。
                 * 这类似于用"tree的右子树中最小节点"做"tree"的替身；
                 * 采用这种方式的好处是：删除"tree的右子树中最小节点"之后，AVL树仍然是平衡的。
                 */

                    TreeNode minNode = null;//minimus(root._rightNode);
                    root._key = minNode._key;
                    root._rightNode = delete(root._rightNode, minNode);
                }
            }
            else
            {
                TreeNode tmp = root;
                root = (root._leftNode != null) ? root._leftNode : root._rightNode;
            }
        }
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
            inOrder(root._leftNode);
//            Log.i("ChenSdk", "root key = " + root._key);
            System.out.println("root key = " + root._key);
            inOrder(root._rightNode);
        }
    }

    public void postOrder(TreeNode root) {
        if (root != null) {
            postOrder(root._leftNode);
            postOrder(root._rightNode);
//            Log.i("ChenSdk", "root key = " + root._key);
            System.out.println("root key = " + root._key);
        }
    }
}
