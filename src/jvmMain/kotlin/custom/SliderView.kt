package custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
@Composable
fun ValueSliderView(
    value: Float,
    valueRange: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
) {
    var sliderValue by remember { mutableStateOf(value) }

    val sliderColors = SliderDefaults.colors(
        thumbColor = MaterialTheme.colors.primary,
        activeTrackColor = MaterialTheme.colors.primary,
        inactiveTrackColor = Color.Gray
    )


    Column(modifier = modifier.background(Color.Black).padding(8.dp)) {
        Text("Selected dimensions: ${sliderValue.toInt()}", style = TextStyle(Color.White))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "${valueRange.start}", style = TextStyle(Color.White))
            Spacer(modifier = Modifier.width(16.dp))

            Slider(
                value = sliderValue,
                onValueChange = {
                    if (it >= valueRange.start && it <= valueRange.endInclusive) {
                        sliderValue = it
                        onValueChange(sliderValue)
                    }
                },
                valueRange = valueRange,
                colors = sliderColors,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${valueRange.endInclusive.toInt()}", style = TextStyle(Color.White))
        }
    }

}