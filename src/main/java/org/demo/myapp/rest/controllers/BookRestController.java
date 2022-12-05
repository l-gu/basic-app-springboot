package org.demo.myapp.rest.controllers;

import org.demo.myapp.rest.dto.BookRestDTO;
import org.demo.myapp.rest.service.BookManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

	private static final Logger logger = LoggerFactory.getLogger(BookRestController.class);
	
	private BookManagementService bookService ; // injected
	
	/**
	 * Constructor usable for Dependency Injection
	 * @param bookService
	 */
	public BookRestController(BookManagementService bookService) {
		super();
		this.bookService = bookService;
	}

    @GetMapping("/{id}")
    ResponseEntity<BookRestDTO>  httpRestGetBookById(@PathVariable long id) {
    	logger.debug("httpRestGetBookById({})", id);
    	BookRestDTO book = bookService.findBook(id);
		if ( book != null ) {
			return ResponseEntity.ok(book);
		}
		else {
			return ResponseEntity.notFound().build();
		}		
    }

	@PutMapping("/{id}")
	protected ResponseEntity<Void> updateBook(@PathVariable long id, @RequestBody BookRestDTO bookDTO) {
    	logger.debug("updateBook({},{})", id, bookDTO);
		bookService.save(bookDTO);
		return ResponseEntity.ok().build();
	}
}
