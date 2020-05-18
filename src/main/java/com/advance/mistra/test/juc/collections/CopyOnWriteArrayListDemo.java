package com.advance.mistra.test.juc.collections;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Mistra
 * @ Version: 1.0
 * @ Time: 2020/4/6 12:56
 * @ Description: ArrayList在迭代过程中不能去修改，CopyOnWriteArrayList可以
 * @ Copyright (c) Mistra,All Rights Reserved.
 * @ Github: https://github.com/MistraR
 * @ CSDN: https://blog.csdn.net/axela30w
 */
public class CopyOnWriteArrayListDemo {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");
        // CopyOnWriteArrayList生成迭代器。迭代器里的数组就是生成迭代器时list的数组。
        // 后续的对list的修改时对这个数组的数据不产生影响。修改是在新的内存中新copy了一个同样的数组。
        // 修改完成后再把list数组指向这个新数组。
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            System.out.println("list is " + list);
            String next = iterator.next();
            System.out.println("next is " + next);
            if (next.equals("B")) {
                list.remove("D");
            }
            if (next.equals("C")) {
                list.add("E");
            }
        }
    }

}
