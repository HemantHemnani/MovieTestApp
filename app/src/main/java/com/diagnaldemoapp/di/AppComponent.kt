package com.diagnaldemoapp.di

import android.app.Application
import com.diagnaldemoapp.AppApplication
import com.diagnaldemoapp.di.modules.AppModule
import com.diagnaldemoapp.di.modules.MainActivityModule

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainActivityModule::class
    ]
)
interface AppComponent : AndroidInjector<AppApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(app: AppApplication)
}
