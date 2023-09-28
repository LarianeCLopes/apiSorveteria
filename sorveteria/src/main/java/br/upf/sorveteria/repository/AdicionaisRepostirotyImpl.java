package br.upf.sorveteria.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AdicionaisRepostirotyImpl implements AdicionaisRepository {
	
	@PersistenceContext
	private EntityManager em;

}
