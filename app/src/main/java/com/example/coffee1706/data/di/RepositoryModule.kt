package com.example.coffee1706.data.di

import com.example.coffee1706.data.repository.AuthorizationUserRepositoryImpl
import com.example.coffee1706.data.repository.CoffeeRepositoryImpl
import com.example.coffee1706.domain.repository.AuthorizationUserRepository
import com.example.coffee1706.domain.repository.CoffeeRepository
import com.example.coffee1706.ui.screens.authorization.common.ValidateRegistration
import com.example.coffee1706.ui.screens.authorization.common.ValidateRegistrationImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindAuthorizationUserRepository(repo: AuthorizationUserRepositoryImpl): AuthorizationUserRepository

    @Binds
    abstract fun bindPasswordValidation(repo: ValidateRegistrationImpl): ValidateRegistration

    @Binds
    abstract fun bindCoffeeRepository(repo: CoffeeRepositoryImpl): CoffeeRepository
}
