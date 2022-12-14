package org.demo.myapp.rest.service;

import java.math.BigDecimal;
import java.util.List;

import org.demo.myapp.rest.dto.BookRestDTO;

/**
 *
 */
public interface BookManagementService {

	/**
	 * Find a book by its id (PK)
	 * @param id
	 * @return
	 */
	BookRestDTO findById(long id);
	
	/**
	 * Find all books 
	 * @return 
	 */
	List<BookRestDTO> findAll();
	
	/**
	 * Update or create the given book
	 * @param id
	 * @param book
	 */
	void save(long id, BookRestDTO book);
	
	/**
	 * Update the given book if it exists 
	 * @param dto
	 * @return true if updated, false if not found
	 */
	boolean update(BookRestDTO dto) ;
	
	/**
	 * Partial update 
	 * @param id
	 * @param book
	 * @return true if updated, false if not found
	 */
	boolean partialUpdate(long id, BookRestDTO book);
	
	/**
	 * Create the given book if it doesn't exist 
	 * @param dto
	 * @return true if created, false if already exist
	 */
	boolean create(BookRestDTO dto) ;
	
	/**
	 * Delete the given book 
	 * @param id
	 * @return true if deleted, false if not found
	 */
	boolean deleteById(long id);
	
	// Specific finders
	
	List<BookRestDTO> findByTitle(String title);
	
	List<BookRestDTO> findByPrice(BigDecimal price);
	
	List<BookRestDTO> findByTitleAndPrice(String title, BigDecimal price);
}
