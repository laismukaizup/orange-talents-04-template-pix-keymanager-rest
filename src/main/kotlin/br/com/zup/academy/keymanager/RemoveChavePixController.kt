package br.com.zup.academy.keymanager

import br.com.zup.academy.RemoveChavePixGRPCServiceGrpc
import br.com.zup.academy.RemoveChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.validation.Validated
import javax.inject.Inject

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RemoveChavePixController(@Inject private val removeChavePixClient: RemoveChavePixGRPCServiceGrpc.RemoveChavePixGRPCServiceBlockingStub) {

    @Delete("/pix/{pixId}")
    fun remove(clienteId: String, pixId: String): HttpResponse<Any> {

        if (clienteId.isNullOrBlank())
            throw IllegalArgumentException("cliente id não pode ser vazio ou nulo")

        if (pixId.isNullOrBlank())
            throw IllegalArgumentException("cliente id não pode ser vazio ou nulo")

        val responseGrpc = removeChavePixClient.remover(
            RemoveChavePixRequest.newBuilder()
                .setClienteId(clienteId)
                .setPixId(pixId)
                .build()
        )

        return HttpResponse.ok()
    }
}