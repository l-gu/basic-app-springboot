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
    	return ResponseEntity.ok(list); // always 200
    }
    
    /**
     * Get ONE identified by the given id
     * @param id
     * @return 200 or 404 
     */
    @GetMapping("/{id}")
    protected ResponseEntity<BookRestDTO> httpRestGetById(@PathVariable long id) {
    	logger.debug("httpRestGetById({})", id);
    	BookRestDTO book = bookService.findById(id);
		if ( book != null ) {
			return ResponseEntity.ok(book); // 200 OK, found
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found
		}		
    }

	/**
	 * Update or create the given book 
	 * @param id
	 * @param bookDTO
	 * @return 200 updated or created
	 */
	@PutMapping("/{id}")
	protected ResponseEntity<Void> save(@PathVariable long id, @RequestBody BookRestDTO bookDTO) {
    	logger.debug("save({},{})", id, bookDTO);
		bookService.save(id, bookDTO);
		return ResponseEntity.ok().build(); // OK, updated or created
	}

	/**
 	 * Update the given book if it exists 
	 * @param bookDTO
	 * @return 200 updated, 404 not found
	 */
	@PutMapping("")
	protected ResponseEntity<Void> update(@RequestBody BookRestDTO bookDTO) {
    	logger.debug("update({})", bookDTO);
		if ( bookService.update(bookDTO) ) {
			return ResponseEntity.ok().build(); // 200 OK, found and updated
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not updated"
		}
	}

	/**
	 * Delete a book by its id 
	 * @param id
	 * @return 204 deleted or 404 not found
	 */
	@DeleteMapping("/{id}")
	protected ResponseEntity<Void> httpRestDelete(@PathVariable long id) {
    	logger.debug("httpRestDelete({})", id);
		if ( bookService.deleteById(id) ) {
			return ResponseEntity.noContent().build(); // 204 No content = "deleted"
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not deleted"
		}
	}
}
