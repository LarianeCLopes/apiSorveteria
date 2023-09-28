package br.upf.sorveteria.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.upf.sorveteria.dto.SorvetesDTO;

public interface SorvetesRepository extends JpaRepository<SorvetesDTO, Long>{
	
	public List<SorvetesDTO> findByNomeContaining(String name);
	
	@Query(nativeQuery = true, value="select s.* from tb_sorvetes u inner join tb_adicionais a on a.id = s.adic_id" 
			+ "where s.adic_id = :adicId order by s.sorv_nome asc")
	public List<SorvetesDTO> findByPorAdicId(@Param("adicId") Long adicId);

}
