package com.jim.mybatis.model.generators;

import com.jim.mybatis.model.dto.BookDTO;
import com.jim.mybatis.model.entities.Book;

/**
 * Created by jim on 2017/9/19.
 * This class is ...
 */
public class BookDTO2Book {
    public static Book g1(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setName(dto.getName());
        book.setIsbn(dto.getIsbn());
        return book;
    }
}
