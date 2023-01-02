package org.demo.myapp.persistence.repositories;

import org.demo.myapp.persistence.entities.Ticket;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Spring Data JPA repository for entity "xx" <br> 
 * 
 * This repository extends PagingAndSortingRepository interface <br>
 * so it provides by default all the basic CRUD operations : <br>
 *   findById, findAll, save, delete, etc <br> 
 * with pagination and sorting : <br>
 *   findAll(Pageable), findAll(Sort)<br>
 * 
 * This repository can be extended by adding specific "finders" methods<br>
 * To do so, see the "predicates conventions" for "derived query methods" in Spring Data documentation
 * 
 * @author xx
 *
 */
public interface TicketRepository extends PagingAndSortingRepository<Ticket, Long> {

}
