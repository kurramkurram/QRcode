package io.github.kurramkurram.qrcode

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.google.zxing.integration.android.IntentIntegrator
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    private val activityResultCallback = ActivityResultCallback<ActivityResult> {
        if (it.resultCode == Activity.RESULT_OK) {
            val result = IntentIntegrator.parseActivityResult(it.resultCode, it.data)
            if (it.data != null) {
                Log.d(TAG, "#ActivityResultCallback result = $result")
                Log.d(TAG, "#ActivityResultCallback result = ${result.contents}")
            }
        }
    }

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(), activityResultCallback
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.start_qr_button).apply {
            setOnClickListener {
                IntentIntegrator(this@MainActivity).apply {
                    captureActivity = ScannerActivity::class.java
                    setBeepEnabled(false)
                    setTorchEnabled(true)
                    createScanIntent().apply {
                        activityResultLauncher.launch(this)
                    }
                }
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}