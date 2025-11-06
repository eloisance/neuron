package com.neuron.di

import com.neuron.domain.usecase.GetChallengeUseCase
import com.neuron.ui.viewmodel.ChallengeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    factory {
        GetChallengeUseCase()
    }

    viewModel {
        ChallengeViewModel(getChallengeUseCase = get())
    }
}
