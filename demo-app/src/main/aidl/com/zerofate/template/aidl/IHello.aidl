// IHello.aidl
package com.zerofate.template.aidl;

// Declare any non-default types here with import statements

interface IHello {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    void hello(String msg);
    String getReply();
}
