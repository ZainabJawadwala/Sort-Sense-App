# SortSense

An Android app that visualizes sorting and search algorithms, with a Q&A section (post doubts, comment, discuss) backed by Firebase.

Adapted from the original open-source project "Algorithm Visualizer and Doubt Solver" — package, app name, and identifiers renamed; core logic unchanged.

---

### Features

**Algorithm Visualizer**
- Bubble Sort
- Selection Sort
- Insertion Sort
- Merge Sort
- Quick Sort
- Linear Search
- Binary Search

**Doubt Solver**
- Sign up / log in (Firebase Authentication)
- Post questions ("doubts")
- Comment on posts
- View your own posts

---

### Tech stack

- Java (Android)
- Firebase Authentication
- Firebase Firestore
- Firebase Analytics
- FirebaseUI (Firestore bindings)
- Lottie (animations)
- Country Code Picker (CCP)
- AndroidX AppCompat / Material Components / ConstraintLayout

---

### Setup — required before this will build

The Doubt Solver features need your own Firebase project (this is not included, for security reasons — every developer needs their own):

1. Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project
2. Add an Android app to that project with package name: `com.sortsense.app`
3. Download the generated `google-services.json`
4. Place it in the `app/` folder of this project (same level as `app/build.gradle`)
5. In the Firebase Console, enable:
   - **Authentication** → Email/Password (or whichever sign-in method you want)
   - **Firestore Database** → create a database in production or test mode

---

### How to run

1. Open this project folder in **Android Studio**
2. Let Gradle sync (first sync may take a few minutes)
3. Complete the Firebase setup above — the build will fail without `google-services.json`
4. Connect an Android device (USB debugging enabled) or start an emulator via AVD Manager
5. Click **Run ▶**

---

### Package / identifiers

- Package name: `com.sortsense.app`
- App display name: `SortSense`
