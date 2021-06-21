package br.com.zup.academy.keymanager

import br.com.zup.academy.CarregaChavePixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChavePixResponse(response: CarregaChavePixResponse) {
    val pixId = response.pidId
    val tipoChave = response.chave.tipo.name
    val chave = response.chave.valorChave
    val criadoEm = response.chave.criadoEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
    val tipoConta = response.chave.conta.tipo.name
    val conta = mapOf(Pair("tipo", tipoConta),
        Pair("instituicao", response.chave.conta.instituicao),
        Pair("nomeDoTitular", response.chave.conta.nomeDoTitular),
        Pair("cpfDoTitular",  response.chave.conta.cpfDoTitular),
        Pair("agencia",  response.chave.conta.agencia),
        Pair("numero",  response.chave.conta.numeroDaConta),
    )
}


