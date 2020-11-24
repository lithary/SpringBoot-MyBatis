package com.shawn.service.impl;

import com.shawn.model.entity.Book;
import com.shawn.model.entity.BookWithBookStore;
import com.shawn.repository.mybatis.BookMapper;
import com.shawn.service.BookInterface;
import com.shawn.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Xiaoyue Xiao
 */
@Named("bookService")
public class BookInterfaceImpl implements BookInterface {
    @Inject
    private BookMapper bookRepository;

    @Override
    public Optional<Book> getBookById(Long id) {
        return Optional.ofNullable(bookRepository.selectBookById(id));
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.selectBooksByAuthor(author);
    }

    @Override
    public List<Book> getBooksByPage(Integer page, Integer perPage) {
        Integer offset = PageUtil.calculateOffset(page, perPage);
        return bookRepository.selectBooksByPage(offset, perPage);
    }

    @Override
    public List<String> getAllBookNames() {
        return bookRepository
                .selectAllBooks()
                .stream()
                .map(Book::getName)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookWithBookStore> getBookWithBookStoreById(Long id) {
        return Optional.ofNullable(bookRepository.selectBookWithBookStoreById(id));
    }

    @Override
    public Integer getTotalPage(Integer perPage) {
        return PageUtil.calculateTotalPage(bookRepository.selectCount(), perPage);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public boolean saveBook(Book book) {
        return bookRepository.insertBook(book) > 0;
    }

    @Override
    @Transactional
    public boolean modifyBookOnNameById(Book book) {
        return bookRepository.updateBookOnNameById(book) > 0;
    }

    @Override
    @Transactional
    public boolean deleteBookById(Long id) {
        return bookRepository.deleteBookById(id) > 0;
    }

    @Override
    public void afterSubmit(Book in) {
        Book book = (Book) in;
        book.setAuthor("lizhixiong" + (Math.random() * 100));
        saveBook(book);
        System.out.println("----------  after -----------");
    }

    @Override
    public void submitTask(Book in) {
        saveBook(in);
    }
}
