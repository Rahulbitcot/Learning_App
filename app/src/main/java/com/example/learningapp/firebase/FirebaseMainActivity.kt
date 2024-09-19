package com.example.learningapp.firebase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.MyApplication
import com.example.learningapp.databinding.ActivityFirebaseMainBinding
import com.example.learningapp.sideAndBottomNavigationBar.SideAndBottomNavigationActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class FirebaseMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirebaseMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
     private  var status : Boolean = false

    private val googleSignInLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFirebaseMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        configureGoogleSignIn()


        binding.btLogin.setOnClickListener {
            val intent = Intent(this, LogInWithPhoneNumber::class.java)
            startActivity(intent)
        }

        binding.btLoginWithGoogle.setOnClickListener {
            signInWithGoogle()
        }

        binding.btLoginWithPhone.setOnClickListener {
            val intent = Intent(this, SignInWithPhoneNumber::class.java)
            startActivity(intent)
        }


    }

    private fun checkLoginStatus() : Boolean{
        status  = (application as MyApplication).sharedPreferences.
                     getBoolean((application as MyApplication).LOG_IN_STATUS, false)
        return status

    }
    private fun setLoginStatus(status: Boolean) {
        with((application as MyApplication).sharedPreferences.edit()) {
            putBoolean((application as MyApplication).LOG_IN_STATUS, status)
            apply()
        }
    }

    private fun configureGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signInWithGoogle() {
        if(checkLoginStatus()){
            Toast.makeText(this , "Already Login" , Toast.LENGTH_LONG).show()
        }else{
            val signInIntent = googleSignInClient.signInIntent
            googleSignInLauncher.launch(signInIntent)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.result
            firebaseAuthWithGoogle(account)
        } catch (e: Exception) {
            Log.w("FirebaseMainActivity", "Google sign in failed", e)
        }
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this , "Login Successful" , Toast.LENGTH_LONG).show()
                    setLoginStatus(true)
                    val intent = Intent(this , SideAndBottomNavigationActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    setLoginStatus(false)
                    Toast.makeText(this , "Unable To Login ${task.exception}" , Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener(this){
                Toast.makeText(this , "Unable To Login " , Toast.LENGTH_LONG).show()
            }
    }
}
