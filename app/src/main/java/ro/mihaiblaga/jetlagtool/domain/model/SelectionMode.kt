package ro.mihaiblaga.jetlagtool.domain.model

sealed class SelectionMode {
    data object RegularSelectionMode : SelectionMode()
    data object PointSelectionMode : SelectionMode()
}