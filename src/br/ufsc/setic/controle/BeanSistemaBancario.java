package br.ufsc.setic.controle;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

import br.ufsc.setic.SistemaBancario;

@Named
@ApplicationScoped
public class BeanSistemaBancario implements Serializable {

	private static final long serialVersionUID = 1L;

	private SistemaBancario sistemaBancario;

	public BeanSistemaBancario() {
		sistemaBancario = new SistemaBancario();
	}

	public SistemaBancario getSistemaBancario() {
		return sistemaBancario;
	}

}
