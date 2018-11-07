package com.zerofate.template.justfortest.purchase;

/**
 * @author dozeboy
 * @date 2017/11/1.
 */

public class Goods {
    private String name;
    private float price;
    private int count;

    public Goods(String name, float price, int count) {
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public void plusCount(int increase) {
        count = count + increase;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int result = 17;
        return  31 * result + name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Goods)) {
            return false;
        }
        final Goods other = (Goods) obj;
        if (name.equals(other.name)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "name: " + name + " & price: " + price + " & count: " + count;
    }
}
