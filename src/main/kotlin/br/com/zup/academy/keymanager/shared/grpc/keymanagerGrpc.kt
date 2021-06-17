package br.com.zup.academy.keymanager.shared.grpc

import br.com.zup.academy.CadastraChavePixGRPCServiceGrpc
import br.com.zup.academy.CarregaChavePixGRPCServiceGrpc
import br.com.zup.academy.ListaChavePixGRPCServiceGrpc
import br.com.zup.academy.RemoveChavePixGRPCServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave() = CadastraChavePixGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave() = RemoveChavePixGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChave() = CarregaChavePixGRPCServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave() = ListaChavePixGRPCServiceGrpc.newBlockingStub(channel)


}