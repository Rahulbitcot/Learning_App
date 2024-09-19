package com.example.learningapp.googleOAuth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.MyApplication
import com.example.learningapp.databinding.ActivityGoogleOauthBinding
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues

class GoogleOAuth : AppCompatActivity() {

    private lateinit var binding : ActivityGoogleOauthBinding
    private lateinit var authService: AuthorizationService
    private val clientId = "860096265123-7ubm1j9ei18p340rhvmbafbms2vc704f.apps.googleusercontent.com"
    private val redirectUri = "com.example.learningapp:/oauth2redirect"
    private val authorizationEndpoint = "https://accounts.google.com/o/oauth2/v2/auth"
    private val tokenEndpoint = "https://oauth2.googleapis.com/token"
    private val scope = "openid email profile https://www.googleapis.com/auth/drive"
    private val authUri: AuthorizationServiceConfiguration
    private var isLoggedIn :Boolean = false



    init {
        // Configure the authorization and token endpoints
        authUri = AuthorizationServiceConfiguration(
            Uri.parse(authorizationEndpoint),
            Uri.parse(tokenEndpoint)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleOauthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(checkLoginStatus()){
            val intent = Intent(this ,GoogleDriveActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Initialize the AppAuth AuthorizationService
        authService = AuthorizationService(this)

        // Create an authorization request
        val authRequest = AuthorizationRequest.Builder(
            authUri,
            clientId,
            ResponseTypeValues.CODE,
            Uri.parse(redirectUri)
        )
            .setScope(scope)
            .build()

        // Start the OAuth 2.0 authorization request
        binding.btSignIn.setOnClickListener{
            val authIntent = authService.getAuthorizationRequestIntent(authRequest)
            startActivityForResult(authIntent, AUTH_REQUEST_CODE)
        }

    }

    private fun checkLoginStatus() : Boolean{
        isLoggedIn  = (application as MyApplication).sharedPreferences.
        getBoolean((application as MyApplication).isLoggedIn, false)
        return isLoggedIn
    }
    private fun setLoginStatus(status: Boolean) {
        with((application as MyApplication).sharedPreferences.edit()) {
            putBoolean((application as MyApplication).isLoggedIn, status)
            apply()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Handle the authorization response
        if (requestCode == AUTH_REQUEST_CODE) {
            val authResponse = AuthorizationResponse.fromIntent(data!!)
            val authException = AuthorizationException.fromIntent(data)

            if (authResponse != null) {
                // Exchange the authorization code for tokens
                val tokenRequest = authResponse.createTokenExchangeRequest()
                authService.performTokenRequest(
                    tokenRequest
                ) { tokenResponse, tokenException ->
                    if (tokenResponse != null) {
                        // Handle successful token response
                         accessToken = tokenResponse.accessToken.toString()
                        Log.d("AccessToken" , "AccessToken = $accessToken")
                        setLoginStatus(true)
                        val intent = Intent(this, GoogleDriveActivity::class.java)
                        startActivity(intent)
                        finish()

                        val idToken = tokenResponse.idToken
                        // Use the tokens (e.g., save them, make authenticated API calls)
                    } else {
                        // Handle token request failure
                        tokenException?.printStackTrace()
                    }
                }
            } else {
                // Handle authorization failure
                authException?.printStackTrace()
            }
        }
    }

    companion object {
        private const val AUTH_REQUEST_CODE = 1001
        var accessToken :String = ""
    }
}