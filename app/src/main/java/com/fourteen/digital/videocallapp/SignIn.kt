package com.fourteen.digital.videocallapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth


class SignIn : Fragment() {

    val auth = FirebaseAuth.getInstance()


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null){
            view?.let { Navigation.findNavController(it).navigate(R.id.action_signIn_to_videoCallConference) }
            Toast.makeText(requireActivity(),"....Welcome....",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(requireActivity(),"....Please LogIn....",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Move to SignUp fragment
        view.findViewById<TextView>(R.id.sign_up_tv).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_signIn_to_signUp)
        }

        //on click signin button
        view.findViewById<Button>(R.id.sign_in_btn).setOnClickListener {

            val email = view.findViewById<EditText>(R.id.email_address_et).text.toString().trim()
            val password = view.findViewById<EditText>(R.id.password_et).text.toString().trim()

            //signin with firebase
            auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(requireActivity(), "Login Successfully..!", Toast.LENGTH_LONG).show()
                    Navigation.findNavController(view).navigate(R.id.action_signIn_to_videoCallConference)
                }
                else{
                    Toast.makeText(requireActivity(),task.exception.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }


    }

}