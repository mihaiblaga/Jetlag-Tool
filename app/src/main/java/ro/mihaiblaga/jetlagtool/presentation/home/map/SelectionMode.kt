package ro.mihaiblaga.jetlagtool.presentation.home.map

sealed class SelectionMode {
    data object RegularSelectionMode : SelectionMode()
    data object PointSelectionMode : SelectionMode()
}