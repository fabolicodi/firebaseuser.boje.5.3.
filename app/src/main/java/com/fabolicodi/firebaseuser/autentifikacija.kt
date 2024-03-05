package com.fabolicodi.firebaseuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabolicodi.firebaseuser.databinding.ActivityAutentifikacijaBinding
import com.fabolicodi.firebaseuser.databinding.TextItemBinding
import com.fabolicodi.firebaseuser.autentifikacija.FirebaseAuth

class autentifikacija : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityAutentifikacijaBinding
    private var userId:String=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityAutentifikacijaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


        binding.log.setOnClickListener {
            if (binding.unosemail.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.jes_glup_nisi_ime_napiso), Toast.LENGTH_SHORT).show()
            else if (binding.unoslozinke.text.toString().isEmpty()) Toast.makeText(applicationContext, getString(R.string.nisi_lozinku_napisao), Toast.LENGTH_SHORT).show()
            else if (binding.pass.text.toString().length < 6) Toast.makeText(applicationContext,getString(R.string.kratka_lozinka), Toast.LENGTH_SHORT).show()
            else{
                val str = binding.username.text.toString().filter { !it.isWhitespace() }
                val email ="$str@gmail.com"
                val password=binding.pass.text.toString()
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(baseContext, getString(R.string.uspjesna_prijava),
                                Toast.LENGTH_SHORT).show()
                            getString(R.string.uspjesna_prijava)
                            val user = auth.currentUser
                            val intent= Intent(this, MainActivity::class.java)
                            intent.putExtra("user", user)
                            startActivity(intent)
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, getString(R.string.neuspjesna_prijava),
                                Toast.LENGTH_SHORT).show()
                            getString(R.string.neuspjesna_prijava)
                        }
                    } }
        }
    }
}