package com.example.learningapp.faceDetection


import com.example.learningapp.databinding.ActivityCameraFaceDetectionBinding
import com.example.learningapp.permissionHandling.permissionUtilClass.PermissionUtil
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

class CameraFaceDetection : AppCompatActivity() {
    private lateinit var binding  :ActivityCameraFaceDetectionBinding
    private var isProcessing = false
    private var isProcessingActive = true
    private var imageCapture: ImageCapture? = null
    private var capturedImageUri: Uri? = null
    private lateinit var cameraProvider: ProcessCameraProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraFaceDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!PermissionUtil.checkCameraPermission(this)){
            PermissionUtil.requestCameraPermission(this,this)
        }else{
            startCamera()
        }

    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()

            imageAnalysis.setAnalyzer(ContextCompat.getMainExecutor(this)) { imageProxy ->
                processImageProxy(imageProxy)
            }
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalysis
                )
            } catch (exc: Exception) {
                Log.d("CameraActivity", "Error $exc")
            }
        }, ContextCompat.getMainExecutor(this))
    }


    @OptIn(ExperimentalGetImage::class)
    private fun processImageProxy(imageProxy: ImageProxy) {
            if (!isProcessingActive) {
                imageProxy.close()
                return
            }

            val mediaImage = imageProxy.image
            if (mediaImage != null && !isProcessing) {
                val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                detectFaces(image, imageProxy)
            } else {
                imageProxy.close()
            }
        }
    private fun detectFaces(image: InputImage, imageProxy: ImageProxy) {
        val realTimeOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
            .enableTracking()
            .build()

        val detector = FaceDetection.getClient(realTimeOpts)

        detector.process(image)
            .addOnSuccessListener { faces ->
                if (faces.isNotEmpty()) {
                    val face = faces[0]
                    val faceBounds = face.boundingBox

                    if (isFaceWithinOverlay(faceBounds, imageProxy.width, imageProxy.height)) {
                        if (!isProcessing) {
                            isProcessing = true
                            pauseImageProcessing()
                            binding.progressBar.visibility = View.VISIBLE
                            Handler(Looper.getMainLooper()).postDelayed({
                                binding.progressBar.visibility = View.GONE
                                binding.messageTextView.visibility = View.VISIBLE
                                captureImage()
                            }, 5000)
                        }
                    } else {
                        Log.d("FaceDetection", "Face is outside the overlay.")
                    }
                } else {
                    resetUI()
                }
            }
            .addOnFailureListener {
                resetUI()
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

    private fun resetUI() {
        binding.progressBar.visibility = View.GONE
        binding.messageTextView.visibility = View.GONE
        isProcessing = false
    }

    private fun isFaceWithinOverlay(faceBounds: Rect, imageWidth: Int, imageHeight: Int): Boolean {
        val viewWidth = binding.previewView.width.toFloat()
        val viewHeight = binding.previewView.height.toFloat()

        val scaleX = viewWidth / imageWidth
        val scaleY = viewHeight / imageHeight

        // Map the face center from image coordinates to view coordinates
        val faceCenterX = faceBounds.centerX() * scaleX
        val faceCenterY = faceBounds.centerY() * scaleY

        // Define egg shape dimensions (you can adjust these values)
        val eggWidth = viewWidth * 0.5f
        val eggHeight = viewHeight * 0.7f
        val centerX = viewWidth / 2f
        val centerY = viewHeight / 2f

        // Check if the face center is within the egg shape
        val isInEggShape = (faceCenterX > centerX - eggWidth / 2 && faceCenterX < centerX + eggWidth / 2 &&
                faceCenterY > centerY - eggHeight / 2 && faceCenterY < centerY + eggHeight / 2)

        // Define square dimensions
        val squareSide = eggWidth
        val left = (viewWidth - squareSide) / 2
        val top = (viewHeight - squareSide) / 2

        // Check if the face center is within the square
        val isInSquare = (faceCenterX > left && faceCenterX < left + squareSide &&
                faceCenterY > top && faceCenterY < top + squareSide)

        return isInEggShape && isInSquare
    }

    private fun pauseImageProcessing() {
        isProcessingActive = false
    }

    private fun resumeImageProcessing() {
        isProcessingActive = true
    }

    private fun captureImage() {
        val photoFile = createImageFile()
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    capturedImageUri = Uri.fromFile(photoFile)
                    showBlackOverlay()
                }

                override fun onError(exception: ImageCaptureException) {
                    resumeImageProcessing()
                }
            })
    }

    private fun createImageFile(): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(System.currentTimeMillis())
        val storageDir = getExternalFilesDir(null)
        return File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
    }

    private fun showBlackOverlay() {
        cameraProvider.unbindAll()
        binding.blackOverlay.visibility = View.VISIBLE
    }
}
