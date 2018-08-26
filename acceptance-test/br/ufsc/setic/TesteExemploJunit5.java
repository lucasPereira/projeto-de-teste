package br.ufsc.setic;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.Duration;

import org.junit.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@Ignore
public class TesteExemploJunit5 {

	@Test
	@Disabled
	void ignorado() throws Exception {

	}

	@ParameterizedTest
	@ValueSource(ints = { 2, 4, 6, 8 })
	void numeroPar(Integer valor) throws Exception {
		assertTrue(valor % 2 == 0);
	}

	@ParameterizedTest
	@CsvSource({ "0, 0, 0", "0, 1, 1", "0, 2, 2", "1, 1, 2", "1, 2, 3" })
	void calculadora(Integer primeiro, Integer segundo, Integer resultado) {
		assertEquals(resultado.intValue(), primeiro + segundo);
	}

	@RepeatedTest(1000)
	void repetir() throws Exception {
		assertTrue(Math.random() < 1);
	}

	@Test
	void variasAssercoes() throws Exception {
		assertAll(() -> assertEquals("A", "a".toUpperCase()), () -> assertEquals("B", "b".toUpperCase()));
	}

	@Test
	void excecao() throws Exception {
		Exception excecao = assertThrows(ArithmeticException.class, () -> {
			Integer operacao = 0 / 0;
			System.out.println(operacao);
		});
		assertEquals("/ by zero", excecao.getMessage());
	}

	@Test
	void timeout() throws Exception {
		Integer resultado = assertTimeout(Duration.ofSeconds(2), () -> {
			Thread.sleep(1000);
			return 10;
		});
		assertEquals(10, resultado.intValue());
	}

	@Test
	void assumptions1() throws Exception {
		assumeTrue(true);
		assertEquals("Teste", "Teste");
	}

	@Test
	void assumptions2() throws Exception {
		assumeTrue(true, () -> {
			return "Mensagem caso a suposição seja falsa";
		});
		assertEquals("Teste", "Teste");
	}

	@Test
	void assumptions3() throws Exception {
		assumingThat(true, () -> {
			assertEquals("Teste", "Teste");
		});
		assertEquals("Software", "Software");
	}

}
