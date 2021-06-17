package br.com.zup.academy.keymanager

import javax.inject.Singleton
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.CLASS, AnnotationTarget.TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [ValidPixKeyValidador::class])
annotation class ValidKeyPix(
    val message: String = "chave pix inválida. (\${validatedValue.tipoChave})",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = []
)

@Singleton
class ValidPixKeyValidador : ConstraintValidator<ValidKeyPix, NovaChavePixRequest> {

    override fun isValid(value: NovaChavePixRequest?, context: ConstraintValidatorContext?): Boolean {
        if (value?.tipoChave == null)
            return false
        return value.tipoChave.valida(value.chave)
    }

}
