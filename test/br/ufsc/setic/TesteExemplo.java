package br.ufsc.setic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

import org.junit.Before;
import org.junit.Test;

import br.ufsc.setic.modelo.Agencia;
import br.ufsc.setic.modelo.Banco;
import br.ufsc.setic.modelo.Conta;
import br.ufsc.setic.modelo.moeda.Dinheiro;
import br.ufsc.setic.modelo.moeda.FabricaDeDinheiro;
import br.ufsc.setic.modelo.moeda.Moeda;
import br.ufsc.setic.modelo.transacao.Entrada;
import br.ufsc.setic.modelo.transacao.NaoRealizada;
import br.ufsc.setic.modelo.transacao.Saida;
import br.ufsc.setic.modelo.transacao.Transacao;

public class TesteExemplo {

	@Before
	public void configurar() throws Exception {
		new SistemaBancario().limparBancoDeDados();
	}

	@Test
	public void criarBanco() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		assertNull(caixa.getIdentificador());
		assertEquals("Caixa Econômica Federal", caixa.getNome());
		assertEquals(Moeda.BRL, caixa.getMoeda());
		assertEquals(zeroReais, caixa.getTaxaDeTransacao());
	}

	@Test
	public void cadastrarBanco() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		sistemaBancario.cadastrarBanco(caixa);
		assertNotNull(caixa.getIdentificador());
	}

	@Test(expected = PersistenceException.class)
	public void cadastrarBancoComMesmoNome() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Dinheiro zeroEuros = new FabricaDeDinheiro(Moeda.EUR).construir();
		Banco caixaReal = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco caixaEuro = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.EUR, zeroEuros);
		sistemaBancario.cadastrarBanco(caixaReal);
		sistemaBancario.cadastrarBanco(caixaEuro);
	}

	@Test
	public void cadastrarBancoComMesmoNomeDepoisCadastrarComNomeDiferente() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixaEconomicaFederal = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		sistemaBancario.cadastrarBanco(caixaEconomicaFederal);
		try {
			sistemaBancario.cadastrarBanco(caixa);
		} catch (Exception excecao) {
			caixa.setNome("Caxa");
			sistemaBancario.cadastrarBanco(caixa);
			assertNotNull(caixa.getIdentificador());
		}
	}

	@Test
	public void listarBancosSemBancos() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		List<Banco> bancos = sistemaBancario.listarBancos();
		assertEquals(0, bancos.size());
	}

	@Test
	public void listarBancosComUmBanco() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		sistemaBancario.cadastrarBanco(caixa);
		List<Banco> bancos = sistemaBancario.listarBancos();
		assertEquals(1, bancos.size());
		assertEquals(caixa, bancos.get(0));
	}

	@Test
	public void listarBancosComDoisBancos() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL, zeroReais);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarBanco(bancoDoBrasil);
		List<Banco> bancos = sistemaBancario.listarBancos();
		assertEquals(2, bancos.size());
		assertEquals(bancoDoBrasil, bancos.get(0));
		assertEquals(caixa, bancos.get(1));
	}

	@Test
	public void criarAgencia() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		assertNull(caixaTrindade.getIdentificador());
		assertEquals("Trindade", caixaTrindade.getNome());
		assertEquals(caixa, caixaTrindade.getBanco());
	}

	@Test
	public void cadastrarAgencia() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		assertNotNull(caixaTrindade.getIdentificador());
	}

	@Test(expected = RollbackException.class)
	public void cadastrarAgenciaComMesmoNomeBancosIguais() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Agencia caixaTrindade1 = sistemaBancario.criarAgencia("Trindade", caixa);
		Agencia caixaTrindade2 = sistemaBancario.criarAgencia("Trindade", caixa);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarAgencia(caixaTrindade1);
		sistemaBancario.cadastrarAgencia(caixaTrindade2);
	}

	@Test
	public void cadastrarAgenciaComMesmoNomeBancosDiferentes() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		Agencia bancoDoBrasilTrindade = sistemaBancario.criarAgencia("Trindade", bancoDoBrasil);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarBanco(bancoDoBrasil);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		sistemaBancario.cadastrarAgencia(bancoDoBrasilTrindade);
		assertNotNull(bancoDoBrasilTrindade.getIdentificador());
	}

	@Test
	public void listarAgencias() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		Agencia bancoDoBrasilTrindade = sistemaBancario.criarAgencia("Trindade", bancoDoBrasil);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarBanco(bancoDoBrasil);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		sistemaBancario.cadastrarAgencia(bancoDoBrasilTrindade);
		List<Agencia> agencias = sistemaBancario.listarAgencias();

		assertEquals(2, agencias.size());
		assertEquals(bancoDoBrasilTrindade, agencias.get(0));
		assertNotEquals(bancoDoBrasilTrindade, agencias.get(1));
		assertNotEquals(caixaTrindade, agencias.get(0));
		assertEquals(caixaTrindade, agencias.get(1));
	}

	@Test
	public void listarContas() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		Agencia bancoDoBrasilTrindade = sistemaBancario.criarAgencia("Trindade", bancoDoBrasil);
		Conta lucasCaixaTrindade = sistemaBancario.criarConta("Lucas", caixaTrindade);
		Conta lucasBancoDoBrasilTrindade = sistemaBancario.criarConta("Lucas", bancoDoBrasilTrindade);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarBanco(bancoDoBrasil);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		sistemaBancario.cadastrarAgencia(bancoDoBrasilTrindade);
		sistemaBancario.cadastrarConta(lucasCaixaTrindade);
		sistemaBancario.cadastrarConta(lucasBancoDoBrasilTrindade);
		List<Conta> contas = sistemaBancario.listarContas();

		assertEquals(2, contas.size());
		assertEquals(lucasBancoDoBrasilTrindade, contas.get(0));
		assertEquals(lucasCaixaTrindade, contas.get(1));
	}

	@Test
	public void operacoes() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Banco bancoDoBrasil = sistemaBancario.criarBanco("Banco do Brasil", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		Agencia bancoDoBrasilTrindade = sistemaBancario.criarAgencia("Trindade", bancoDoBrasil);
		Conta lucasCaixaTrindade = sistemaBancario.criarConta("Lucas", caixaTrindade);
		Conta lucasBancoDoBrasilTrindade = sistemaBancario.criarConta("Lucas", bancoDoBrasilTrindade);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarBanco(bancoDoBrasil);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		sistemaBancario.cadastrarAgencia(bancoDoBrasilTrindade);
		sistemaBancario.cadastrarConta(lucasCaixaTrindade);
		sistemaBancario.cadastrarConta(lucasBancoDoBrasilTrindade);
		FabricaDeDinheiro fabricaDeDinheiro = new FabricaDeDinheiro(Moeda.BRL);
		Dinheiro doisReais = fabricaDeDinheiro.construir(2);
		Dinheiro tresReais = fabricaDeDinheiro.construir(3);
		Dinheiro cincoReais = fabricaDeDinheiro.construir(5);
		Dinheiro dezReais = fabricaDeDinheiro.construir(10);
		Dinheiro vinteReais = fabricaDeDinheiro.construir(20);
		Dinheiro vinteCincoReais = fabricaDeDinheiro.construir(25);
		sistemaBancario.depositar(lucasBancoDoBrasilTrindade, dezReais);
		sistemaBancario.depositar(lucasCaixaTrindade, vinteReais);
		sistemaBancario.sacar(lucasBancoDoBrasilTrindade, doisReais);
		sistemaBancario.transferir(lucasBancoDoBrasilTrindade, lucasCaixaTrindade, cincoReais);

		assertTrue(sistemaBancario.calcularSaldo(lucasBancoDoBrasilTrindade).getPositivo());
		assertEquals(sistemaBancario.calcularSaldo(lucasBancoDoBrasilTrindade).getQuantia(), tresReais);
		assertTrue(sistemaBancario.calcularSaldo(lucasCaixaTrindade).getPositivo());
		assertEquals(sistemaBancario.calcularSaldo(lucasCaixaTrindade).getQuantia(), vinteCincoReais);
	}

	@Test
	public void cadastrarTransacao() throws Exception {
		SistemaBancario sistemaBancario = new SistemaBancario();
		Dinheiro zeroReais = new FabricaDeDinheiro(Moeda.BRL).construir();
		Banco caixa = sistemaBancario.criarBanco("Caixa Econômica Federal", Moeda.BRL, zeroReais);
		Agencia caixaTrindade = sistemaBancario.criarAgencia("Trindade", caixa);
		Conta lucasCaixaTrindade = sistemaBancario.criarConta("Lucas", caixaTrindade);
		sistemaBancario.cadastrarBanco(caixa);
		sistemaBancario.cadastrarAgencia(caixaTrindade);
		sistemaBancario.cadastrarConta(lucasCaixaTrindade);
		FabricaDeDinheiro fabricaDeDinheiro = new FabricaDeDinheiro(Moeda.BRL);
		Dinheiro cincoReais = fabricaDeDinheiro.construir(5);
		Dinheiro dezReais = fabricaDeDinheiro.construir(10);
		Transacao entrada = new Entrada(lucasCaixaTrindade, cincoReais);
		Transacao saida = new Saida(lucasCaixaTrindade, dezReais);
		Transacao naoRealizada = new NaoRealizada(entrada);
		sistemaBancario.cadastrarTransacao(entrada);
		sistemaBancario.cadastrarTransacao(saida);
		sistemaBancario.cadastrarTransacao(naoRealizada);

		assertEquals(3, sistemaBancario.listarTransacoes(lucasCaixaTrindade).size());
		assertEquals(entrada, sistemaBancario.listarTransacoes(lucasCaixaTrindade).get(0));
		assertEquals(saida, sistemaBancario.listarTransacoes(lucasCaixaTrindade).get(1));
		assertEquals(naoRealizada, sistemaBancario.listarTransacoes(lucasCaixaTrindade).get(2));
	}

}
