package org.demo.myapp.persistence.repositories;

import org.demo.myapp.persistence.jpa.entities.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

}
