package com.fenghuangzhujia.foundation.core.dto;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fenghuangzhujia.foundation.core.dto.adapter.DtoAdapter;
import com.fenghuangzhujia.foundation.core.entity.Identified;
import com.fenghuangzhujia.foundation.core.service.CrudService;

@Transactional
public abstract class DtoCrudService<D extends Identified<ID>, T extends Identified<ID>, I extends Identified<ID>, ID extends Serializable>
	implements CrudService<T, I, ID> {
	
	private CrudRepository<D, ID> repository;
	
	public CrudRepository<D, ID> getRepository() {
		return repository;
	}

	@Autowired
	public void setRepository(CrudRepository<D, ID> repository) {
		this.repository = repository;
	}

	@Autowired
	protected DtoAdapter<D, T, I> adapter;

	@Override
	public T add(I entity) {
		if(entity==null) return null;
		D d=adapter.convertToDo(entity);
		d=getRepository().save(d);
		T result=adapter.convertToDetailedDto(d);
		return result;
	}

	@Override
	public T update(I entity) {
		if(entity==null) return null;
		ID id=entity.getId();
		D d=getRepository().findOne(id);
		if(d==null) return null;
		d=adapter.update(entity, d);
		d=getRepository().save(d);
		T result=adapter.convertToDetailedDto(d);
		return result;
	}

	@Override
	public T findOne(ID id) {
		if(id==null)return null;
		D d=getRepository().findOne(id);
		if(d==null) return null;
		return adapter.convertToDetailedDto(d);
	}

	@Override
	public boolean exists(ID id) {
		return getRepository().exists(id);
	}

	@Override
	public Iterable<T> findAll() {
		Iterable<D> all=getRepository().findAll();
		if(all==null)return null;
		return adapter.convertDoList(all);
	}

	@Override
	public Iterable<T> findAll(Iterable<ID> ids) {
		Iterable<D> all=getRepository().findAll(ids);
		if(all==null)return null;
		return adapter.convertDoList(all);
	}

	@Override
	public long count() {
		return getRepository().count();
	}

	@Override
	public void delete(ID id) {
		if(id==null)return;
		getRepository().delete(id);
	}

	@Override
	public void delete(T entity) {	
		if(entity==null)return;
		getRepository().delete(entity.getId());
	}

	@Override
	public void delete(Iterable<? extends T> entities) {
		if(entities==null)return;
		for (T t : entities) {
			delete(t);
		}
	}

	@Override
	public void deleteAll(Iterable<ID> ids) {
		if(ids==null)return;
		for (ID id : ids) {
			delete(id);
		}
	}
}
