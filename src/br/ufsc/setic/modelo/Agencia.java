package br.ufsc.setic.modelo;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "nome", "banco_identificador" }) })
public class Agencia {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer identificador;

	private String nome;

	@ManyToOne
	private Banco banco;

	public Agencia() {}

	public Agencia(String nome, Banco banco) {
		this.nome = nome;
		this.banco = banco;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public Integer getDigitoVerificador() {
		return identificador % 10;
	}

	public String getNome() {
		return nome;
	}

	public Banco getBanco() {
		return banco;
	}

	@Override
	public String toString() {
		return String.format("%03d-%d", identificador, getDigitoVerificador());
	}

	@Override
	public boolean equals(Object objeto) {
		if (objeto instanceof Agencia) {
			Agencia outra = (Agencia) objeto;
			Boolean mesmoIdentificador = Objects.equals(identificador, outra.identificador);
			Boolean mesmoNome = Objects.equals(nome, outra.nome);
			Boolean mesmoBanco = Objects.equals(banco, outra.banco);
			return mesmoIdentificador && mesmoNome && mesmoBanco;
		}
		return super.equals(objeto);
	}
}
