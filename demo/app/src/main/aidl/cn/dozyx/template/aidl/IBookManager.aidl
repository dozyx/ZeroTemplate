package cn.dozyx.template.aidl;

import cn.dozyx.template.aidl.Book;

interface IBookManager {

    List<Book> getBookList();
    void addBook(in Book book);
}
