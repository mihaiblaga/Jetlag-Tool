package ro.mihaiblaga.jetlagtool.models

sealed class SelectionMode {
    data object RegularSelectionMode : SelectionMode()
    data object PointSelectionMode : SelectionMode()
}