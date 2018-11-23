package com.chen.security.tree.avl_tree;

/**
 * Created by PengChen on 2018/11/23.
 */

public class TreeNode {
    TreeNode _leftNode;
    TreeNode _rightNode;
    NodeData _nodeData;
    int _key;
    int _height;

    TreeNode(int key, TreeNode left, TreeNode right, NodeData data) {
        _key = key;
        _leftNode = left;
        _rightNode = right;
        _nodeData = data;
        _height = 1;
    }

}
