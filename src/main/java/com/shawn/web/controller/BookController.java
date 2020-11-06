package com.shawn.web.controller;

import com.shawn.constant.PageConstant;
import com.shawn.constant.ResourceNameConstant;
import com.shawn.model.dto.PaginatedResult;
import com.shawn.model.entity.Book;
import com.shawn.service.BookService;
import com.shawn.util.PageUtil;
import com.shawn.web.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * @author Xiaoyue Xiao
 */
@RestController
@RequestMapping("/books")
public class BookController {

	private BookService bookService;

	@Autowired
	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping
	public ResponseEntity<?> getBooks(@RequestParam(value = "page", required = false) String pageString,
			@RequestParam(value = "per_page", required = false) String perPageString) {
		// Parse request parameters
		int page = PageUtil.parsePage(pageString, PageConstant.PAGE);
		int perPage = PageUtil.parsePerPage(perPageString, PageConstant.PER_PAGE);
		PaginatedResult paResult = new PaginatedResult();
		paResult.setData(bookService.getBooksByPage(page, perPage));
		paResult.setTotalPage(bookService.getTotalPage(perPage));
		return ResponseEntity.ok(paResult);
	}

	@GetMapping("/{bookId}")
	public ResponseEntity<?> getBookById(@PathVariable Long bookId) {
		ResourceNotFoundException reException = new ResourceNotFoundException();
		Optional<Book> book = bookService.getBookById(bookId);
		reException.setResourceName(ResourceNameConstant.BOOK);
		reException.setId(bookId);
		if (book.isPresent()) {
			return ResponseEntity.ok(book);
		} else {
			throw reException;
		}
	}

	@PostMapping
	public ResponseEntity<?> postBook(@RequestBody Book book) {
		bookService.saveBook(book);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(book.getId())
				.toUri();

		return ResponseEntity.created(location).body(book);

	}

	@PutMapping("/{bookId}")
	public ResponseEntity<?> putBook(@PathVariable Long bookId, @RequestBody Book book) {
		assertBookExist(bookId);
		book.setId(bookId);
		bookService.modifyBookOnNameById(book);

		return ResponseEntity.status(HttpStatus.OK).body(book);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
		assertBookExist(bookId);

		bookService.deleteBookById(bookId);

		return ResponseEntity.noContent().build();
	}

	/**********************************
	 * HELPER METHOD
	 **********************************/
	private void assertBookExist(Long bookId) {
		ResourceNotFoundException reException = new ResourceNotFoundException();
		reException.setResourceName(ResourceNameConstant.BOOK);
		reException.setId(bookId);
		Optional<Book> book = bookService.getBookById(bookId);
		if (!book.isPresent()) {
			throw reException;
		}
	}

}
