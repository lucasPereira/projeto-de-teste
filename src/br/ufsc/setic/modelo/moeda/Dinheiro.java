package br.ufsc.setic.modelo.moeda;

import javax.persistence.Embeddable;

@Embeddable
public class Dinheiro {

	private Moeda moeda;
	private Integer inteiro;
	private Integer fracao;

	public Dinheiro() {}

	public Dinheiro(Moeda moeda, Integer inteiro, Integer fracao) {
		this.moeda = moeda;
		this.inteiro = inteiro;
		this.fracao = fracao;
	}

	public Integer getInteiro() {
		return inteiro;
	}

	public Integer getFracao() {
		return fracao;
	}

	public Moeda getMoeda() {
		return moeda;
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Dinheiro) {
			Dinheiro outro = (Dinheiro) objeto;
			Boolean mesmaMoeda = moeda.equals(outro.moeda);
			Boolean mesmoValor = inteiro.equals(outro.inteiro) && fracao.equals(outro.fracao);
			return mesmoValor && mesmaMoeda;
		}
		return super.equals(objeto);
	}

	@Override
	public String toString() {
		return String.format("%s %d,%02d", moeda, inteiro, fracao);
	}

}
