package org.demo.myapp.rest.service;

import org.demo.myapp.rest.dto.BookRestDTO;

/**
 *
 */
public interface BookManagementService {

	public BookRestDTO findBook(long id);

	public void save(BookRestDTO book);

}
