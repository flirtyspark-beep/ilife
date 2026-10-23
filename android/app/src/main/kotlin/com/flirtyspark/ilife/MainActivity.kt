package com.flirtyspark.ilife

import android.app.PictureInPictureParams
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Rational
import android.view.WindowManager
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity : FlutterFragmentActivity() {

    private val CHANNEL = "com.flirtyspark.ilife/pip"
    private var methodChannel: MethodChannel? = null
    private var isPipEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )

        updateTurnScreenOnAndShowWhenLocked()
    }

    private fun updateTurnScreenOnAndShowWhenLocked() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            )
        }
    }

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL)
        methodChannel?.setMethodCallHandler { call, result ->
            when (call.method) {
                "enablePip" -> {
                    isPipEnabled = true
                    result.success(true)
                }
                "disablePip" -> {
                    isPipEnabled = false
                    result.success(true)
                }
                "enterPip" -> {
                    val entered = enterPipMode()
                    result.success(entered)
                }
                "isPipSupported" -> {
                    result.success(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                }
                "isInPipMode" -> {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        result.success(isInPictureInPictureMode)
                    } else {
                        result.success(false)
                    }
                }
                else -> result.notImplemented()
            }
        }
    }

    private fun enterPipMode(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                val aspectRatio = Rational(9, 16)
                val params = PictureInPictureParams.Builder()
                    .setAspectRatio(aspectRatio)
                    .build()
                return enterPictureInPictureMode(params)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return false
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (isPipEnabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            enterPipMode()
        }
    }

    override fun onResume() {
        super.onResume()
        updateTurnScreenOnAndShowWhenLocked()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val inPip = isInPictureInPictureMode
            methodChannel?.invokeMethod("onPipModeChanged", inPip)
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        methodChannel?.invokeMethod("onPipModeChanged", isInPictureInPictureMode)
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode)
        methodChannel?.invokeMethod("onPipModeChanged", isInPictureInPictureMode)
    }
}