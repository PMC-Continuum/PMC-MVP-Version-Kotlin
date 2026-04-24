package com.continuum.presentation.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.*
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    sealed class Child {
        object Landing      : Child()
        object RoleSelector : Child()
        object PatientApp   : Child()
        object DoctorApp    : Child()
        object FamilyApp    : Child()
        object ElderlyApp   : Child()
        object Home         : Child()
        object VoiceEntry   : Child()
        object Dashboard    : Child()
        object SOS          : Child()
    }
}

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val userId: String?
) : RootComponent, ComponentContext by componentContext {

    @Serializable
    sealed class Config {
        @Serializable data object Landing      : Config()
        @Serializable data object RoleSelector : Config()
        @Serializable data object PatientApp   : Config()
        @Serializable data object DoctorApp    : Config()
        @Serializable data object FamilyApp    : Config()
        @Serializable data object ElderlyApp   : Config()
        @Serializable data object Home         : Config()
        @Serializable data object VoiceEntry   : Config()
        @Serializable data object Dashboard    : Config()
        @Serializable data object SOS          : Config()
    }

    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.RoleSelector,
        handleBackButton = true,
        childFactory = ::createChild
    )

    private fun createChild(config: Config, @Suppress("UNUSED_PARAMETER") ctx: ComponentContext) =
        when (config) {
            Config.Landing      -> RootComponent.Child.Landing
            Config.RoleSelector -> RootComponent.Child.RoleSelector
            Config.PatientApp   -> RootComponent.Child.PatientApp
            Config.DoctorApp    -> RootComponent.Child.DoctorApp
            Config.FamilyApp    -> RootComponent.Child.FamilyApp
            Config.ElderlyApp   -> RootComponent.Child.ElderlyApp
            Config.Home         -> RootComponent.Child.Home
            Config.VoiceEntry   -> RootComponent.Child.VoiceEntry
            Config.Dashboard    -> RootComponent.Child.Dashboard
            Config.SOS          -> RootComponent.Child.SOS
        }

    fun navigateTo(config: Config) = navigation.push(config)
    fun goBack() = navigation.pop()
    fun replaceWith(config: Config) = navigation.replaceCurrent(config)
}
