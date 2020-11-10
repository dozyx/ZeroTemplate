package cn.dozyx.core.exception;

import junit.framework.TestCase;

import java.util.concurrent.CompletionException;

public class ExceptionUtilsTest extends TestCase {

    public void testGetCauseChain() {
        Exception e1 = new IllegalStateException("1 ex");
        Exception e2 = new IllegalArgumentException("2 ex", e1);
        Exception e3 = new RuntimeException("3 ex", e2);
        Exception e4 = new Exception("4 ex", e3);
        Exception e5 = new CompletionException("5 ex", e4);
        System.out.println(ExceptionUtils.getCauseChain(e3));
        System.out.println(ExceptionUtils.getCauseChain(e4));
        System.out.println(ExceptionUtils.getCauseChain(e5));
    }
}