package br.ufsc.setic.modelo;

import br.ufsc.setic.modelo.moeda.Dinheiro;
import br.ufsc.setic.modelo.moeda.Moeda;

public class Banco {

	private Integer codigo;
	private String nome;
	private Moeda moeda;
	private Dinheiro taxaDeTransacao;

	protected Banco(Integer codigo, String nome, Moeda moeda, Dinheiro taxaDeTransacao) {
		this.codigo = codigo;
		this.nome = nome;
		this.moeda = moeda;
		this.taxaDeTransacao = taxaDeTransacao;
	}

	public Integer getCodigo() {
		return codigo;
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

}
