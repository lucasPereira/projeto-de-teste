package br.ufsc.setic.controle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Agencia;

@Named
public class ConversorAgencia implements Converter<Agencia> {

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	@Override
	public Agencia getAsObject(FacesContext faces, UIComponent componente, String valor) {
		if (valor == null) {
			return null;
		}
		return beanSistemaBancario.getSistemaBancario().obterPeloIdentificador(Agencia.class, Integer.parseInt(valor));
	}

	@Override
	public String getAsString(FacesContext faces, UIComponent componente, Agencia objeto) {
		if (objeto == null) {
			return null;
		}
		return objeto.getIdentificador().toString();
	}

}
