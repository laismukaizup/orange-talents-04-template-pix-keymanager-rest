package br.com.zup.academy.keymanager

import br.com.zup.academy.RegistraChavePixRequest
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@ValidKeyPix
@Introspected
class NovaChavePixRequest(
    @field:NotNull val tipoConta: TipoDeConta,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoChave: TipoDeChave
) {

    fun paraModelGrpc(clienteId: String): RegistraChavePixRequest {
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clienteId)
            .setTipoConta(tipoConta.converterToGrpcObject())
            .setTipoChave(tipoChave.converterToGRPCObject())
            .setValorChave(chave ?: "")
            .build()
    }


}
