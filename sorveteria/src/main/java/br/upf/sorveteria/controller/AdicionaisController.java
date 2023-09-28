package br.upf.sorveteria.controller;

import java.util.List;

import org.modelmapper.ModelMapper;
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
import org.springframework.http.HttpStatus;

import br.upf.sorveteria.dto.AdicionaisDTO;
import br.upf.sorveteria.service.AdicionaisService;
import br.upf.sorveteria.utils.TokenJWT;

@RestController
@RequestMapping(value ="/sorveteria/adicionais")
public class AdicionaisController {

	@Autowired
	private AdicionaisService adicionaisService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping(value ="/inserir")
	@ResponseStatus(HttpStatus.CREATED)
	public AdicionaisDTO inserir(@RequestBody AdicionaisDTO AdicionaisDTO, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return adicionaisService.salvar(AdicionaisDTO);
	}
	
	@GetMapping(value ="/listarTodos")
	@ResponseStatus(HttpStatus.OK)
	public List<AdicionaisDTO> listarTodos(@RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return adicionaisService.listarTodos();
	}
	
	@GetMapping(value = "/buscarPorId")
	@ResponseStatus(HttpStatus.OK)
	public AdicionaisDTO buscarPorId(@RequestHeader (value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		return adicionaisService.buscarPorId(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adicional não encontrado"));
	}
	
	@DeleteMapping(value = "/delete")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerAdicional(@RequestHeader(value = "id")Long id, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		adicionaisService.buscarPorId(id)
			.map(adicionais -> {
				adicionaisService.removerPorId(adicionais.getId());
				return Void.TYPE;
			}).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adicional não encontrado"));
	}
	
	@PutMapping(value = "/atualizar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void atualizar(@RequestBody AdicionaisDTO AdicionaisDTO, @RequestHeader(value = "token")String token) {
		TokenJWT.validarToken(token);
		adicionaisService.buscarPorId(AdicionaisDTO.getId()).map(adicionaisBase -> {
			modelMapper.map(AdicionaisDTO, adicionaisBase);
			adicionaisService.salvar(adicionaisBase);
			return Void.TYPE;
		}).orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Adicional não encontrado"));
	}
}
