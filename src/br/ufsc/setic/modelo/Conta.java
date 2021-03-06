package br.ufsc.setic.modelo;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Conta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;

	private String titular;

	@ManyToOne
	private Agencia agencia;

	public Conta() {}

	public Conta(String titular, Agencia agencia) {
		this.titular = titular;
		this.agencia = agencia;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public Integer getDigitoVerificador() {
		return (titular.length() + identificador) % 10;
	}

	public String getTitular() {
		return titular;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	@Override
	public String toString() {
		return String.format("%04d-%d", identificador, getDigitoVerificador());
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Conta) {
			Conta outro = (Conta) objeto;
			Boolean mesmoIdentificador = Objects.equals(identificador, outro.identificador);
			Boolean mesmoTitular = Objects.equals(titular, outro.titular);
			Boolean mesmaAgencia = Objects.equals(agencia, outro.agencia);
			return mesmoIdentificador && mesmoTitular && mesmaAgencia;
		}
		return super.equals(objeto);
	}

}
