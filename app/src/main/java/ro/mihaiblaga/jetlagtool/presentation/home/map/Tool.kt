package ro.mihaiblaga.jetlagtool.presentation.home.map

sealed class Tool {
    data object Regular : Tool()
    data object Line : Tool()
    data object Circle : Tool()
    data object Point : Tool()
}