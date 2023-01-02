package org.demo.myapp.rest.services;

import java.util.List;
import java.util.Optional;

import org.demo.myapp.persistence.entities.Ticket;
import org.demo.myapp.persistence.repositories.TicketRepository;
import org.demo.myapp.rest.dto.TicketRestDTO;
import org.demo.myapp.rest.services.commons.GenericService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * REST service for entity "Ticket" <br>
 * 
 * This service provides all the necessary operations required by the REST controller<br>
 * 
 * @author 
 *
 */
@Service
public class TicketService extends GenericService<Ticket, TicketRestDTO> {

	private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

	private final TicketRepository repository; // injected by constructor

	/**
	 * Constructor usable for Dependency Injection
	 * 
	 * @param bookRepository the repository to be injected
	 */
	public TicketService(TicketRepository bookRepository) {
		super(Ticket.class, TicketRestDTO.class);
		this.repository = bookRepository;
	}

	/**
	 * Finds all occurrences of the entity
	 * @return
	 */
	public List<TicketRestDTO> findAll() {
		logger.debug("findAll()");
		Iterable<Ticket> all = repository.findAll();
		return entityListToDtoList(all);
	}

	/**
	 * Finds the entity identified by the given PK
	 * @param id
	 * @return the entity or null if not found
	 */
	public TicketRestDTO findById(long id) {
		logger.debug("findTicket({})", id);
		Optional<Ticket> optionalTicket = repository.findById(id);
		return entityToDto(optionalTicket);
	}

	/**
	 * Saves the given entity with the given PK (updated if exists else created)
	 * @param id
	 * @param dto 
	 */
	public void save(long id, TicketRestDTO dto) {
		logger.debug("save({},{})", id, dto);
		dto.setId(id); 
		repository.save(dtoToEntity(dto));
	}

	/**
	 * Updates the given entity if it exists
	 * @param dto
	 * @return true if updated, false if not found
	 */
	public boolean update(TicketRestDTO dto) {
		logger.debug("update({})", dto);
		if (repository.existsById(dto.getId())) {
			repository.save(dtoToEntity(dto));
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
	public boolean partialUpdate(long id, TicketRestDTO dto) {
		logger.debug("partialUpdate({}, {})", id, dto);
		Optional<Ticket> optionalTicket = repository.findById(id);
		if (optionalTicket.isPresent()) {
			Ticket book = optionalTicket.get();
			// implement here the partial update
			// book.setXxx(dto.getXxx());
			repository.save(book);
			return true; // find and updated
		} else {
			return false; // not found (not updated)
		}
	}

	/**
	 * Creates the given entity : auto-generated PK => always created
	 * @param dto
	 * @return true if created, false if already exists
	 */
	public boolean create(TicketRestDTO dto) {
		logger.debug("create({})", dto);
		repository.save(dtoToEntity(dto));
		return true; // created
	}

	/**
	 * Deletes an entity by its PK
	 * @param id
	 * @return true if deleted, false if not found
	 */
	public boolean deleteById(long id) {
		logger.debug("deleteById({})", id);
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true; // find and deleted
		} else {
			return false; // not found (not deleted)
		}
	}

}
