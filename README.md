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

- [Ambient Temperature](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/AmbientTemperatureLifecycleObserver.kt)
- [Light](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/LightLifecycleObserver.kt)
- [Pressure](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/PressureLifecycleObserver.kt)
- [Relative Humidity](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/RelativeHumidityLifecycleObserver.kt)
- [Temperature](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/TemperatureLifecycleObserver.kt)

- [Accelerometer](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/AccelerometerLifecycleObserver.kt)
- [Accelerometer Uncalibrated](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/AccelerometerUncalibratedLifecycleObserver.kt)
- [Gravity](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/GravityLifecycleObserver.kt)
- [Gyroscope](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/GyroscopeLifecycleObserver.kt)
- [Gyroscope Uncalibrated](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/GyroscopeUncalibratedLifecycleObserver.kt)
- [Linear Acceleration](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/LinearAccelerationLifecycleObserver.kt)
- [Rotation Vector](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/RotationVectorLifecycleObserver.kt)
- [Significant Motions](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/SignificantMotionLifecycleObserver.kt)
- [Step Counter](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/StepCounterLifecycleObserver.kt)
- [Step Detector](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/StepDetectorLifecycleObserver.kt)

- [Game Rotation Vector](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/GameRotationVectorLifecycleObserver.kt)
- [Geomagnetic Rotation Vector](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/GeomagneticRotationVectorLifecycleObserver.kt)
- [Magnetic Field](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/MagneticFieldLifecycleObserver.kt)
- [Magnetic Field Uncalibrated](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/MagneticFieldUncalibratedLifecycleObserver.kt)
- [Orientation](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/OrientationLifecycleObserver.kt)
- [Proximity](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/raw/ProximityLifecycleObserver.kt)

## Other examples:

- [Altitude](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/samples/AltitudeLifecycleObserver.kt)
- [Device Orientation](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/samples/DeviceOrientationLifecycleObserver.kt)
- [Metal Detection](lib_mobile_sensors/src/main/kotlin/com/reboot297/sensors/lib/samples/MetalDetectionLifecycleObserver.kt)