package br.com.zup.academy.keymanager

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoDeChaveTest{

    @Nested
    inner class ChaveAleatoriaTest{

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
    inner class CpfTest{
        @Test
        internal fun deveSerValidoQuandoCpfForUmNumeroValido() {
            val tipoDeChave = TipoDeChave.CPF
            tipoDeChave.valida("71961721082")
        }

        @Test
        internal fun naoDeveSerValidoQuandoCpfForNuloOuVazio() {
            val tipoDeChave = TipoDeChave.CPF
            tipoDeChave.valida("")
        }

        @Test
        internal fun naoDeveSerValidoQuandoCpfForInvalido() {
            val tipoDeChave = TipoDeChave.CPF
            tipoDeChave.valida("11111111111")
        }
    }
}