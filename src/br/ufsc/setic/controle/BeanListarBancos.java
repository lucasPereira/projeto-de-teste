package br.ufsc.setic.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Banco;

@Named
@ViewScoped
public class BeanListarBancos implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	public List<Banco> getBancos() {
		return beanSistemaBancario.getSistemaBancario().listarBancos();
	}

}
