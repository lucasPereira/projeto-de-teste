package br.ufsc.setic.modelo.transacao;

import javax.persistence.Entity;

import br.ufsc.setic.modelo.Conta;
import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;
import br.ufsc.setic.modelo.moeda.Dinheiro;

@Entity
public class Saida extends Transacao {

	private Dinheiro quantia;

	public Saida() {
		super();
	}

	public Saida(Conta conta, Dinheiro quantia) {
		super(conta);
		this.quantia = quantia;
	}

	public Dinheiro getQuantia() {
		return quantia;
	}

	@Override
	public void contabilizar(CalculadoraMonetaria calculadora) {
		calculadora.subtrair(quantia);
	}

}
