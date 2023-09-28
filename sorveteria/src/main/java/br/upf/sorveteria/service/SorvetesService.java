package br.upf.sorveteria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.upf.sorveteria.dto.SorvetesDTO;
import br.upf.sorveteria.repository.SorvetesRepository;

@Service
public class SorvetesService {
	
	@Autowired
	private SorvetesRepository sorvetesRepository;
	
	public SorvetesDTO salvar (SorvetesDTO dto) {
		return sorvetesRepository.save(dto);
	}
	
	public List<SorvetesDTO> listarTodos(){
		return sorvetesRepository.findAll();
	}
	
	public Optional<SorvetesDTO> buscarPorId(Long id){
		return sorvetesRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		sorvetesRepository.deleteById(id);
	}
	
	public List<SorvetesDTO> buscarPorAdicId(Long adicId) {
		return sorvetesRepository.findByPorAdicId(adicId);
	}
}
