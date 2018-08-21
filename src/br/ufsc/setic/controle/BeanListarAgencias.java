package br.ufsc.setic.controle;

import java.io.Serializable;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Agencia;

@Named
@ViewScoped
public class BeanListarAgencias implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	public List<Agencia> getAgencias() {
		return beanSistemaBancario.getSistemaBancario().listarAgencias();
	}

}
