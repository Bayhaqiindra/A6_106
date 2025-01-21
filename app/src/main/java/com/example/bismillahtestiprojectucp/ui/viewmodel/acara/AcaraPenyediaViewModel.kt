package com.example.bismillahtestiprojectucp.ui.viewmodel.acara

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bismillahtestiprojectucp.EventApplications

object AcaraPenyediaViewModel {
    val Factory = viewModelFactory {
        initializer { HomeAcaraViewModel(acaraApp().container.acaraRepository) }
    }
}

fun CreationExtras.acaraApp(): EventApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EventApplications)