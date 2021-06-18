package br.com.zup.academy.keymanager

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoDeChaveTest {

    @Nested
    inner class ChaveAleatoriaTest {

        @Test
        internal fun deveSerValidoQuandoChaveAleatoriaForNuloOuVazia() {
            val tipoDeChave = TipoDeChave.CHAVE_ALEATORIA

            assertTrue(tipoDeChave.valida(null))
            assertTrue(tipoDeChave.valida(""))
        }

        @Test
        internal fun naoDeveSerValidoQuandoChaveAleatoriaPossuirUmValor() {
            val tipoDeChave = TipoDeChave.CHAVE_ALEATORIA

            assertFalse(tipoDeChave.valida("um valor qualquer"))
        }
    }

    @Nested
    inner class CpfTest {
        @Test
        internal fun deveSerValidoQuandoCpfForUmNumeroValido() {
            val tipoDeChave = TipoDeChave.CPF
            assertTrue(tipoDeChave.valida("71961721082"))
        }

        @Test
        internal fun naoDeveSerValidoQuandoCpfForNuloOuVazio() {
            val tipoDeChave = TipoDeChave.CPF
            assertFalse(tipoDeChave.valida(""))
        }

        @Test
        internal fun naoDeveSerValidoQuandoCpfForInvalido() {
            val tipoDeChave = TipoDeChave.CPF
            assertFalse(tipoDeChave.valida("123"))
        }
    }

    @Nested
    inner class EmailTest {
        @Test
        internal fun deveSerValidoQuandoEmailForValido() {
            val tipoDeChave = TipoDeChave.EMAIL
            assertTrue(tipoDeChave.valida("teste@teste.com.br"))
        }

        @Test
        internal fun naoDeveSerValidoQuandoEmailVazioOuNulo() {
            val tipoDeChave = TipoDeChave.EMAIL
            assertFalse( tipoDeChave.valida(""))
            assertFalse( tipoDeChave.valida(null))
        }

        @Test
        internal fun naoDeveSerValidoQuandoEmailForInvalido() {
            val tipoDeChave = TipoDeChave.EMAIL
            assertFalse( tipoDeChave.valida("123"))
        }
    }

    @Nested
    inner class CelularTest {
        @Test
        internal fun deveSerValidoQuandoTelefoneForValido() {
            val tipoDeChave = TipoDeChave.CELULAR
            assertFalse(tipoDeChave.valida("+5515999999999"))
        }

        @Test
        internal fun naoDeveSerValidoQuandoCelularForVazioOuNulo() {
            val tipoDeChave = TipoDeChave.CELULAR
            assertFalse(tipoDeChave.valida(""))
            assertFalse(tipoDeChave.valida(null))
        }

        @Test
        internal fun naoDeveSerValidoQuandoCelularForInvalido() {
            val tipoDeChave = TipoDeChave.CELULAR
            assertFalse(tipoDeChave.valida("123"))
        }
    }
}