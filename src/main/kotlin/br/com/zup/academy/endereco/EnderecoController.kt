package br.com.zup.academy.endereco

import br.com.zup.academy.keymanager.DetalheChavePixResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import javax.inject.Inject


@Controller("/endereco")
class EnderecoController(@Inject val enderecoClient: EnderecoClient) {


    @Get("{cep}")
    fun buscaEndereco(cep : String) : HttpResponse<EnderecoResponse>{

        val consultaCep = enderecoClient.consultaCep(cep)
        return consultaCep

    }
}