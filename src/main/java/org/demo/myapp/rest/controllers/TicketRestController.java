package org.demo.myapp.rest.controllers;

import java.util.List;

import org.demo.myapp.rest.dto.TicketRestDTO;
import org.demo.myapp.rest.services.TicketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tickets", produces = MediaType.APPLICATION_JSON_VALUE)
public class TicketRestController {

	private static final Logger logger = LoggerFactory.getLogger(TicketRestController.class);
	
	private TicketService service ; // injected
	
	/**
	 * Constructor usable for Dependency Injection
	 * @param service
	 */
	protected TicketRestController(TicketService service) {
		super();
		this.service = service;
	}
    
	/**
	 * Get ALL
	 * @return
	 */
	@GetMapping("")
	protected ResponseEntity<List<TicketRestDTO>> findAll() {
    	logger.debug("httpRestGetAll()");
    	List<TicketRestDTO> list = service.findAll();
    	return ResponseEntity.ok(list); // always 200
    }
    
    /**
     * Get ONE identified by the given id
     * @param id
     * @return 200 or 404 
     */
    @GetMapping("/{id}")
    protected ResponseEntity<TicketRestDTO> findById(@PathVariable long id) {
    	logger.debug("httpRestGetById({})", id);
    	TicketRestDTO book = service.findById(id);
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
	protected ResponseEntity<Void> create(@RequestBody TicketRestDTO bookDTO) {
    	logger.debug("create({})", bookDTO);
		if ( service.create(bookDTO) ) {
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
	protected ResponseEntity<Void> save(@PathVariable long id, @RequestBody TicketRestDTO bookDTO) {
    	logger.debug("save({},{})", id, bookDTO);
		service.save(id, bookDTO);
		return ResponseEntity.ok().build(); // OK, updated or created
	}

	/**
 	 * Update the given book if it exists 
	 * @param bookDTO
	 * @return 200 updated or 404 not found
	 */
	@PutMapping("")
	protected ResponseEntity<Void> update(@RequestBody TicketRestDTO bookDTO) {
    	logger.debug("update({})", bookDTO);
		if ( service.update(bookDTO) ) {
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
	protected ResponseEntity<Void> deleteById(@PathVariable long id) {
    	logger.debug("httpRestDelete({})", id);
		if ( service.deleteById(id) ) {
			return ResponseEntity.noContent().build(); // 204 No content = "deleted"
		}
		else {
			return ResponseEntity.notFound().build(); // 404 Not found = "not deleted"
		}
	}
}
