package com.example.project

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.ComponentActivity
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.project.ui.theme.ProjectTheme
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.chart.scroll.rememberChartScrollSpec
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.formatter.DecimalFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.scroll.AutoScrollCondition
import com.patrykandpatrick.vico.core.scroll.InitialScroll


class ChartActivity : ComponentActivity() {
    private lateinit var sensorManager: SensorManager
    private val accVal = mutableStateListOf<List<Float>>()
    private val accListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                accVal.add(event.values.toList())
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }



    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(accListener)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also { accelerometer ->
            sensorManager.registerListener(
                accListener,
                accelerometer,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }
}

@Composable
fun AccChartScreen(
    modifier: Modifier = Modifier,
    accVal: SnapshotStateList<List<Float>>,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "X: %.2f, Y: %.2f, Z: %.2f".format(
                accVal.lastOrNull()?.get(0) ?: 0f,
                accVal.lastOrNull()?.get(1) ?: 0f,
                accVal.lastOrNull()?.get(2) ?: 0f,
            ),
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth(),
            data = accVal.map { it[0] }
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth(),
            data = accVal.map { it[1] }
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth(),
            data = accVal.map { it[2] }
        )
    }
}

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    data: List<Float>,
) {
    val producer = remember { ChartEntryModelProducer() }

    // 當 data 有變動時，更新圖表資料
    LaunchedEffect(data) {
        if (data.isNotEmpty()) {
            producer.setEntries(data.mapIndexed { idx, v ->
                FloatEntry(idx.toFloat(), v)
            })
        } else {
            producer.setEntries(listOf(FloatEntry(0f, 0.0f)))
        }
    }

    Chart(
        modifier = modifier,
        chart = lineChart(
            // 設定每筆資料的 X 軸間距為螢幕寬度的 1/50，但因為圖表包含Y軸寬度，因此實際能容納的資料小於 50 筆
            spacing = LocalConfiguration.current.screenWidthDp.dp / 50,
            axisValuesOverrider = AxisValuesOverrider.fixed(
                // 設定 Y 軸最大值為最後 50 筆資料的最大值+0.1
                maxY = data.takeLast(50).maxOrNull()?.plus(0.1f) ?: 20f,
                // 設定 Y 軸最小值為最後 50 筆資料的最小值-0.1
                minY = data.takeLast(50).minOrNull()?.minus(0.1f) ?: -20f,
            )
        ),
        chartModelProducer = producer,
        chartScrollSpec = rememberChartScrollSpec(
            // 設定圖表初始捲動位置為最後一筆資料
            initialScroll = InitialScroll.End,
            // 設定當圖表資料增加時自動捲動
            autoScrollCondition = AutoScrollCondition.OnModelSizeIncreased,
        ),
        // 設定圖表資料更新時的動畫效果
        diffAnimationSpec = tween(0),
        startAxis = rememberStartAxis(
            valueFormatter = DecimalFormatAxisValueFormatter(
                // 設定 Y 軸數字格式為小數點後兩位
                pattern = "0.00",
            ),
        ),
        bottomAxis = rememberBottomAxis(
            itemPlacer = AxisItemPlacer.Horizontal.default(
                // 設定 X 軸每幾個間距顯示一個數字
                spacing = 10,
            ),
        ),
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAccChartScreen() {
    ProjectTheme {
        // Creating a sample data set to preview the chart
        val sampleData = remember {
            mutableStateListOf(
                listOf(0.1f, 0.2f, 0.3f),
                listOf(0.2f, 0.3f, 0.4f),
                listOf(0.3f, 0.4f, 0.5f)
            )
        }

        AccChartScreen(
            accVal = sampleData
        )
    }
}


