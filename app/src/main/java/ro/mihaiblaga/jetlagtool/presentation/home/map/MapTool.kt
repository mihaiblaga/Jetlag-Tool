package ro.mihaiblaga.jetlagtool.presentation.home.map

sealed class MapTool {
    data object Regular : MapTool()
    data object Line : MapTool()
    data object Circle : MapTool()
    data object Point : MapTool()
}