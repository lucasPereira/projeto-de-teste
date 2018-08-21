package br.ufsc.setic.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Conta;

@Named
@ViewScoped
public class BeanListarContas implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	public List<Conta> getContas() {
		return beanSistemaBancario.getSistemaBancario().listarContas();
	}

	public void remover(Conta conta) {
		beanSistemaBancario.getSistemaBancario().removerConta(conta);
	}

}
