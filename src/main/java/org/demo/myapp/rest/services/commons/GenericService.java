package org.demo.myapp.rest.services.commons;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

public abstract class GenericService<ENTITY, DTO> {
	
	private final ModelMapper mapper = new ModelMapper();
	
	private final Class<ENTITY> entityClass ;
	private final Class<DTO> dtoClass ;
	
	
	public GenericService(Class<ENTITY> entityClass, Class<DTO> dtoClass) {
		super();
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
	}

	protected ENTITY dtoToEntity(DTO dto) {
		return mapper.map(dto, entityClass);
		//return BookMapper.getInstance().dtoToEntity(dto);
	}
	
	protected DTO entityToDto(ENTITY entity) {
		return mapper.map(entity, dtoClass);
		//return BookMapper.getInstance().entityToDto(entity);
	}
	
	protected DTO entityToDto(Optional<ENTITY> optionalEntity) {
		if ( optionalEntity.isPresent()) {
			return entityToDto(optionalEntity.get());
		}
		else {
			return null;
		}
	}
	
	protected List<DTO> entityListToDtoList(Iterable<ENTITY> entities) {
		List<DTO> dtoList = new LinkedList<>();
		if ( entities != null ) {
			for ( ENTITY e : entities ) {
				dtoList.add(entityToDto(e));
			}
		}
		return dtoList;
	}
}
