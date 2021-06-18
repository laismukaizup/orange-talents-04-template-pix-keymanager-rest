package br.com.zup.academy.keymanager.shared

import io.grpc.Status
import io.grpc.StatusRuntimeException
import io.micronaut.http.HttpRequest
import io.micronaut.http.hateoas.JsonError
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

internal class GlobalExceptionHandlerTest {

    val requestGenerica = HttpRequest.GET<Any>("/")

    @Test
    internal fun deveRetornar404quandoForNotFound() {
        val mensagem = "nao encontrado"
        val statusRuntimeException = StatusRuntimeException(Status.NOT_FOUND.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestGenerica, statusRuntimeException)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }
    @Test
    internal fun deveRetornar422quandoForAlreadyExist() {
        val mensagem = "ja existe"
        val statusRuntimeException = StatusRuntimeException(Status.ALREADY_EXISTS.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestGenerica, statusRuntimeException)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }
    @Test
    internal fun deveRetornar400quandoForInvalidArgument() {
        val mensagem = "dados da requisição estão inválidos"
        val statusRuntimeException = StatusRuntimeException(Status.INVALID_ARGUMENT.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestGenerica, statusRuntimeException)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }
    @Test
    internal fun deveRetornar500quandoForQualquerOutroErro() {
        val mensagem = "não foi possível completar a requisição"
        val statusRuntimeException = StatusRuntimeException(Status.UNKNOWN.withDescription(mensagem))

        val resposta = GlobalExceptionHandler().handle(requestGenerica, statusRuntimeException)
        assertNotNull(resposta.body())
        assertEquals(mensagem, (resposta.body() as JsonError).message)
    }
}