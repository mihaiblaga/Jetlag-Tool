package ro.mihaiblaga.jetlagtool.presentation.sidebar.util

import ro.mihaiblaga.jetlagtool.domain.model.AdministrativeDivision

sealed class SidebarEvent {
    data class OnItemClick(val item: AdministrativeDivision) : SidebarEvent()

}
