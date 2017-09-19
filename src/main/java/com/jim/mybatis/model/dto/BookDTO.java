package com.jim.mybatis.model.dto;

/**
 * Created by jim on 2017/9/19.
 * This class is ...
 */
public class BookDTO {
    private Long id;
    private String name;
    private String isbn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
