import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import custom.FileHandler
import custom.ValueSliderView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.Coordinate
import java.awt.GraphicsEnvironment
import java.awt.Rectangle
import java.awt.Robot
import java.awt.image.BufferedImage
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * Thread Pool
 */
val workerPool: ExecutorService = Executors.newSingleThreadExecutor()
var captureRegionCoordinate: Coordinate? = null

@Composable
@Preview
fun ScreenCapture() {
    var screenCapture by remember { mutableStateOf<ImageBitmap?>(null) }
    val graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
    var captureDimension by remember { mutableStateOf(loadSettings() ?: 250f) }
    val screenWidth = graphicsDevice.displayMode.width
    val screenHeight = graphicsDevice.displayMode.height
    val captureX = screenWidth - 270 // calculate the X coordinate of the capture region
    val captureY = screenHeight - 270 // calculate the Y coordinate of the capture region
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {

            // Wait for user to select the region to capture
            while (captureRegionCoordinate == null) {
                // Have to do something in order for loop to run in kotlin
                println()
            }
            workerPool.shutdown()
            val coordinate = captureRegionCoordinate as Coordinate
            val robot = Robot()
            while (true) {
                val screenRect = Rectangle(
                    coordinate.x,
                    coordinate.y,
                    captureDimension.toInt(),
                    captureDimension.toInt()
                ) // adjust to your screen resolution

                val screenCaptureImage = robot.createScreenCapture(screenRect)
                val bufferedImage = BufferedImage(
                    screenCaptureImage.width,
                    screenCaptureImage.height,
                    BufferedImage.TYPE_INT_ARGB
                )
                bufferedImage.graphics.drawImage(screenCaptureImage, 0, 0, null)
                val imageBitmap = bufferedImage.toComposeImageBitmap()
                screenCapture = imageBitmap
            }
        }
    }

    Box {
        Column {
            Column(modifier = Modifier.weight(1f)) {
                Image(
                    painter = screenCapture?.let { BitmapPainter(it) } ?: return@Column,
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            ValueSliderView(
                value = captureDimension,
                valueRange = 50f..400f,
                onValueChange = { newWidth ->
                    captureDimension = newWidth
                    saveSettings(newWidth)
                }
            )
        }
    }
}

fun loadSettings(): Float? {
    return FileHandler.readFileToObject(File("dimensions"))
}

fun saveSettings(dimension: Float) {
    FileHandler.writeObjectToFile(dimension, File(System.getProperty("user.dir"), "dimensions"))
}

fun startCaptureCoordinatesEvent() {
    var captureCoordinatesThread: CaptureCoordinatesEvent =
        CaptureCoordinatesEvent()
    // Start thread
    workerPool.execute(captureCoordinatesThread)
}

fun main() = application {
    startCaptureCoordinatesEvent()
    Window(
        title = "League of Legends Map",
        icon = painterResource("LoL_icon.png"),
        onCloseRequest = ::exitApplication
    )


    {
        ScreenCapture()
    }
}



