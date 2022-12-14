package org.demo.myapp.persistence.repositories;

import java.math.BigDecimal;
import java.util.List;

import org.demo.myapp.persistence.entities.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

	List<Book> findByTitle(String title);
	
	List<Book> findByTitleStartingWith(String titlePart);
	
	List<Book> findByTitleContaining(String titlePart);

	List<Book> findByPrice(BigDecimal price);

	List<Book> findByTitleContainingAndPrice(String titlePart, BigDecimal price);
}
