package br.ufsc.setic.modelo.transacao;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

import br.ufsc.setic.modelo.Conta;
import br.ufsc.setic.modelo.moeda.CalculadoraMonetaria;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Transacao {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;

	@ManyToOne
	private Conta conta;

	public Transacao() {}

	public Transacao(Conta conta) {
		this.conta = conta;
	}

	public Conta getConta() {
		return conta;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public abstract void contabilizar(CalculadoraMonetaria calculadora);

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Transacao) {
			Transacao outro = (Transacao) objeto;
			Boolean mesmoIdentificador = Objects.equals(identificador, outro.identificador);
			return mesmoIdentificador;
		}
		return super.equals(objeto);
	}

}
