package org.demo.myapp.rest.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.demo.myapp.persistence.entities.Book;
import org.demo.myapp.persistence.repositories.BookRepository;
import org.demo.myapp.rest.dto.BookRestDTO;
import org.demo.myapp.rest.services.commons.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * REST service for entity "Book" <br>
 * 
 * This service provides all the necessary operations required by the REST controller<br>
 * 
 * @author 
 *
 */
@Service
public class BookService extends GenericService<Book, BookRestDTO> {

	private static final Logger logger = LoggerFactory.getLogger(BookService.class);

	private final BookRepository bookRepository; // injected by constructor

	/**
	 * Constructor usable for Dependency Injection
	 * 
	 * @param bookRepository the repository to be injected
	 */
	public BookService(BookRepository bookRepository) {
		super(Book.class, BookRestDTO.class);
		this.bookRepository = bookRepository;
	}

	/**
	 * Finds all occurrences of the entity
	 * @return
	 */
	public List<BookRestDTO> findAll() {
		logger.debug("findAll()");
		Iterable<Book> all = bookRepository.findAll();
		return entityListToDtoList(all);
	}

	/**
	 * Finds the entity identified by the given PK
	 * @param id
	 * @return the entity or null if not found
	 */
	public BookRestDTO findById(long id) {
		logger.debug("findBook({})", id);
		Optional<Book> optionalBook = bookRepository.findById(id);
		return entityToDto(optionalBook);
	}

	/**
	 * Saves the given entity with the given PK (updated if exists else created)
	 * @param id
	 * @param dto 
	 */
	public void save(long id, BookRestDTO dto) {
		logger.debug("save({},{})", id, dto);
		dto.setId(id); 
		bookRepository.save(dtoToEntity(dto));
	}

	/**
	 * Updates the given entity if it exists
	 * @param dto
	 * @return true if updated, false if not found
	 */
	public boolean update(BookRestDTO dto) {
		logger.debug("update({})", dto);
		if (bookRepository.existsById(dto.getId())) {
			bookRepository.save(dtoToEntity(dto));
			return true; // find and updated
		} else {
			return false; // not found (not updated)
		}
	}

	/**
	 * Updates partially the given entity if it exists
	 * @param id
	 * @param dto
	 * @return true if updated, false if not found
	 */
	public boolean partialUpdate(long id, BookRestDTO dto) {
		logger.debug("partialUpdate({}, {})", id, dto);
		Optional<Book> optionalBook = bookRepository.findById(id);
		if (optionalBook.isPresent()) {
			Book book = optionalBook.get();
			// implement here the partial update
			// book.setXxx(dto.getXxx());
			bookRepository.save(book);
			return true; // find and updated
		} else {
			return false; // not found (not updated)
		}
	}

	/**
	 * Creates the given entity
	 * @param dto
	 * @return true if created, false if already exists
	 */
	public boolean create(BookRestDTO dto) {
		logger.debug("create({})", dto);
		if (bookRepository.existsById(dto.getId())) {
			return false; // already exists, not created
		} else {
			bookRepository.save(dtoToEntity(dto));
			return true; // created
		}
	}

	/**
	 * Deletes an entity by its PK
	 * @param id
	 * @return true if deleted, false if not found
	 */
	public boolean deleteById(long id) {
		logger.debug("deleteById({})", id);
		if (bookRepository.existsById(id)) {
			bookRepository.deleteById(id);
			return true; // find and deleted
		} else {
			return false; // not found (not deleted)
		}
	}

	// -----------------------------------------------------------------------------------------
	// Specific "finders"
	// -----------------------------------------------------------------------------------------

	public List<BookRestDTO> findByTitle(String title) {
		logger.debug("findByTitle({})", title);
		// List<Book> list = bookRepository.findByTitle(title);
		List<Book> list = bookRepository.findByTitleContaining(title);
		return entityListToDtoList(list);
	}

	public List<BookRestDTO> findByPrice(BigDecimal price) {
		logger.debug("findByPrice({})", price);
		// List<Book> list = bookRepository.findByTitle(title);
		List<Book> list = bookRepository.findByPrice(price);
		return entityListToDtoList(list);
	}

	public List<BookRestDTO> findByTitleAndPrice(String title, BigDecimal price) {
		logger.debug("findByTitleAndPrice({}, {})", title, price);
		List<Book> list = bookRepository.findByTitleContainingAndPrice(title, price);
		return entityListToDtoList(list);
	}

}
