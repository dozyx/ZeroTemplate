package cn.dozyx.template.aidl;

import cn.dozyx.template.aidl.Book;

interface IBookManager {
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    List<Book> getBookList();
    void addBook(in Book book);
}
