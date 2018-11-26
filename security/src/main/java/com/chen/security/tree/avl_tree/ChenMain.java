package com.chen.security.tree.avl_tree;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by PengChen on 2018/11/23.
 */

public class ChenMain {
    public static void main(String[] args) {
        //, 7, 8, 9, 10, 11, 12, 13, 14, 15
        int[] arr = new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        AVLTree tree = new AVLTree();
        TreeNode root = tree.getRoot();
        System.out.println("start time = " + System.currentTimeMillis());
        for (int i = 1; i < 100000; i++) {
            root = tree.insert(root, i);
        }
        System.out.println("finish time = " + System.currentTimeMillis());
        List<TreeNode> ls = new LinkedList<>();
        for (int i = 1; i < 100000; i++) {
            ls.add(new TreeNode(i, null, null, null));
        }
        System.out.println("finish time = " + System.currentTimeMillis());
//        tree.inOrder(root);
        System.out.println("-----");
//        tree.preOrder(root);
    }
}
