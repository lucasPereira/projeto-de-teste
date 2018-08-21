package br.ufsc.setic.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Agencia;
import br.ufsc.setic.modelo.Banco;
import br.ufsc.setic.modelo.Conta;

@Named
@ViewScoped
public class BeanCadastrarConta implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	@Inject
	private BeanNavegacao beanNavegacao;

	@Inject
	private BeanMensageiro beanMensageiro;

	private Conta conta;
	private Agencia agencia;
	private Banco banco;

	public BeanCadastrarConta() {
		inicializar();
	}

	private void inicializar() {
		conta = new Conta();
		banco = null;
		agencia = null;
	}

	public Conta getConta() {
		return conta;
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
	}

	public void setAgencia(Agencia agencia) {
		this.agencia = agencia;
	}

	public List<Banco> getBancos() {
		return beanSistemaBancario.getSistemaBancario().listarBancos();
	}

	public List<Agencia> getAgencias() {
		return beanSistemaBancario.getSistemaBancario().listarAgencias(banco);
	}

	public void cadastrar() {
		try {
			conta.setAgencia(agencia);
			beanSistemaBancario.getSistemaBancario().cadastrarConta(conta);
			inicializar();
			beanMensageiro.sucesso("Conta cadastradada com sucesso!");
			beanNavegacao.irParaListarContas();
		} catch (Exception excecao) {
			beanMensageiro.erro("Não foi possível cadastrar a conta.");
		}
	}

}
