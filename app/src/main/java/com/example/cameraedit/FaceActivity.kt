package com.example.cameraedit

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import android.hardware.display.DisplayManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceContour
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.google.mlkit.vision.face.FaceLandmark

class FaceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.act_face)

        val RelativeLayout_main = findViewById<RelativeLayout>(R.id.RelativeLayout_main)

        // 1. High-accuracy landmark detection and face classification
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
            .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
            .build()

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.sample)


        // 2. Using a Bitmap
        val image = InputImage.fromBitmap(bitmap, 0)

        // 3. Get an Instance of FaceDetector
        val detector = FaceDetection.getClient()
        // Or, to use the default option:
        // val detector = FaceDetection.getClient();

        // 4. Process the image
        val result = detector.process(image)
            .addOnSuccessListener { faces ->
                // Task completed successfully
                // ...

                // jason 정보들이 다 들어가있음
                Log.d("FACES", faces.toString())

          5

                for (face in faces) {
                    val bounds = face.boundingBox
                    val rotY = face.headEulerAngleY // Head is rotated to the right rotY degrees
                    val rotZ = face.headEulerAngleZ // Head is tilted sideways rotZ degrees

                    // If landmark detection was enabled (mouth, ears, eyes, cheeks, and
                    // nose available):
                    val leftEye = face.getLandmark(FaceLandmark.LEFT_EYE)
                    leftEye?.let {
                        val leftEarPos = leftEye.position
                    }

                    val rightEye = face.getLandmark(FaceLandmark.RIGHT_EYE)
                    rightEye?.let {
                        val rightEarPos = rightEye.position
                    }

                    val leftCheek = face.getLandmark(FaceLandmark.LEFT_CHEEK)
                    leftCheek?.let {
                        val leftEarPos = leftCheek.position
                    }

                    val rightCheek = face.getLandmark(FaceLandmark.RIGHT_CHEEK)
                    rightCheek?.let {
                        val rightEarPos = rightCheek.position
                    }

                    val imageLE = ImageView(this)
                    imageLE.setImageResource(R.drawable.mung)
                    RelativeLayout_main.addView(imageLE)
                    val imageRC = ImageView(this)
                    imageRC.setImageResource(R.drawable.left_whiskers)
                    RelativeLayout_main.addView(imageRC)
                    val imageLC = ImageView(this)
                    imageLC.setImageResource(R.drawable.right_whiskers)
                    RelativeLayout_main.addView(imageLC)

                }
            }
            .addOnFailureListener { e ->
                // Task failed with an exception
                // ...
            }
    }
}