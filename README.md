# Examples of using Android sensors.

[<img src="doc/google-play-badge.png" height=75 align=center>](https://play.google.com/store/apps/details?id=com.reboot297.sensors)
[<img src="doc/amazon-appstore-badge-english-black.png" height=50 align=center>](http://www.amazon.com/gp/mas/dl/android?p=com.reboot297.sensors)

This project is an example of reading data from Android sensors.

For simplicity, I don't use view models, fragments, DI, etc. and I have each code example in a separate activity. So please look at this project not as a real project, but rather as a couple of samples.

In a real project, consider optimizing your code to suit your application's architecture.


# Examples of reading raw data from:

- [Ambient Temperature](mobile/src/main/java/com/reboot297/sensors/raw/environment/AmbientTemperatureDetailsActivity.kt)
- [Light](mobile/src/main/java/com/reboot297/sensors/raw/environment/LightDetailsActivity.kt)
- [Pressure](mobile/src/main/java/com/reboot297/sensors/raw/environment/PressureDetailsActivity.kt)
- [Relative Humidity](mobile/src/main/java/com/reboot297/sensors/raw/environment/RelativeHumidityDetailsActivity.kt)
- [Temperature](mobile/src/main/java/com/reboot297/sensors/raw/environment/TemperatureDetailsActivity.kt)

- [Accelerometer](mobile/src/main/java/com/reboot297/sensors/raw/motion/AccelerometerDetailsActivity.kt)
- [Accelerometer Uncalibrated](mobile/src/main/java/com/reboot297/sensors/raw/motion/AccelerometerUncalibratedDetailsActivity.kt)
- [Gravity](mobile/src/main/java/com/reboot297/sensors/raw/motion/GravityDetailsActivity.kt)
- [Gyroscope](mobile/src/main/java/com/reboot297/sensors/raw/motion/GyroscopeDetailsActivity.kt)
- [Gyroscope Uncalibrated](mobile/src/main/java/com/reboot297/sensors/raw/motion/GyroscopeUncalibratedDetailsActivity.kt)
- [Linear Acceleration](mobile/src/main/java/com/reboot297/sensors/raw/motion/LinearAccelerationDetailsActivity.kt)
- [Rotation Vector](mobile/src/main/java/com/reboot297/sensors/raw/motion/RotationVectorDetailsActivity.kt)
- [Significant Motions](mobile/src/main/java/com/reboot297/sensors/raw/motion/SignificantMotionsDetailsActivity.kt)
- [Step Counter](mobile/src/main/java/com/reboot297/sensors/raw/motion/StepCounterDetailsActivity.kt)
- [Step Detector](mobile/src/main/java/com/reboot297/sensors/raw/motion/StepDetectorDetailsActivity.kt)

- [Game Rotation Vector](mobile/src/main/java/com/reboot297/sensors/raw/position/GameRotationVectorDetailsActivity.kt)
- [Geomagnetic Rotation Vector](mobile/src/main/java/com/reboot297/sensors/raw/position/GeomagneticRotationVectorDetailsActivity.kt)
- [Magnetic Field](mobile/src/main/java/com/reboot297/sensors/raw/position/MagneticFieldDetailsActivity.kt)
- [Magnetic Field Uncalibrated](mobile/src/main/java/com/reboot297/sensors/raw/position/MagneticFieldUncalibratedDetailsActivity.kt)
- [Orientation](mobile/src/main/java/com/reboot297/sensors/raw/position/OrientationDetailsActivity.kt)
- [Proximity](mobile/src/main/java/com/reboot297/sensors/raw/position/ProximityDetailsActivity.kt)

# Other examples:

- [Altitude](mobile/src/main/java/com/reboot297/sensors/samples/altitude/AltitudeActivity.kt)
- [Device Orientation](mobile/src/main/java/com/reboot297/sensors/samples/orientation/DeviceOrientationActivity.kt)
- [Metal Detection](mobile/src/main/java/com/reboot297/sensors/samples/metal_detection/MetalDetectionActivity.kt)