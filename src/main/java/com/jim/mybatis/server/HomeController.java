package com.jim.mybatis.server;

import com.jim.mybatis.model.IBook;
import com.jim.mybatis.model.dto.BookDTO;
import com.jim.mybatis.model.entities.Book;
import com.jim.mybatis.model.generators.BookDTO2Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by jim on 2017/9/18.
 * 根据服务器的返回结果生成统一的API
 */
@Controller
@RequestMapping(value = "/api/book")
public class HomeController {

    @Autowired
    private IBook iBook;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Book> books() {
        return iBook.books();
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.GET)
    @ResponseBody
    public Book getBookById(@PathVariable("bookId") Long bookId) {
        return iBook.getBookById(bookId);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void insert(@Validated @RequestBody final List<BookDTO> bookDTOs, Errors errors) {
        iBook.insert(bookDTOs);
    }


    @RequestMapping(method = RequestMethod.PUT)
    public void update(@Validated @RequestBody final BookDTO bookDTO, Errors errors) {
        Book book = BookDTO2Book.g1(bookDTO);
        iBook.update(book);
    }

    @RequestMapping(value = "/{bookId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("bookId") Long bookId) {
        iBook.delete(bookId);
    }

}
