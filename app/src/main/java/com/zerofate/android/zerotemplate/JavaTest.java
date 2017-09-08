package com.zerofate.android.zerotemplate;

import android.util.LruCache;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by zero on 2017/8/15.
 */

public class JavaTest {
    private static int max = 0;

    public static void main(String[] args) {
        LruCache<String,String> cache = new LruCache<>(3);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.put("4","4");

        Scanner scanner = new Scanner(System.in);

//        cache.put("1","5");

//        System.out.println(cache.get("1"));
//        System.out.println(cache.get("2"));
//        System.out.println(cache.get("3"));
//        System.out.println(cache.get("4"));
    }

    private int deepNormal(Tree tree) {
        int deep = 0;
        ArrayList<Tree> currents = new ArrayList<>();
        ArrayList<Tree> nexts = null;
        if (tree != null) {
            currents.add(tree);
        }
        while (currents.size() > 0) {
            deep++;
            nexts = new ArrayList<>();
            for (Tree current : currents) {
                if (current.left != null) {
                    nexts.add(current.left);
                }
                if (current.right != null) {
                    nexts.add(current.right);
                }
            }
            currents = nexts;
        }
        return deep;
    }


    private void deepByRecur(Tree tree, int current) {
        if (tree.left != null || tree.right != null) {
            current++;
            if (max < current) {
                max = current;
            }
        }
        if (tree.left != null) {
            deepByRecur(tree.left, current);
        }
        if (tree.right != null) {
            deepByRecur(tree.right, current);
        }
    }

    private static class Tree {
        Tree left, right;
    }

}
