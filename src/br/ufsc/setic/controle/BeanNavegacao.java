package br.ufsc.setic.controle;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class BeanNavegacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pagina;

	public BeanNavegacao() {
		irParaInicio();
	}

	public String getPagina() {
		return pagina;
	}

	public void irParaInicio() {
		pagina = "/paginas/inicio.xhtml";
	}

	public void irParaListarBancos() {
		pagina = "/paginas/listarBancos.xhtml";
	}

	public void irParaCadastrarBanco() {
		pagina = "/paginas/cadastrarBanco.xhtml";
	}

	public void irParaListarAgencias() {
		pagina = "/paginas/listarAgencias.xhtml";
	}

	public void irParaCadastrarAgencia() {
		pagina = "/paginas/cadastrarAgencia.xhtml";
	}

	public void irParaListarContas() {
		pagina = "/paginas/listarContas.xhtml";
	}

	public void irParaCadastrarConta() {
		pagina = "/paginas/cadastrarConta.xhtml";
	}

	public void irParaConstrucao() {
		pagina = "/paginas/construcao.xhtml";
	}

}
