package org.demo.myapp.rest.service.impl;

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
	public void update(BookRestDTO dto) {
		logger.debug("update {}", dto);
		Book book = bookRepository.save(dtoToEntity(dto)); // TODO : update only
	}
	
	@Override
	public boolean deleteById(long id) {
		logger.debug("deleteById {}", id);
		bookRepository.deleteById(id); // TODO : return bool
		return true;
	}
	
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
