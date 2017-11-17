package com.zerofate.template.purejava;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Timon
 * @date 2017/11/17
 */

public class LambdaTest {
    public static void main(String[] args) {
        View.OnClickListener oneArguments = view -> System.out.println();
        BinaryOperator<Long> add = (x, y) -> x + y;
        String name = "1";
        name = "2";
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }
        list.stream().filter(n -> {
            System.out.println(n);
            return n / 2 == 0;
        });
        List<String> collected = Stream.of("a","b","c").collect(Collectors.toList());
        assert(Arrays.asList("a","b","c").equals(collected));
    }
}
