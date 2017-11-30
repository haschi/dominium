package com.github.haschi.haushaltsbuch.testing.sandbox

import nl.jqno.equalsverifier.EqualsVerifier
import org.github.haschi.haushaltsbuch.api.BeendeInventur
import org.github.haschi.haushaltsbuch.api.BeginneHaushaltsbuchführung
import org.github.haschi.haushaltsbuch.api.BeginneInventur
import org.github.haschi.haushaltsbuch.api.ErfasseInventar
import org.github.haschi.haushaltsbuch.api.ErfasseSchulden
import org.github.haschi.haushaltsbuch.api.ErfasseUmlaufvermögen
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestTemplate
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.jupiter.api.extension.TestTemplateInvocationContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider
import java.util.stream.Stream
import kotlin.reflect.KClass

@DisplayName("Äquivalenzregeln der Wertobjekte prüfen")
class AequivalenzTest
{
    @TestTemplate
    @ExtendWith(DatenklassenProvider::class, AnweisungenProvider::class)
    fun aequivalenz(testklasse: KClass<*>)
    {
        EqualsVerifier.forClass(testklasse.java).verify()
    }

    class DatenklassenProvider : TestTemplateInvocationContextProvider
    {
        override fun supportsTestTemplate(context: ExtensionContext?): Boolean = true

        override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext>
        {
            return Stream.of(
                    KlasseA::class,
                    KlasseB::class,
                    KlasseC::class,
                    KlasseD::class,
                    KlasseE::class)
                    .map { testfall(it) }
        }

        private fun testfall(klasse: KClass<*>): TestTemplateInvocationContext
        {
            return object : TestTemplateInvocationContext
            {
                override fun getDisplayName(invocationIndex: Int): String
                {
                    return klasse.qualifiedName.orEmpty()
                }

                override fun getAdditionalExtensions(): MutableList<Extension>
                {
                    return mutableListOf(object : ParameterResolver
                    {
                        override fun supportsParameter(p0: ParameterContext, p1: ExtensionContext?): Boolean
                        {
                            return p0.parameter.type.isAssignableFrom(KClass::class.java)
                        }

                        override fun resolveParameter(
                                parameter: ParameterContext?,
                                extension: ExtensionContext?): Any
                        {
                            return klasse
                        }
                    })
                }
            }
        }
    }

    class AnweisungenProvider : TestTemplateInvocationContextProvider
    {
        override fun supportsTestTemplate(context: ExtensionContext?): Boolean = true

        override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext>
        {
            return Stream.of(
                    BeendeInventur::class,
                    BeginneHaushaltsbuchführung::class,
                    BeginneInventur::class,
                    ErfasseInventar::class,
                    ErfasseSchulden::class,
                    ErfasseUmlaufvermögen::class)

                    .map { testfall(it) }
        }

        private fun testfall(klasse: KClass<*>): TestTemplateInvocationContext
        {
            return object : TestTemplateInvocationContext
            {
                override fun getDisplayName(invocationIndex: Int): String
                {
                    return klasse.qualifiedName.orEmpty()
                }

                override fun getAdditionalExtensions(): MutableList<Extension>
                {
                    return mutableListOf(object : ParameterResolver
                    {
                        override fun supportsParameter(p0: ParameterContext, p1: ExtensionContext?): Boolean
                        {
                            return p0.parameter.type.isAssignableFrom(KClass::class.java)
                        }

                        override fun resolveParameter(
                                parameter: ParameterContext?,
                                extension: ExtensionContext?): Any
                        {
                            return klasse
                        }
                    })
                }
            }
        }
    }

}