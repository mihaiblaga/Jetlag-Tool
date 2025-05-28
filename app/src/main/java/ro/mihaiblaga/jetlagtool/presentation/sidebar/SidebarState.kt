package ro.mihaiblaga.jetlagtool.presentation.sidebar

import ro.mihaiblaga.jetlagtool.data.local.entity.AdministrativeDivisionEntity

data class SidebarState(
    val isSidebarOpen: Boolean = false,
    val items: List<AdministrativeDivisionEntity>
)
