# Ktello

## A wrapper class for DJI / Ryzetech Tello SDK Mini Drone using Kotlin programming language


Sample for a takeoff, hover for some seconds and land.
```kotlin
fun main(args: Array<String>) {
    val tello = KTello()
    tello.connect()
    Thread.sleep(3000)
    if (tello.isConnected) {
        tello.takeOff()
        Thread.sleep(10000)
        tello.land()
    }
    tello.close()
}
```
# References:

https://dl-cdn.ryzerobotics.com/downloads/tello/0228/Tello+SDK+Readme.pdf

https://dl-cdn.ryzerobotics.com/downloads/tello/20180222/Tello3.py
