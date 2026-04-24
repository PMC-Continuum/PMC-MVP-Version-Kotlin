package com.continuum.presentation.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        object Landing    : Child()
        object Home       : Child()
        object VoiceEntry : Child()
        object Dashboard  : Child()
        object SOS        : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val userId: String?
) : RootComponent, ComponentContext by componentContext {

    @Serializable
    sealed class Config {
        @Serializable data object Landing    : Config()
        @Serializable data object Home       : Config()
        @Serializable data object VoiceEntry : Config()
        @Serializable data object Dashboard  : Config()
        @Serializable data object SOS        : Config()
    }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = if (userId != null) Config.Home else Config.Landing,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, @Suppress("UNUSED_PARAMETER") ctx: ComponentContext) =
        when (config) {
            Config.Landing    -> RootComponent.Child.Landing
            Config.Home       -> RootComponent.Child.Home
            Config.VoiceEntry -> RootComponent.Child.VoiceEntry
            Config.Dashboard  -> RootComponent.Child.Dashboard
            Config.SOS        -> RootComponent.Child.SOS
        }

    fun navigateTo(config: Config) = navigation.push(config)
    fun goBack() = navigation.pop()
}
