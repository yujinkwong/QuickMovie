// MainActivity.kt
package com.example.quickmovie

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.quickmovie.ui.theme.QuickMovieTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient
    private val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Realtime Database
        database = FirebaseDatabase.getInstance("https://quickmovie-490e5-default-rtdb.asia-southeast1.firebasedatabase.app/").reference

        // Initialize Google Sign-In options
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)) // Replace with your Web Client ID
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        // Set the layout using AndroidView with event listener
        setContent {
            QuickMovieTheme {
                SignInScreenWithXML { signInWithGoogle() }
            }
        }
    }

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    firebaseAuthWithGoogle(account)
                } catch (e: ApiException) {
                    Log.e("MainActivity", "Google Sign-In failed: ${e.statusCode}", e)
                }
            } else {
                Log.e("MainActivity", "Sign-in result was not OK")
            }
        }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(account: GoogleSignInAccount?) {
        val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Toast.makeText(this, "Sign-In Successful: ${user?.email}", Toast.LENGTH_SHORT).show()
                    Log.d("MainActivity", "Firebase Sign-In successful: ${user?.email}")

                    // Store user data in Firebase Realtime Database
                    storeUserDataInDatabase(user)

                    // Play success audio
                    playSuccessAudio()

                    // After successful sign-in, navigate to DashboardActivity
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Close MainActivity to prevent going back
                } else {
                    Toast.makeText(this, "Sign-In Failed", Toast.LENGTH_SHORT).show()
                    Log.e("MainActivity", "Firebase Sign-In failed", task.exception)
                }
            }
    }

    private fun storeUserDataInDatabase(user: FirebaseUser?) {
        user?.let {
            // Use the email (with '.' replaced by '_') as the user key
            val userRef = database.child("users").child(user.email?.replace(".", "_") ?: "unknown")

            // Check if the user data already exists
            userRef.get().addOnSuccessListener { snapshot ->
                if (snapshot.exists()) {
                    // Data already exists, no need to save again
                    Log.d("MainActivity", "User data already exists. No need to save.")
                } else {
                    // Data does not exist, save the user data
                    val userData = mapOf(
                        "name" to user.displayName,
                        "email" to user.email,
                    )
                    userRef.setValue(userData)
                        .addOnSuccessListener {
                            Log.d("MainActivity", "User data saved successfully.")
                        }
                        .addOnFailureListener { e ->
                            Log.e("MainActivity", "Error saving user data", e)
                        }
                }
            }
        }
    }

    private fun playSuccessAudio() {
        val mediaPlayer = MediaPlayer.create(this, R.raw.login_success) // Use your audio file name
        mediaPlayer.start()
        mediaPlayer.setOnCompletionListener {
            it.release() // Release resources after playback
        }
    }

}

@Composable
fun SignInScreenWithXML(onSignInButtonClick: () -> Unit) {
    AndroidView(
        factory = { context ->
            val view = LayoutInflater.from(context).inflate(R.layout.activity_login, null)
            val signInButton: View = view.findViewById(R.id.signInButton)
            signInButton.setOnClickListener {
                onSignInButtonClick()
            }
            view
        },
        modifier = Modifier.fillMaxSize()
    )
}