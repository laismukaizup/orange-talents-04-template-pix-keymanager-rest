package br.com.zup.academy.keymanager

import br.com.zup.academy.*
import com.google.protobuf.Timestamp
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.validation.Validated
import java.time.ZoneId
import javax.inject.Inject

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController(
    @Inject private val carregaClient: CarregaChavePixGRPCServiceGrpc.CarregaChavePixGRPCServiceBlockingStub,
    @Inject private val listaClient: ListaChavePixGRPCServiceGrpc.ListaChavePixGRPCServiceBlockingStub
) {

    @Get("pix/{pixId}")
    fun carrega(clienteId: String, pixId: String): HttpResponse<Any> {

        val response = carregaClient.carregar(
            CarregaChavePixRequest.newBuilder()
                .setPixId(
                    CarregaChavePixRequest.FiltroPorIdPix.newBuilder()
                        .setClienteId(clienteId)
                        .setPixId(pixId).build()
                )
                .build()
        )

        return HttpResponse.ok(DetalheChavePixResponse(response))
    }

    @Get("chave/{valorChave}")
    fun carrega(valorChave: String): HttpResponse<Any> {
        val response = carregaClient.carregar(
            CarregaChavePixRequest.newBuilder()
                .setValorChave(valorChave)
                .build()
        )

        return HttpResponse.ok(DetalheChavePixResponse(response))
    }

    @Get("/lista")
    fun lista(clienteId: String): HttpResponse<Any> {
        val response = listaClient.listar(
            ListaChavePixRequest.newBuilder()
                .setClienteId(clienteId)
                .build()
        )
        val chaves = response.chavesList.map { ChavePixResponse(it) }


        return HttpResponse.ok(chaves)
    }
}