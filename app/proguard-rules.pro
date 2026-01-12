# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /files/getDefaultProguardFile('proguard-android-optimize.txt')
# which is part of the Android SDK.

# Keep AdMob classes
-keep class com.google.android.gms.ads.** { *; }
-keep class com.google.ads.** { *; }

# Keep AdMob interfaces
-keep public interface com.google.android.gms.ads.** { *; }

# For safe measure if using mediation (optional, but good practice)
-keepattributes SourceFile,LineNumberTable
