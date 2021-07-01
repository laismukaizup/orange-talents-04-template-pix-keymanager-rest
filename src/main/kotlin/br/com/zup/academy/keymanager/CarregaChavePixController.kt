package br.com.zup.academy.keymanager

import br.com.zup.academy.CarregaChavePixGRPCServiceGrpc
import br.com.zup.academy.CarregaChavePixRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.tracing.annotation.ContinueSpan
import io.micronaut.tracing.annotation.SpanTag
import io.micronaut.validation.Validated
import javax.inject.Inject
import javax.validation.constraints.NotBlank

@Validated
@Controller("/api/v1/clientes/{clienteId}")
class CarregaChavePixController(@Inject private val carregaClient: CarregaChavePixGRPCServiceGrpc.CarregaChavePixGRPCServiceBlockingStub) {

    @Get("pix/{pixId}")
    @ContinueSpan
    fun carrega(@SpanTag("grpc.clienteId") clienteId: String, pixId: String): HttpResponse<Any> {

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
    fun carrega(valorChave:String): HttpResponse<Any> {
        val response = carregaClient.carregar(
            CarregaChavePixRequest.newBuilder()
                .setValorChave(valorChave)
                .build()
        )

        return HttpResponse.ok(DetalheChavePixResponse(response))
    }
}