package ro.mihaiblaga.jetlagtool.presentation.sidebar

import ro.mihaiblaga.jetlagtool.data.model.AdministrativeDivision

data class SidebarState(
    val isSidebarOpen: Boolean = false,
    val items: List<AdministrativeDivision>
)
