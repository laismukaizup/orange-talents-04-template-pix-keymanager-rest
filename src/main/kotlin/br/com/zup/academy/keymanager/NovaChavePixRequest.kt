package br.com.zup.academy.keymanager

import br.com.zup.academy.RegistraChavePixRequest
import br.com.zup.academy.TipoDeChaveGRPC
import br.com.zup.academy.TipoDeContaGRPC
import io.micronaut.core.annotation.Introspected
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator
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


enum class TipoDeConta {
    CONTA_CORRENTE, CONTA_POUPANCA;

//        fun converter(): AccountType {
//            return when (this) {
//                CONTA_CORRENTE -> AccountType.CACC
//                CONTA_POUPANCA -> AccountType.SVGS
//            }
//    }

    fun converterToGrpcObject(): TipoDeContaGRPC {
        return when (this) {
            CONTA_CORRENTE -> TipoDeContaGRPC.CONTA_CORRENTE
            CONTA_POUPANCA -> TipoDeContaGRPC.CONTA_POUPANCA
        }
    }
}


enum class TipoDeChave {

    CPF {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            if (!chave.matches("^[0-9]{11}\$".toRegex()))
                return false
            return CPFValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    EMAIL {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },
    CELULAR {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank())
                return false
            return chave.matches("^\\\\+[1-9][0-9]\\\\d{1,14}\\\$".toRegex())
        }

    },
    CHAVE_ALEATORIA {
        override fun valida(chave: String?) = chave.isNullOrBlank()
    };

//    fun converter(): KeyType {
//        return when (this) {
//            CPF -> KeyType.CPF
//            EMAIL -> KeyType.EMAIL
//            CELULAR -> KeyType.PHONE
//            CHAVE_ALEATORIA -> KeyType.RANDOM
//        }
//    }

    fun converterToGRPCObject(): TipoDeChaveGRPC {
        return when (this) {
            CPF -> TipoDeChaveGRPC.CPF
            EMAIL -> TipoDeChaveGRPC.EMAIL
            CELULAR -> TipoDeChaveGRPC.CELULAR
            CHAVE_ALEATORIA -> TipoDeChaveGRPC.CHAVE_ALEATORIA
        }
    }

    abstract fun valida(chave: String?): Boolean
}
