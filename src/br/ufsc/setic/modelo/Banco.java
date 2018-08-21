package br.ufsc.setic.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import br.ufsc.setic.modelo.moeda.Dinheiro;
import br.ufsc.setic.modelo.moeda.Moeda;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nome" }) })
public class Banco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;

	private String nome;

	@Enumerated(EnumType.STRING)
	private Moeda moeda;

	private Dinheiro taxaDeTransacao;

	public Banco() {}

	public Banco(String nome, Moeda moeda, Dinheiro taxaDeTransacao) {
		this.nome = nome;
		this.moeda = moeda;
		this.taxaDeTransacao = taxaDeTransacao;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public String getNome() {
		return nome;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	public Dinheiro getTaxaDeTransacao() {
		return taxaDeTransacao;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setMoeda(Moeda moeda) {
		this.moeda = moeda;
	}

	public void setTaxaDeTransacao(Dinheiro taxaDeTransacao) {
		this.taxaDeTransacao = taxaDeTransacao;
	}

	@Override
	public String toString() {
		return String.format("%s (%s)", nome, moeda);
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Banco) {
			Banco outro = (Banco) objeto;
			Boolean mesmoIdentificador = Objects.equals(identificador, outro.identificador);
			Boolean mesmoNome = Objects.equals(nome, outro.nome);
			Boolean mesmaMoeda = Objects.equals(moeda, outro.moeda);
			Boolean mesmaTaxa = Objects.equals(taxaDeTransacao, outro.taxaDeTransacao);
			return mesmoIdentificador && mesmoNome && mesmaMoeda && mesmaTaxa;
		}
		return super.equals(objeto);
	}

}
