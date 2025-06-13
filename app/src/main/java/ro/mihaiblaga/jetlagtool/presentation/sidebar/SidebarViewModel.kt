package ro.mihaiblaga.jetlagtool.presentation.sidebar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ro.mihaiblaga.jetlagtool.data.repository.FakeAdministrativeDivisionRepositoryImpl
import ro.mihaiblaga.jetlagtool.domain.repository.AdministrativeDivisionRepository
import javax.inject.Inject

@HiltViewModel
class SidebarViewModel @Inject constructor(
    administrativeDivisionRepository: AdministrativeDivisionRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SidebarState())
    val state: StateFlow<SidebarState> = _state

    init {
        viewModelScope.launch {
            _state.value = SidebarState(
                items = administrativeDivisionRepository.getAdministrativeDivisions()
            )
        }
    }

}

val fakeSidebarViewModel = SidebarViewModel(FakeAdministrativeDivisionRepositoryImpl())