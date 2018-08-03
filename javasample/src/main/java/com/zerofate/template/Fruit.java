package com.zerofate.template;

/**
 * @author timon
 * @date 2018/8/3
 */
public enum Fruit {
    /**
     */
    APPLE("苹果", "1");

    public static final String TAG = "Fruit";
    private String name;
    private String price;

    static {
        System.out.println("static enum");
    }
    Fruit(String name, String price) {
        System.out.println("constructor enum");
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
}
