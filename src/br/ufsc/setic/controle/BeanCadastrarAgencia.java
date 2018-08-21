package br.ufsc.setic.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Agencia;
import br.ufsc.setic.modelo.Banco;

@Named
@ViewScoped
public class BeanCadastrarAgencia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	@Inject
	private BeanNavegacao beanNavegacao;

	@Inject
	private BeanMensageiro beanMensageiro;

	private Agencia agencia;

	public BeanCadastrarAgencia() {
		inicializar();
	}

	private void inicializar() {
		agencia = new Agencia();
	}

	public Agencia getAgencia() {
		return agencia;
	}

	public List<Banco> getBancos() {
		return beanSistemaBancario.getSistemaBancario().listarBancos();
	}

	public void cadastrar() {
		try {
			beanSistemaBancario.getSistemaBancario().cadastrarAgencia(agencia);
			inicializar();
			beanMensageiro.sucesso("Agência cadastrada com sucesso!");
			beanNavegacao.irParaListarAgencias();
		} catch (Exception excecao) {
			beanMensageiro.erro("Não foi possível cadastrar a agência. Certifique-se que não exista uma agência com o mesmo nome.");
		}
	}

}
