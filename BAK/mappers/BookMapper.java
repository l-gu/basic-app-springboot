package org.demo.myapp.rest.mappers;

import org.demo.myapp.persistence.entities.Book;
import org.demo.myapp.rest.dto.BookRestDTO;

public class BookMapper {
	
	private static final BookMapper singleInstance = new BookMapper();
	
	public static BookMapper getInstance() {
		return singleInstance;
	}
	
	/**
	 * Private constructor (singleton : use getInstance)
	 */
	private BookMapper() {	
	}
	

	public Book dtoToEntity(BookRestDTO bookDTO) {
		Book book = new Book();
		book.setId(bookDTO.getId());
		book.setTitle(bookDTO.getTitle());
		book.setPrice(bookDTO.getPrice());
		return book;
	}

	public BookRestDTO entityToDto(Book book) {
		BookRestDTO bookDTO = new BookRestDTO();
		bookDTO.setId(book.getId());
		bookDTO.setTitle(book.getTitle());
		bookDTO.setPrice(book.getPrice());
		return bookDTO;
	}
}
