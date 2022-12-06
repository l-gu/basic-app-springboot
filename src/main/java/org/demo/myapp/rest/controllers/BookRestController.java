package org.demo.myapp.rest.controllers;

import java.util.List;

import org.demo.myapp.rest.dto.BookRestDTO;
import org.demo.myapp.rest.service.BookManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	protected BookRestController(BookManagementService bookService) {
		super();
		this.bookService = bookService;
	}
    
	/**
	 * Get ALL
	 * @return
	 */
	@GetMapping("")
	protected ResponseEntity<List<BookRestDTO>> httpRestGetAll() {
    	logger.debug("httpRestGetAll()");
    	List<BookRestDTO> list = bookService.findAll();
    	return ResponseEntity.ok(list);
    }
    
    /**
     * Get ONE identified by the given id
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    protected ResponseEntity<BookRestDTO> httpRestGetById(@PathVariable long id) {
    	logger.debug("httpRestGetById({})", id);
    	BookRestDTO book = bookService.findById(id);
		if ( book != null ) {
			return ResponseEntity.ok(book);
		}
		else {
			return ResponseEntity.notFound().build();
		}		
    }

	@PutMapping("/{id}")
	protected ResponseEntity<Void> httpRestPut(@PathVariable long id, @RequestBody BookRestDTO bookDTO) {
    	logger.debug("update({},{})", id, bookDTO);
		bookService.update(bookDTO);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	protected ResponseEntity<Void> httpRestDelete(@PathVariable long id) {
    	logger.debug("httpRestDelete({})", id);
		bookService.deleteById(id);
		return ResponseEntity.ok().build(); // TODO : http status ???
	}
}
