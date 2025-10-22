package com.api.payment.domain;

import com.api.payment.enums.StatusCobranca;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name="cobranca")
@SequenceGenerator(name="cobranca_id_seq", sequenceName ="cobranca_id_seq", initialValue = 1, allocationSize = 1)
public class Cobranca {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cobranca_id_seq")
	@Column(name="id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="originador_cobranca_id")
	private Usuario originadorCobranca;
		
	@Column(name="cpf_destinatario")
	private String cpfDestinatario;
	
	@Column(name="valor_cobranca")
	private Double valorCobranca;
	
	@Column(name="descricao")
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private StatusCobranca status = StatusCobranca.PENDENTE;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Usuario getOriginadorCobranca() {
		return originadorCobranca;
	}

	public void setOriginadorCobranca(Usuario originadorCobranca) {
		this.originadorCobranca = originadorCobranca;
	}

	public String getCpfDestinatario() {
		return cpfDestinatario;
	}

	public void setCpfDestinatario(String cpfDestinatario) {
		this.cpfDestinatario = cpfDestinatario;
	}

	public Double getValorCobranca() {
		return valorCobranca;
	}

	public void setValorCobranca(Double valorCobranca) {
		this.valorCobranca = valorCobranca;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public StatusCobranca getStatus() {
		return status;
	}

	public void setStatus(StatusCobranca status) {
		this.status = status;
	}
	
	
	
}
