package llc.bokadev.chirp.di

import llc.bokadev.auth.presentation.di.authPresentationModule
import llc.bokadev.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appModule
        )
    }
}