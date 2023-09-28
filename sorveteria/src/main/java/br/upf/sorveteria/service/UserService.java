package br.upf.sorveteria.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.upf.sorveteria.dto.UserDTO;
import br.upf.sorveteria.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDTO salvar(UserDTO dto) {
		return userRepository.save(dto);
	}
	
	public List<UserDTO> listarTodos() {
		return userRepository.findAll();
	}
	
	public Optional<UserDTO> buscarPorId(Long id){
		return userRepository.findById(id);
	}
	
	public void removerPorId(Long id) {
		userRepository.deleteById(id);
	}
	
	public List<UserDTO> buscarPorParteNome(String nome){
		return userRepository.findByNomeContaining(nome);
	}
	
	public UserDTO buscarPorEmail (String email) {
		return userRepository.findByEmail(email);
	}
	
	public List<UserDTO> buscarPorSenha (String senha) {
		return userRepository.findByPorSenha(senha);
	}
	
}
