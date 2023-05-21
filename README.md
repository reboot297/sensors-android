# Examples of using Android sensors.

### Android mobile version
[<img src="doc/google-play-badge.png" height=75 align=center>](https://play.google.com/store/apps/details?id=com.reboot297.sensors)
[<img src="doc/amazon-appstore-badge-english-black.png" height=50 align=center>](http://www.amazon.com/gp/mas/dl/android?p=com.reboot297.sensors)

### Version for android Wear
[<img src="doc/google-play-badge.png" height=75 align=center>](https://play.google.com/store/apps/details?id=com.reboot297.sensors.wear)

This project is an example of reading data from Android sensors.

For simplicity, I don't use view models, fragments, DI, etc. and I have each code example in a separate activity. So please look at this project not as a real project, but rather as a couple of samples.

In a real project, consider optimizing your code to suit your application's architecture.


## Examples of reading raw data from:

- [Ambient Temperature](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/AmbientTemperaturelifecycleObserver.kt)
- [Light](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/LightLifecycleObserver.kt)
- [Pressure](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/PressureLifecycleObserver.kt)
- [Relative Humidity](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/RelativeHumidityLifecycleObserver.kt)
- [Temperature](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/TemperatureLifecycleObserver.kt)

- [Accelerometer](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/AccelerometerLifecycleObserver.kt)
- [Accelerometer Uncalibrated](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/AccelerometerUncalibratedLifecycleObserver.kt)
- [Gravity](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/GravityLifecycleObserver.kt)
- [Gyroscope](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/GyroscopeLifecycleObserver.kt)
- [Gyroscope Uncalibrated](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/GyroscopeUncalibratedLifecycleObserver.kt)
- [Linear Acceleration](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/LinearAccelerationLifecycleObserver.kt)
- [Rotation Vector](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/RotationVectorLifecycleObserver.kt)
- [Significant Motions](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/SignificantMotionsLifecycleObserver.kt)
- [Step Counter](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/StepCounterLifecycleObserver.kt)
- [Step Detector](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/raw/StepDetectorLifecycleObserver.kt)

- [Game Rotation Vector](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/GameRotationVectorLifecycleObserver.kt)
- [Geomagnetic Rotation Vector](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/GeomagneticRotationVectorLifecycleObserver.kt)
- [Magnetic Field](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/MagneticFieldLifecycleObserver.kt)
- [Magnetic Field Uncalibrated](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/MagneticFieldUncalibratedLifecycleObserver.kt)
- [Orientation](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/OrientationLifecycleObserver.kt)
- [Proximity](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/raw/ProximityLifecycleObserver.kt)

## Other examples:

- [Altitude](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/samples/AltitudeLifecycleObserver.kt)
- [Device Orientation](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/samples/DeviceOrientationLifecycleObserver.kt)
- [Metal Detection](lib_mobilie_sensors/src/main/kotlin/com/reboot297/sensors/samples/MetalDetectionLifecycleObserver.kt)