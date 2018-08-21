package br.ufsc.setic.controle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.ufsc.setic.modelo.Banco;
import br.ufsc.setic.modelo.moeda.Dinheiro;
import br.ufsc.setic.modelo.moeda.FabricaDeDinheiro;
import br.ufsc.setic.modelo.moeda.Moeda;

@Named
@ViewScoped
public class BeanCadastrarBanco implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private BeanSistemaBancario beanSistemaBancario;

	@Inject
	private BeanNavegacao beanNavegacao;

	@Inject
	private BeanMensageiro beanMensageiro;

	private Banco banco;
	private BigDecimal taxaDeTransacao;

	public BeanCadastrarBanco() {
		inicializar();
	}

	private void inicializar() {
		banco = new Banco();
		taxaDeTransacao = null;
	}

	public Banco getBanco() {
		return banco;
	}

	public BigDecimal getTaxaDeTransacao() {
		return taxaDeTransacao;
	}

	public void setTaxaDeTransacao(BigDecimal taxaDeTransacao) {
		this.taxaDeTransacao = taxaDeTransacao;
	}

	public List<Moeda> getMoedas() {
		return Arrays.asList(Moeda.values());
	}

	public void cadastrar() {
		preencherTaxaDeTransacao();
		try {
			beanSistemaBancario.getSistemaBancario().cadastrarBanco(banco);
			inicializar();
			beanMensageiro.sucesso("Banco cadastradado com sucesso!");
			beanNavegacao.irParaListarBancos();
		} catch (Exception excecao) {
			beanMensageiro.erro("Não foi possível cadastrar o banco. Certifique-se que não exista um banco com o mesmo nome.");
		}
	}

	private void preencherTaxaDeTransacao() {
		Moeda moeda = banco.getMoeda();
		BigDecimal base = new BigDecimal(moeda.getBase());
		Integer inteiro = taxaDeTransacao.intValue();
		Integer fracao = taxaDeTransacao.remainder(BigDecimal.ONE).multiply(base).intValue();
		FabricaDeDinheiro fabricaDeDinheiro = new FabricaDeDinheiro(moeda);
		Dinheiro dinheiro = fabricaDeDinheiro.construir(inteiro, fracao);
		banco.setTaxaDeTransacao(dinheiro);
	}

}
