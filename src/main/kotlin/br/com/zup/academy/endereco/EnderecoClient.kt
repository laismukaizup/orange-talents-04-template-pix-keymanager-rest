package br.com.zup.academy.endereco

import io.micronaut.core.annotation.Introspected
import io.micronaut.http.HttpResponse
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("https://viacep.com.br/ws")
interface EnderecoClient {

    @Get("/{cep}/xml/", produces = [MediaType.APPLICATION_XML], consumes = [MediaType.APPLICATION_XML])
    fun consultaCep(@PathVariable cep: String): HttpResponse<EnderecoResponse>
}


@Introspected
data class EnderecoResponse(val cepxml: CepXML)

@Introspected
data class CepXML(
    val cep: String,
    val logradouro: String,
    val complemento: String,
    val bairro: String,
    val localidade: String,
    val uf: String,
    val ibge: String,
    val gia: String,
    val ddd: String,
    val siafi: String
)