package com.example.islamiccompass;

public class Book {
    private String name;
    private String desc;
    private String author;

    public Book(String name, String desc, String author) {
        this.name = name;
        this.desc = desc;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
