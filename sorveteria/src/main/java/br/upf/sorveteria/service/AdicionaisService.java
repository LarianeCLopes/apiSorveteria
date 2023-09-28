package br.upf.sorveteria.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import br.upf.sorveteria.dto.AdicionaisDTO;
import br.upf.sorveteria.repository.AdicionaisRepository;


@Service
public class AdicionaisService {
	
	@Autowired
	private AdicionaisRepository adicionaisRepository;
	
	public AdicionaisDTO salvar (AdicionaisDTO dto) {
		return adicionaisRepository.save(dto);
	}
	
	public List<AdicionaisDTO> listarTodos(){
		return adicionaisRepository.findAll();
	}
	
	public Optional<AdicionaisDTO> buscarPorId(Long id){
		return adicionaisRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		adicionaisRepository.deleteById(id);
	}
}
