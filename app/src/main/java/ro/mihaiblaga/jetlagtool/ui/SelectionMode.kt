package ro.mihaiblaga.jetlagtool.ui

sealed class SelectionMode {
    class RegularSelectionMode() : SelectionMode()
    class PointSelectionMode() : SelectionMode()
}