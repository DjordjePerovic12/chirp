package llc.bokadev.chirp.di

import llc.bokadev.auth.presentation.di.authPresentationModule
import llc.bokadev.chat.data.di.chatDataModule
import llc.bokadev.chat.presentation.di.chatPresentationModule
import llc.bokadev.core.data.di.coreDataModule
import llc.bokadev.core.presentation.di.corePresentationModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule,
            chatPresentationModule,
            corePresentationModule,
            chatDataModule
        )
    }
}