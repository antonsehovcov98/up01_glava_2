package com.hfad.up_glava2_shehovcov

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel

private val FormUiEvent.OnPhoneChange.phone: Any
    get() {}

object it {

}

abstract class FormUiEvent {
    class OnNameChange {

    }

    class OnPhoneChange {

    }

    abstract var name: Any
}

class FormUiState {

}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    class OrderViewModel : ViewModel() {

        // UI's states
        var formUiState by mutableStateOf(FormUiState())
            private set
        // Business's logic or maybe some UI's logic for update the state
        companion object {
            fun applyLogicToValidateInputs(name: String, phone: String): Boolean {
                return name.length > 1 && phone.length > 3
            }
        }
        //UI's Events
        fun onFormEvent(formEvent: FormUiEvent) {
            when (formEvent) {
                is FormUiEvent.OnNameChange -> {
                    formUiState = formUiState.copy(name = formEvent.name)
                }
                is FormUiEvent.OnPhoneChange -> {
                    formUiState = formUiState.copy(phone = formEvent.phone)
                }
                is FormUiEvent.PayOrderClicked -> {
                    println("Sending form with parameters:
                        ${formUiState.name} and ${formUiState.phone}")
                }
            }
        }
    }
    data class FormUiState(
        val name: String = "",
        val phone: String = ""
    )

    val FormUiState.successValidated: Boolean get() =
        OrderViewModel.applyLogicToValidateInputs(name, phone)
}
