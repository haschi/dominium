package com.github.haschi.haushaltsbuch.haushaltsbuch.backend.rest.serialization

import org.junit.jupiter.api.extension.Extension
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.junit.jupiter.api.extension.TestTemplateInvocationContext
import org.junit.jupiter.api.extension.TestTemplateInvocationContextProvider
import java.util.stream.Stream
import kotlin.reflect.KClass

abstract class TestfallAnbieter<T: Any> : TestTemplateInvocationContextProvider
{
    override fun supportsTestTemplate(context: ExtensionContext?): Boolean = true

    override fun provideTestTemplateInvocationContexts(context: ExtensionContext?): Stream<TestTemplateInvocationContext>
    {
        return testfälle()
                .map { testfall(it) }
                .stream()
    }

    private fun testfall(klasse: Testfall<T>): TestTemplateInvocationContext
    {
        return object : TestTemplateInvocationContext
        {
            override fun getDisplayName(invocationIndex: Int): String
            {
                return "Testfall $invocationIndex: $klasse"
            }

            override fun getAdditionalExtensions(): MutableList<Extension>
            {
                return mutableListOf(object : ParameterResolver
                {
                    override fun supportsParameter(p0: ParameterContext, p1: ExtensionContext?): Boolean
                    {
                        return p0.parameter.type.isAssignableFrom(Testfall::class.java)
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


    abstract fun testfälle(): Iterable<Testfall<T>>
}