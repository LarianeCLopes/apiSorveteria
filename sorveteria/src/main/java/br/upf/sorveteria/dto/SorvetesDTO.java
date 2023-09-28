package br.upf.sorveteria.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode

@Entity
@Table (name = "tb_sorvetes")
public class SorvetesDTO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "sorv_nome", nullable = false)
	private String nome;
	
	@Column (name = "sorv_sabor", nullable = false)
	private String sabor;
	
	@ManyToOne
	@JoinColumn (name = "adic_id")
	private AdicionaisDTO adicionais;
	
	@Transient
	private String token;
}
