package com.fourteen.digital.videocallapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.net.MalformedURLException


class SignUp : Fragment() {

    val auth = FirebaseAuth.getInstance()
    val db = Firebase.firestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Move to SignIn fragment
        view.findViewById<TextView>(R.id.sign_in_tv).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signUp_to_signIn)
        }

        //on click signup button
        view.findViewById<Button>(R.id.sign_up_btn).setOnClickListener {

            val full_name = view.findViewById<EditText>(R.id.name_et).text.toString().trim()
            val email = view.findViewById<EditText>(R.id.email_address_et).text.toString().trim()
            val password = view.findViewById<EditText>(R.id.password_et).text.toString().trim()
            val conf_password = view.findViewById<EditText>(R.id.confirm_password_et).text.toString().trim()

            val user = UserData()



            if(valdidate(full_name,email,password,conf_password)){

                user.full_name = full_name
                user.email = email
                user.password = password

                //create new user
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task ->

                    if (task.isSuccessful) {
                        db.collection("Users").document().set(user).addOnSuccessListener {
                            //go to main/home activity
                        }
                        Toast.makeText(requireActivity(), "Register Successfully...!", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(view).navigate(R.id.action_signUp_to_videoCallConference)
                    }
                    else{
                        Toast.makeText(requireActivity(),task.exception.toString(), Toast.LENGTH_LONG).show()
                    }

                }
            }
            else{
                Toast.makeText(requireActivity(), "Invalid Input Fields", Toast.LENGTH_LONG).show()
            }





            }
        }

    private fun valdidate(fullName: String, email: String, password: String, confPassword: String): Boolean {

        return fullName.isNotEmpty() && email.isNotEmpty() && password.equals(confPassword)

    }

}
