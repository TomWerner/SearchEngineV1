package org.uiowa.cs2820.engine;

import java.util.LinkedList;
import java.util.Queue;

public class AVLTree
{
    AVLNode<BinaryFileNode> root;

    public AVLTree()
    {
        root = null;
    }
    
    public static void main(String[] args)
    {
        AVLTree tree = new AVLTree();
        tree.insert(new BinaryFileNode(new Field("value", "a"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "b"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "c"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "d"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "e"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "f"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "g"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "h"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "i"), 0));
        tree.insert(new BinaryFileNode(new Field("value", "j"), 0));
        tree.PrintTree();
    }

    public BinaryFileNode Maximum()
    {
        AVLNode<BinaryFileNode> local = root;
        if (local == null)
            return null;
        while (local.getRight() != null)
            local = local.getRight();
        return local.getData();
    }

    public BinaryFileNode Minimum()
    {
        AVLNode<BinaryFileNode> local = root;
        if (local == null)
            return null;
        while (local.getLeft() != null)
        {
            local = local.getLeft();
        }
        return local.getData();
    }

    private int depth(AVLNode<BinaryFileNode> node)
    {
        if (node == null)
            return 0;
        return node.getDepth();
        //0 1 + Math.max(depth(node.getLeft()), depth(node.getRight()));
    }

    public AVLNode<BinaryFileNode> insert(BinaryFileNode data)
    {
        root = insert(root, data);
        switch (balanceNumber(root))
        {
        case 1:
            root = rotateLeft(root);
            break;
        case -1:
            root = rotateRight(root);
            break;
        default:
            break;
        }
        return root;
    }

    public AVLNode<BinaryFileNode> insert(AVLNode<BinaryFileNode> node, BinaryFileNode data)
    {
        if (node == null)
            return new AVLNode<BinaryFileNode>(data);
        if (node.getData().compareTo(data) > 0)
        {
            node = new AVLNode<BinaryFileNode>(node.getData(), insert(node.getLeft(), data), node.getRight());
            // node.setLeft(insert(node.getLeft(), data));
        }
        else if (node.getData().compareTo(data) < 0)
        {
            // node.setRight(insert(node.getRight(), data));
            node = new AVLNode<BinaryFileNode>(node.getData(), node.getLeft(), insert(node.getRight(), data));
        }
        // After insert the new node, check and rebalance the current node if
        // necessary.
        switch (balanceNumber(node))
        {
        case 1:
            node = rotateLeft(node);
            break;
        case -1:
            node = rotateRight(node);
            break;
        default:
            return node;
        }
        return node;
    }

    private int balanceNumber(AVLNode<BinaryFileNode> node)
    {
        int L = depth(node.getLeft());
        int R = depth(node.getRight());
        if (L - R >= 2)
            return -1;
        else if (L - R <= -2)
            return 1;
        return 0;
    }

    private AVLNode<BinaryFileNode> rotateLeft(AVLNode<BinaryFileNode> node)
    {
        AVLNode<BinaryFileNode> q = node;
        AVLNode<BinaryFileNode> p = q.getRight();
        AVLNode<BinaryFileNode> c = q.getLeft();
        AVLNode<BinaryFileNode> a = p.getLeft();
        AVLNode<BinaryFileNode> b = p.getRight();
        q = new AVLNode<BinaryFileNode>(q.getData(), c, a);
        p = new AVLNode<BinaryFileNode>(p.getData(), q, b);
        return p;
    }

    private AVLNode<BinaryFileNode> rotateRight(AVLNode<BinaryFileNode> node)
    {
        AVLNode<BinaryFileNode> q = node;
        AVLNode<BinaryFileNode> p = q.getLeft();
        AVLNode<BinaryFileNode> c = q.getRight();
        AVLNode<BinaryFileNode> a = p.getLeft();
        AVLNode<BinaryFileNode> b = p.getRight();
        q = new AVLNode<BinaryFileNode>(q.getData(), b, c);
        p = new AVLNode<BinaryFileNode>(p.getData(), a, q);
        return p;
    }

    public boolean search(BinaryFileNode data)
    {
        AVLNode<BinaryFileNode> local = root;
        while (local != null)
        {
            if (local.getData().compareTo(data) == 0)
                return true;
            else if (local.getData().compareTo(data) > 0)
                local = local.getLeft();
            else
                local = local.getRight();
        }
        return false;
    }

    public String toString()
    {
        return root.toString();
    }

    public void PrintTree()
    {
        root.level = 0;
        Queue<AVLNode<BinaryFileNode>> queue = new LinkedList<AVLNode<BinaryFileNode>>();
        queue.add(root);
        while (!queue.isEmpty())
        {
            AVLNode<BinaryFileNode> node = queue.poll();
            System.out.println(node);
            int level = node.level;
            AVLNode<BinaryFileNode> left = node.getLeft();
            AVLNode<BinaryFileNode> right = node.getRight();
            if (left != null)
            {
                left.level = level + 1;
                queue.add(left);
            }
            if (right != null)
            {
                right.level = level + 1;
                queue.add(right);
            }
        }
    }
}