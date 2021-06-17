package br.com.zup.academy.keymanager

import br.com.zup.academy.CadastraChavePixGRPCServiceGrpc.CadastraChavePixGRPCServiceBlockingStub
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class RegistraChavePixController(@Inject private val registraChavePixClient: CadastraChavePixGRPCServiceBlockingStub) {

    @Post("/pix")
    fun create(clienteId: String, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {

        val grpcResponse = registraChavePixClient.cadastrar(request.paraModelGrpc(clienteId))
        return HttpResponse.created(location(clienteId, grpcResponse.pixId))

    }

    private fun location(clienteId: String, pixId: String) =
        HttpResponse.uri("/api/v1/clientes/$clienteId/pix/${pixId}")

}