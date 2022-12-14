package org.demo.myapp.rest.service.impl;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.demo.myapp.persistence.jpa.entities.Book;
import org.demo.myapp.persistence.repositories.BookRepository;
import org.demo.myapp.rest.dto.BookRestDTO;
import org.demo.myapp.rest.mappers.BookMapper;
import org.demo.myapp.rest.service.BookManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookManagementServiceImpl implements BookManagementService {

	// Standard logger (not static => so it can be reused without having to change the name of the class)
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final BookRepository bookRepository; // injected by constructor

	/**
	 * Constructor usable for Dependency Injection
	 * @param bookRepository
	 */
	public BookManagementServiceImpl(BookRepository bookRepository) {
		super();
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookRestDTO> findAll() {
		logger.debug("findAll");
		Iterable<Book> all = bookRepository.findAll();
		return entityListToDtoList(all);
	}

	@Override
	public BookRestDTO findById(long id) {
		logger.debug("findBook {}", id);
		Optional<Book> optionalBook = bookRepository.findById(id);
		return entityToDto(optionalBook);
	}

	@Override
	public void save(long id, BookRestDTO dto) {
		logger.debug("save {} : {}", id, dto);
		dto.setId(id);
		bookRepository.save(dtoToEntity(dto)); 
	}
	
	@Override
	public boolean update(BookRestDTO dto) {
		logger.debug("update {}", dto);
		if ( bookRepository.existsById(dto.getId()) ) {
			bookRepository.save(dtoToEntity(dto)); 
			return true; // find and updated
		}
		else {
			return false; // not found (not updated)
		}
	}
	
	@Override
	public boolean partialUpdate(long id, BookRestDTO dto) {
		logger.debug("partialUpdate {} : {}", id, dto);
		Optional<Book> optionalBook = bookRepository.findById(id);
		if ( optionalBook.isPresent() ) {
			Book book = optionalBook.get();
			// implement here the partial update
			bookRepository.save(book);
			return true; // find and updated
		}
		else {
			return false; // not found (not updated)
		}
	}

	@Override
	public boolean create(BookRestDTO dto) {
		logger.debug("create {}", dto);
		if ( bookRepository.existsById(dto.getId()) ) {
			return false; // already exists, not created
		}
		else {
			bookRepository.save(dtoToEntity(dto)); 
			return true; // created
		}
	}
	
	@Override
	public boolean deleteById(long id) {
		logger.debug("deleteById {}", id);
		if ( bookRepository.existsById(id) ) {
			bookRepository.deleteById(id); 
			return true; // find and deleted
		}
		else {
			return false; // not found (not deleted)
		}
	}
	

	//-----------------------------------------------------------------------------------------
	// Specific "finders"
	//-----------------------------------------------------------------------------------------
	
	@Override
	public List<BookRestDTO> findByTitle(String title) {
		logger.debug("findByTitle {}", title);
		//List<Book> list = bookRepository.findByTitle(title);
		List<Book> list = bookRepository.findByTitleContaining(title);
		return entityListToDtoList(list);
	}

	@Override
	public List<BookRestDTO> findByPrice(BigDecimal price) {
		logger.debug("findByPrice {}", price);
		//List<Book> list = bookRepository.findByTitle(title);
		List<Book> list = bookRepository.findByPrice(price);
		return entityListToDtoList(list);
	}

	@Override
	public List<BookRestDTO> findByTitleAndPrice(String title, BigDecimal price) {
		logger.debug("findByTitleAndPrice {} {}", title, price);
		List<Book> list = bookRepository.findByTitleContainingAndPrice(title, price);
		return entityListToDtoList(list);
	}


	//-----------------------------------------------------------------------------------------
	//-----------------------------------------------------------------------------------------
	//--------------- Generic => in super class
	protected Book dtoToEntity(BookRestDTO dto) {
		return BookMapper.getInstance().dtoToEntity(dto);
	}
	protected BookRestDTO entityToDto(Book entity) {
		return BookMapper.getInstance().entityToDto(entity);
	}
	protected BookRestDTO entityToDto(Optional<Book> optionalEntity) {
		if ( optionalEntity.isPresent()) {
			return entityToDto(optionalEntity.get());
		}
		else {
			return null;
		}
	}
	protected List<BookRestDTO> entityListToDtoList(Iterable<Book> entities) {
		List<BookRestDTO> dtoList = new LinkedList<>();
		if ( entities != null ) {
			for ( Book e : entities ) {
				dtoList.add(entityToDto(e));
			}
		}
		return dtoList;
	}
}
