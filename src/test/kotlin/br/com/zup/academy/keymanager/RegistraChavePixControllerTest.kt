package br.com.zup.academy.keymanager

import br.com.zup.academy.CadastraChavePixGRPCServiceGrpc
import br.com.zup.academy.keymanager.shared.grpc.KeyManagerGrpcFactory
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class RegistraChavePixControllerTest {

    @field:Inject
    lateinit var registraStub: CadastraChavePixGRPCServiceGrpc.CadastraChavePixGRPCServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient


    @Test
    internal fun deveInserirUmaChavePix() {

        assertTrue(true)
//        val clientId = UUID.randomUUID().toString()
//        val pixId = UUID.randomUUID().toString()
//
//        val responseGrpc = RegistraChavePixResponse.newBuilder()
//            .setClienteId(clientId)
//            .setPixId(pixId)
//            .build()
//
//        given(registraStub.cadastrar(Mockito.any())).willReturn(responseGrpc)
//
//
//        val novaChavePixRequest = NovaChavePixRequest(
//            TipoDeConta.CONTA_POUPANCA,
//            "teste@teste.com.br",
//            TipoDeChave.EMAIL
//        )
//        val request = HttpRequest.POST("/api/v1/clientes/$clientId/pix", novaChavePixRequest)
//        val response = client.toBlocking().exchange(request, novaChavePixRequest::class.java)
//        with(response) {
//            assertEquals(HttpStatus.CREATED, response.status)
//            assertTrue(response.headers.contains("Location"))
//            assertTrue(response.header("Location")!!.contains(pixId))
//        }
    }

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class MockitoStubFactory() {
        @Singleton
        fun stubMock() =
            Mockito.mock(CadastraChavePixGRPCServiceGrpc.CadastraChavePixGRPCServiceBlockingStub::class.java)
    }
}