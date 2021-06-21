package br.com.zup.academy.keymanager

import br.com.zup.academy.*
import br.com.zup.academy.keymanager.shared.grpc.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.any
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CarregaChavePixControllerTest {

    @field:Inject
    lateinit var carregaStub: CarregaChavePixGRPCServiceGrpc.CarregaChavePixGRPCServiceBlockingStub

    @field:Inject
    lateinit var listaStub: ListaChavePixGRPCServiceGrpc.ListaChavePixGRPCServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun deveCarregarUmaChavePixQuandoPassadoUmClienteIdeChavePix() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = carregaStub.carregar(
            CarregaChavePixRequest.newBuilder()
                .setPixId(
                    CarregaChavePixRequest.FiltroPorIdPix.newBuilder()
                        .setClienteId(clientId)
                        .setPixId(pixId).build()
                )
                .build()
        )

        val chavePixResponse = CarregaChavePixResponse.newBuilder()
            .setClienteId(clientId)
            .setPidId(pixId)
            .setChave(
                CarregaChavePixResponse.ChavePix.newBuilder()
                    .setTipo(TipoDeChaveGRPC.EMAIL)
                    .setValorChave("teste@teste.com")
                    .setConta(
                        CarregaChavePixResponse.ChavePix.ContaInfo.newBuilder()
                            .setTipo(TipoDeContaGRPC.CONTA_POUPANCA)
                            .setInstituicao("itau")
                            .setNomeDoTitular("teste")
                            .setCpfDoTitular("3333333333333")
                            .setAgencia("0001")
                            .setNumeroDaConta("12458")
                            .build()
                    )
                    .setCriadoEm(LocalDateTime.now().let {
                        val createAt = it.atZone(ZoneId.of(("UTC"))).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createAt.epochSecond)
                            .setNanos(createAt.nano)
                    })
            )
            .build()

        given(carregaStub.carregar(any())).willReturn(chavePixResponse)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clientId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

    }


    @Test
    internal fun deveCarregarUmaListaQuandoPassadoUmClienteId() {

        val clientId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val responseGrpc = listaStub.listar(ListaChavePixRequest.newBuilder()
            .setClienteId(clientId)
                .build()
        )

        val chavePixResponse = ListaChavePixResponse.ChavePix.newBuilder()
                    .setPixId(pixId)
                    .setTipoChave(TipoDeChaveGRPC.EMAIL)
                    .setValorChave("teste@teste.com")
                    .setTipoConta(TipoDeContaGRPC.CONTA_POUPANCA)
                    .setCriadoEm(LocalDateTime.now().let {
                        val createAt = it.atZone(ZoneId.of(("UTC"))).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createAt.epochSecond)
                            .setNanos(createAt.nano)
                    })
            .build()

        val chavePixResponse02 = ListaChavePixResponse.ChavePix.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoChave(TipoDeChaveGRPC.EMAIL)
            .setValorChave("teste02@teste.com")
            .setTipoConta(TipoDeContaGRPC.CONTA_POUPANCA)
            .setCriadoEm(LocalDateTime.now().let {
                val createAt = it.atZone(ZoneId.of(("UTC"))).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createAt.epochSecond)
                    .setNanos(createAt.nano)
            })
            .build()

        val listaChaves = ListaChavePixResponse.newBuilder()
            .setClienteId(clientId)
            .addAllChaves(listOf(chavePixResponse, chavePixResponse02))
            .build()




        given(listaStub.listar(any())).willReturn(listaChaves)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clientId/lista")
        val response = client.toBlocking().exchange(request, Any::class.java)

    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory() {
        @Singleton
        fun stubMock() =
            Mockito.mock(CarregaChavePixGRPCServiceGrpc.CarregaChavePixGRPCServiceBlockingStub::class.java)


        @Singleton
        fun listaStubMock() =
            Mockito.mock(ListaChavePixGRPCServiceGrpc.ListaChavePixGRPCServiceBlockingStub::class.java)
    }
}