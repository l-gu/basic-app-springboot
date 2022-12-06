package org.demo.myapp.rest.service;

import java.util.List;

import org.demo.myapp.rest.dto.BookRestDTO;

/**
 *
 */
public interface BookManagementService {

	BookRestDTO findById(long id);
	
	List<BookRestDTO> findAll();
	
	void update(BookRestDTO book);

	boolean deleteById(long id);
	
}
