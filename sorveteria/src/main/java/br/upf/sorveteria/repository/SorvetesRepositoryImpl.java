package br.upf.sorveteria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class SorvetesRepositoryImpl implements SorvetesRepository {
	
	@PersistenceContext
	private EntityManager em;

}
