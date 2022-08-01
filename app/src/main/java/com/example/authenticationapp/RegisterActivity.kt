package com.example.authenticationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.authenticationapp.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHaveAccount.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        firebaseAuth = FirebaseAuth.getInstance()
        binding.btnRegister.setOnClickListener{

            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val confirmPass = binding.etConfirmPassword.text.toString()


            if (pass.length < 8 )
            {
                return@setOnClickListener Toast.makeText(this, "Minimum 8 characters password", Toast.LENGTH_SHORT).show()

            }
            if (!pass.matches(".*[A-Z].*".toRegex()))
            {
                return@setOnClickListener Toast.makeText(this, "Must contain 1 upper-case character", Toast.LENGTH_SHORT).show()
            }

            if (!pass.matches(".*[a-z].*".toRegex()))
            {
                return@setOnClickListener Toast.makeText(this,"Must contain 1 lower-case character" , Toast.LENGTH_SHORT).show()
            }


            if (!pass.matches(".*[!@#\$%^&*()_+=?/><.,{}|`~].*".toRegex()))
            {
                return@setOnClickListener Toast.makeText(this, "Must contain 1 special character (!@#\$%^&*()_+=?/><.,{}|`~)", Toast.LENGTH_SHORT).show()

            }

            if (pass.isNotEmpty() && confirmPass.isNotEmpty()&& email.isNotEmpty()) {

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

                }

                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                else {
                    Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                }


            }




        }
    }





}