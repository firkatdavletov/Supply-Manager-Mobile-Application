package org.example.project.feature.map_view

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraListener
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.ui_view.ViewProvider
import org.example.project.domain.models.DepartmentModel
import org.example.project.features.map.UiPoint
import java.util.Calendar

@Composable
fun YandexMapView(
    context: Context,
    position: UiPoint?,
    moveToLocation: Boolean,
    showDepartments: Boolean,
    selectedDepartment: Int?,
    departments: List<DepartmentModel>,
    modifier: Modifier = Modifier,
    onMapMoved: (lat: Double, lon: Double, reason: Int, finished: Boolean) -> Unit,
    onSelectDepartment: (Int) -> Unit,
) {
    val cameraListener by remember {
        mutableStateOf(CameraListener { map, cameraPosition, reason, finished ->
            onMapMoved(
                cameraPosition.target.latitude,
                cameraPosition.target.longitude,
                reason.ordinal,
                finished
            )
        })
    }

    val clusterListener by remember {
        mutableStateOf(ClusterListener { cluster ->
            cluster.appearance.setView(
                ViewProvider(
                    DepartmentMapObjView(context)
                )
            )
        })
    }

    val _mapObjects = remember {
        mutableMapOf<Int, PlacemarkMapObject>()
    }

    val mapObjectTapListener by remember {
        mutableStateOf(MapObjectTapListener { obj, point ->
            val id = obj.userData as Int
            if (id != selectedDepartment) {
                onSelectDepartment(id)
            }
            true
        })
    }

    var clusterizedCollection by remember {
        mutableStateOf<ClusterizedPlacemarkCollection?>(null)
    }

    val currentDayOfWeek = remember {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
        dayOfWeek
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    AndroidView(
        modifier = modifier,
        factory = { context ->
            val mapView = MapView(context)

            lifecycle.addObserver(object : DefaultLifecycleObserver {
                override fun onStart(owner: LifecycleOwner) {
                    MapKitFactory.getInstance().onStart()
                    mapView.onStart()
                }

                override fun onStop(owner: LifecycleOwner) {
                    mapView.onStop()
                    MapKitFactory.getInstance().onStop()
                }
            })

            mapView.mapWindow.map.addCameraListener(cameraListener)
            clusterizedCollection = mapView.mapWindow.map.mapObjects.addClusterizedPlacemarkCollection(clusterListener)
//            val point = Point(53.967621, 58.410023)//53.967621, 58.410023
//            val anim = Animation(Animation.Type.SMOOTH, 0.5f)
//            mapView.mapWindow.map.move(CameraPosition(point, 10f, 0.0f, 0.0f), anim) {}
            mapView
        },
        update = { mapView ->
            if (moveToLocation && position != null) {
                val point = Point(position.latitude, position.longitude)
                val anim = Animation(Animation.Type.SMOOTH, 0.5f)
                mapView.mapWindow.map.move(CameraPosition(point, 15f, 0.0f, 0.0f), anim) {}
            }

            if (showDepartments && _mapObjects.size != departments.size) {
                clusterizedCollection?.clear()
                _mapObjects.clear()

                departments.forEach { department ->
                    val point = Point(department.latitude, department.longitude)
                    val mark = clusterizedCollection!!.addPlacemark()
                    val selected = selectedDepartment == department.id
                    mark.geometry = point
                    mark.isVisible = true
                    mark.isDraggable = false
                    mark.userData = department.id
                    mark.setView(
                        ViewProvider(
                            DepartmentMapObjView(context)
                        )
                    )
                    mark.addTapListener(mapObjectTapListener)
                    _mapObjects[department.id] = mark
                }
                clusterizedCollection?.clusterPlacemarks(55.0, 17)
            } else if (!showDepartments) {
                clusterizedCollection?.clear()
                _mapObjects.clear()
            }
        },
        onRelease = { mapView ->
            mapView.onStop()
            MapKitFactory.getInstance().onStop()
        }
    )
}