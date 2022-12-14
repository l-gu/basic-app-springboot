package org.demo.myapp.rest.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.demo.myapp.rest.dto.BookRestDTO;
import org.demo.myapp.rest.services.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/books", produces = MediaType.APPLICATION_JSON_VALUE)
public class BookRestController {

	private static final Logger logger = LoggerFactory.getLogger(BookRestController.class);
	
	private BookService bookService ; // injected
	
	/**
	 * Constructor usable for Dependency Injection
	 * @param bookService
	 */
	protected BookRestController(BookService bookService) {
		super();
		this.bookService = bookService;
	}
    
	/**
	 * Get ALL
	 * @return
	 */
	@GetMapping("")
	protected ResponseEntity<List<BookRestDTO>> findAll() {
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
    protected ResponseEntity<BookRestDTO> findById(@PathVariable long id) {
    	logger.debug("httpRestGetById({})", id);
    	BookRestDTO book = bookService.findById(id);
		if ( book != null ) {
			return ResponseEntity.ok(book); // 200 OK, found
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found
		}		
    }

    // HEAD method is implicit 
    // HEAD /xx      : 200 or 404  => same call as GET without id => findAll()    (without body in response)
    // HEAD /xx/{id} : 200 or 404  => same call as GET with id    => findById(id) (without body in response)
    
	/**
 	 * Create the given book if it doesn't exist 
	 * @param bookDTO
	 * @return 201 created or 409 conflict
	 */
	@PostMapping("")
	protected ResponseEntity<Void> create(@RequestBody BookRestDTO bookDTO) {
    	logger.debug("create({})", bookDTO);
		if ( bookService.create(bookDTO) ) {
			return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 created
		}
		else {
			return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 Conflict
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
	 * @return 200 updated or 404 not found
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
 	 * Partial update for the given book id if it exists 
	 * @param id
	 * @param bookDTO
	 * @return 200 updated or 404 not found
	 */
	@PatchMapping("/{id}")
	protected ResponseEntity<Void> partialUpdate(@PathVariable long id, @RequestBody BookRestDTO bookDTO) {
    	logger.debug("partialUpdate({},{})", id, bookDTO);
    	if ( bookService.partialUpdate(id, bookDTO) ) {
    		return ResponseEntity.ok().build(); // OK, found and updated
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
	protected ResponseEntity<Void> deleteById(@PathVariable long id) {
    	logger.debug("httpRestDelete({})", id);
		if ( bookService.deleteById(id) ) {
			return ResponseEntity.noContent().build(); // 204 No content = "deleted"
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not deleted"
		}
	}

	//-----------------------------------------------------------------------------------------
	// Specific "finders"
	//-----------------------------------------------------------------------------------------

	/**
	 * Find by title
	 * @param title
	 * @return
	 */
	@GetMapping(value = "", params = "title")
	protected ResponseEntity<List<BookRestDTO>> findByTitle(@RequestParam("title")  String title) {
    	logger.debug("findByTitle({})", title);
    	List<BookRestDTO> list = bookService.findByTitle(title);
    	return ResponseEntity.ok(list); // always 200
    }
    
	/**
	 * Find by price
	 * @param price
	 * @return
	 */
	@GetMapping(value = "", params = "price")
	protected ResponseEntity<List<BookRestDTO>> findByPrice(@RequestParam("price")  BigDecimal price) {
    	logger.debug("findByPrice({})", price);
    	List<BookRestDTO> list = bookService.findByPrice(price);
    	return ResponseEntity.ok(list); // always 200
    }
    
	/**
	 * Find by title and price
	 * @param title
	 * @param price
	 * @return
	 */
	@GetMapping(value = "", params={"title","price"} ) // params : for request mapping (avoid ambiguous mapping)
	protected ResponseEntity<List<BookRestDTO>> findByTitleAndPrice(@RequestParam("title") String title, @RequestParam("price") BigDecimal price) {
    	logger.debug("findByTitleAndPrice({},{})", title, price);
    	List<BookRestDTO> list = bookService.findByTitleAndPrice(title, price);
    	return ResponseEntity.ok(list); // always 200
    }
    
}
