package com.narify.ecommerce.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.narify.ecommerce.R
import com.narify.ecommerce.data.remote.amazon.PutProductsActivity
import com.narify.ecommerce.databinding.ActivityMainBinding
import com.narify.ecommerce.model.User
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val user = Firebase.auth.currentUser
    private val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        binding.bottomNavigation.setupWithNavController(navController)

    }

    /* override fun onCreateOptionsMenu(menu: Menu): Boolean {
         val searchView = findViewById<View>(R.id.search_view) as MaterialSearchView

         menuInflater.inflate(R.menu.menu_main, menu)

         val item: MenuItem = menu.findItem(R.id.action_search)

         searchView.setMenuItem(item)

         return true
     }
 */

    private fun checkUser() {
        if (user == null) signUp()
        else addNewUser()
    }


    private fun signUp() {
        //Firebase.auth.signOut()
        Timber.d("GeneralLogKey signUp: user: $user")
        Firebase.auth.createUserWithEmailAndPassword("amr_salah3@yahoo.com", "12345666")
            .addOnSuccessListener {
                Timber.d("GeneralLogKey signUp: success")
                startActivity(Intent(this, PutProductsActivity::class.java))
            }
            .addOnFailureListener {
                Timber.d("GeneralLogKey signUp: fail ${it.message}")
            }
        /*AuthUI.getInstance().signOut(this).addOnCompleteListener {
            val providers = arrayListOf(
                AuthUI.IdpConfig.FacebookBuilder().build(),
                AuthUI.IdpConfig.TwitterBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build(),
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .setLogo(R.drawable.ic_launcher_foreground)
                    .build(),
                1
            )
        }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //if (requestCode == 1 && resultCode == RESULT_OK) addNewUser()
        Timber.d("GeneralLogKey onActivityResult: done")
        startActivity(Intent(this, PutProductsActivity::class.java))
    }

    private fun addNewUser() {
        val currentUser = Firebase.auth.currentUser
        currentUser?.let {
            val user = User(
                name = it.displayName,
                email = it.email,
                photoUrl = it.photoUrl.toString(),
                age = 21,
                gender = User.Gender.MALE,
                phoneNumber = "013514534"
            )
            Timber.d("GeneralLogKey addNewUser: $user")
            db.collection("users").add(user)
        }
    }
}