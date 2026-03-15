package com.example.healthydoggy;

public class test1 {
    int a=0;
    String man="mimade";

    public test1() {
    }

    public test1(int a, String man) {
        this.a = a;
        this.man = man;
    }

    /**
     * 获取
     * @return a
     */
    public int getA() {
        return a;
    }

    /**
     * 设置
     * @param a
     */
    public void setA(int a) {
        this.a = a;
    }

    /**
     * 获取
     * @return man
     */
    public String getMan() {
        return man;
    }

    /**
     * 设置
     * @param man
     */
    public void setMan(String man) {
        this.man = man;
    }

    public String toString() {
        return "test1{a = " + a + ", man = " + man + "}";
    }
}
