package ro.mihaiblaga.jetlagtool

sealed class MapAction {
    object Clear: MapAction()
    object DrawLine: MapAction()
}