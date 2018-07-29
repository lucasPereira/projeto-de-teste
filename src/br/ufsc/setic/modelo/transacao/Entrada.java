package br.ufsc.setic.modelo.transacao;

import br.ufsc.setic.modelo.Conta;
import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;
import br.ufsc.setic.modelo.moeda.Dinheiro;

public class Entrada implements Transacao {

	private Conta conta;
	private Dinheiro quantia;

	public Entrada(Conta conta, Dinheiro quantia) {
		this.conta = conta;
		this.quantia = quantia;
	}

	public Conta getConta() {
		return conta;
	}

	public Dinheiro getQuantia() {
		return quantia;
	}

	@Override
	public void contabilizar(CalculadoraMonetaria calculadora) {
		calculadora.somar(quantia);
	}

}
