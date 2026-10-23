# ──────────────────────────────────────────────────────────────────────────────
# Flutter & Core Android Rules
# ──────────────────────────────────────────────────────────────────────────────
-keep class io.flutter.** { *; }
-dontwarn io.flutter.embedding.**
-keepattributes *Annotation*,Signature,InnerClasses,EnclosingMethod

# Keep all BroadcastReceivers and Services (instantiated dynamically by system)
-keep public class * extends android.content.BroadcastReceiver
-keepclassmembers public class * extends android.content.BroadcastReceiver {
    public void onReceive(android.content.Context, android.intent.Intent);
}
-keep public class * extends android.app.Service

# ── Multidex & Desugaring ─────────────────────────────────────────────────────
-keep class androidx.multidex.** { *; }
-dontwarn j$.**

# ── Firebase & Google Auth ─────────────────────────────────────────────────────
-keep class com.google.firebase.** { *; }
-dontwarn com.google.firebase.**
-keep class com.google.android.gms.auth.api.signin.** { *; }
-dontwarn com.google.android.gms.auth.api.signin.**
-keep class com.google.android.gms.** { *; }
-dontwarn com.google.android.gms.**

# ── Kotlin & Coroutines ───────────────────────────────────────────────────────
-keep class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**
-keep class kotlin.** { *; }
-dontwarn kotlin.**

# ── Agora RTC Engine (Live Streaming & Video Calling) ─────────────────────────
-keep class io.agora.** { *; }
-dontwarn io.agora.**

# ── Payment SDKs ──────────────────────────────────────────────────────────────
# Razorpay
-keep class com.razorpay.** { *; }
-dontwarn com.razorpay.**

# Stripe
-keep class com.stripe.android.** { *; }
-dontwarn com.stripe.android.**

# Flutterwave
-keep class com.flutterwave.raveandroid.** { *; }
-dontwarn com.flutterwave.raveandroid.**

# In-App Purchase (Google Play Billing)
-keep class com.android.vending.billing.** { *; }
-keep class com.android.billingclient.api.** { *; }

# ── Notifications ─────────────────────────────────────────────────────────────
-keep class me.carda.awesome_notifications.** { *; }
-keepclassmembers class me.carda.awesome_notifications.** { *; }
-dontwarn me.carda.awesome_notifications.**

-keep class com.dexterous.flutterlocalnotifications.** { *; }
-dontwarn com.dexterous.flutterlocalnotifications.**

# ── Networking & WebSockets (Socket.IO & OkHttp) ──────────────────────────────
-keep class io.socket.** { *; }
-dontwarn io.socket.**
-keep class okhttp3.** { *; }
-dontwarn okhttp3.**

# ── Gson & Serialization ──────────────────────────────────────────────────────
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# ── Facebook SDK & Auth ───────────────────────────────────────────────────────
-keep class com.facebook.** { *; }
-dontwarn com.facebook.**

# ── Camera, Animations & Media (CameraX, Lottie) ─────────────────────────────
-keep class androidx.camera.** { *; }
-dontwarn androidx.camera.**

-keep class com.airbnb.lottie.** { *; }
-dontwarn com.airbnb.lottie.**
