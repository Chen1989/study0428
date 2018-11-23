package com.chen.security.tree.avl_tree;

/**
 * Created by PengChen on 2018/11/23.
 */

public class ChenMain {
    public static void main(String[] args) {
        //, 7, 8, 9, 10, 11, 12, 13, 14, 15
        int[] arr = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        AVLTree tree = new AVLTree();
        TreeNode root = tree.getRoot();
        for (int i : arr) {
            root = tree.insert(root, i);
        }

        tree.inOrder(root);
        System.out.println("-----");
        tree.preOrder(root);
    }
}
