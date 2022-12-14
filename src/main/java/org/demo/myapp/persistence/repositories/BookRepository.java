package org.demo.myapp.persistence.repositories;

import java.util.List;

import org.demo.myapp.persistence.jpa.entities.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

	List<Book> findByTitle(String title);
	
	List<Book> findByTitleStartingWith(String titlePart);
	
}
