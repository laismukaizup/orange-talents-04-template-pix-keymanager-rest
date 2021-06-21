package br.com.zup.academy.keymanager

import br.com.zup.academy.ListaChavePixResponse
import io.micronaut.core.annotation.Introspected
import java.net.SecureCacheResponse
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class ChavePixResponse(response: ListaChavePixResponse.ChavePix) {
    val pixId = response.pixId
    val chave = response.valorChave
    val tipoChave = response.tipoChave.name
    val tipoConta = response.tipoConta.name
    val criadoEm = response.criadoEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
}
