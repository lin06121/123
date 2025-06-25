package com.example.myapplication



import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var buttonOpenAC: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 获取按钮并设置点击事件
        buttonOpenAC = findViewById(R.id.buttonOpenAC)
        buttonOpenAC.setOnClickListener {
            // 点击按钮时调用打开空调的函数
            openAC()
        }
    }

    // 发送 HTTP 请求打开空调
    private fun openAC() {
        Thread {
            try {
                // 替换为你的空调设备的 IP 地址和控制端点
                val url = URL("http://your_ac_device_ip/control") // 控制接口URL
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")

                // 发送 JSON 请求体，通知空调开启
                val jsonInputString = """{"action": "on"}"""
                connection.outputStream.use { os ->
                    val input = jsonInputString.toByteArray(Charsets.UTF_8)
                    os.write(input, 0, input.size)
                }

                // 获取响应码
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // 请求成功，显示成功消息
                    runOnUiThread {
                        Toast.makeText(this, "空调已打开", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    // 请求失败，显示失败消息
                    runOnUiThread {
                        Toast.makeText(this, "控制失败", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                // 网络错误，显示错误消息
                runOnUiThread {
                    Toast.makeText(this, "网络错误", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
