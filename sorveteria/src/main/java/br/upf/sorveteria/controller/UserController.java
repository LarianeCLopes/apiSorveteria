package br.upf.sorveteria.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.upf.sorveteria.dto.UserDTO;
import br.upf.sorveteria.service.UserService;
import br.upf.sorveteria.utils.TokenJWT;

@RestController
@RequestMapping(value = "/sorveteria/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public UserDTO inserir(@RequestBody UserDTO user, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.salvar(user);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> listarTodos(@RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO buscaPorId(@RequestHeader(value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
	}

	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUusuario(@RequestHeader(value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(id)
			.map(usuario -> {
				userService.removerPorId(usuario.getId());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
	}
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody UserDTO user, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		userService.buscarPorId(user.getId())
		.map(usuarioBase -> {
			modelMapper.map(user, usuarioBase);
			userService.salvar(usuarioBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
	}
	
	@GetMapping(value = "/buscarPorEmail")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO findByEmail(@RequestHeader(value = "email")String email, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorEmail(email);
	}
	
	@GetMapping(value = "/buscarPorParteNome")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> buscarPorParteNome(@RequestHeader(value = "nome")String nome, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorParteNome(nome);
	}
	
	@GetMapping(value = "/buscarPorSenha")
	@ResponseStatus(HttpStatus.OK)
	public List<UserDTO> buscarPorSenha(@RequestHeader(value = "senha") String senha, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return userService.buscarPorSenha(senha);
	}
	
	@GetMapping(value = "/authorize")
	@ResponseStatus(HttpStatus.OK)
	public UserDTO authorize(@RequestHeader(value = "email")String email, @RequestHeader(value = "password")String password) {
		UserDTO UserDTO;
		if (email != null && !email.isEmpty() && password != null && !password.isEmpty()) {
			UserDTO = userService.buscarPorEmail(email);
			if (UserDTO.getId() != null) {
				if (UserDTO.getSenha().equals(password)) {
					UserDTO.setToken(TokenJWT.processarTokenJWT(email));
					return UserDTO;
				} else {
					return null;
				}	
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

}
