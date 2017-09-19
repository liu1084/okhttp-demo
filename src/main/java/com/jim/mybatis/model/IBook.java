package com.jim.mybatis.model;

import com.jim.mybatis.model.dto.BookDTO;
import com.jim.mybatis.model.entities.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by jim on 2017/9/19.
 * This class is ...
 */

@Mapper
public interface IBook {

    List<Book> books();

    Book getBookById(Long bookId);

    void insert(List<BookDTO> bookDTOS);

    int update(Book book);

    void delete(Long bookId);
}
