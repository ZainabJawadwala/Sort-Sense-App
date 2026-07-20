# Walkthrough - Enabling App Build and View

I have resolved the remaining build blockers so you can now run and view the app.

## Changes Made

### [google-services.json](file:///C:/Users/Lenovo/OneDrive/Desktop/SortSense_app/app/google-services.json) [NEW]
- Added a **placeholder** `google-services.json` file. This allows the app to build without a real Firebase configuration. Note that Firebase features (like real login or database) won't work until you replace this with your actual file from the Firebase Console.

### [AndroidManifest.xml](file:///C:/Users/Lenovo/OneDrive/Desktop/SortSense_app/app/src/main/AndroidManifest.xml)
- **Android 12+ Compatibility**: Added `android:exported="true"` to the `SplashScreen` activity. This is mandatory for apps targeting Android 12 or higher when an activity has an intent filter.

### Java Source Code
- **Non-final Resource IDs**: Converted `switch` statements to `if-else` blocks in [SearchingActivity.java](file:///C:/Users/Lenovo/OneDrive/Desktop/SortSense_app/app/src/main/java/com/sortsense/app/activities/SearchingActivity.java) and [SortingActivity.java](file:///C:/Users/Lenovo/OneDrive/Desktop/SortSense_app/app/src/main/java/com/sortsense/app/activities/SortingActivity.java). Modern Android Gradle Plugins make resource IDs non-final for better build performance, which prevents them from being used in `switch` cases.

## Current Status

> [!TIP]
> **The project now builds successfully!** You can click the **Run** button (green play icon) in Android Studio to deploy it to your device or emulator.

## Verification Results

- **Build Result**: `BUILD SUCCESSFUL`
- **Output Artifact**: `app-debug.apk` generated.
