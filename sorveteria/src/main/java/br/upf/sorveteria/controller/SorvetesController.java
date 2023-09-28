package br.upf.sorveteria.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
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

import br.upf.sorveteria.dto.SorvetesDTO;
import br.upf.sorveteria.service.SorvetesService;
import br.upf.sorveteria.utils.TokenJWT;

@RestController
@RequestMapping(value = "/sorveteria/sorvetes")
public class SorvetesController {
	@Autowired
	private SorvetesService sorvetesService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value = "/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public SorvetesDTO inserir (@RequestBody SorvetesDTO SorvetesDTO, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return sorvetesService.salvar(SorvetesDTO);
	}
	
	@GetMapping(value = "/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<SorvetesDTO> listarTodos(@RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return sorvetesService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public SorvetesDTO buscarPorId(@RequestHeader(value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return sorvetesService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sorvete não encontrado"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerUsuario(@RequestHeader(value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		sorvetesService.buscarPorId(id)
			.map(sorvetes -> {
				sorvetesService.removerPorId(sorvetes.getId());
				return Void.TYPE;
			}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
	
	}
		
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody SorvetesDTO SorvetesDTO, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		sorvetesService.buscarPorId(SorvetesDTO.getId()).map(sorvetesBase -> {
			modelMapper.map(SorvetesDTO, sorvetesBase);
			sorvetesService.salvar(sorvetesBase);
			return Void.TYPE;
		}).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não encontrado"));
	}
	
	@GetMapping(value = "/buscarPorAdicId")
	@ResponseStatus(HttpStatus.OK)
	public List<SorvetesDTO> buscarPorAdicId(@RequestHeader(value = "adicId")Long adicId, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return sorvetesService.buscarPorAdicId(adicId);
	}
	
}
