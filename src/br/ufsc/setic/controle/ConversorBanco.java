package br.ufsc.setic.controle;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Banco;

@Named
public class ConversorBanco implements Converter<Banco> {

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	@Override
	public Banco getAsObject(FacesContext faces, UIComponent componente, String valor) {
		if (valor == null) {
			return null;
		}
		return beanSistemaBancario.getSistemaBancario().obterPeloIdentificador(Banco.class, Integer.parseInt(valor));
	}

	@Override
	public String getAsString(FacesContext faces, UIComponent componente, Banco objeto) {
		if (objeto == null) {
			return null;
		}
		return objeto.getIdentificador().toString();
	}

}
