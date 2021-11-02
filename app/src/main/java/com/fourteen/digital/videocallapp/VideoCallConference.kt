package com.fourteen.digital.videocallapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.react.modules.core.PermissionListener
import com.google.firebase.auth.FirebaseAuth
import org.jitsi.meet.sdk.JitsiMeet
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetActivityInterface
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import java.net.MalformedURLException
import java.net.URL


class VideoCallConference : Fragment(), JitsiMeetActivityInterface {

    val auth = FirebaseAuth.getInstance()


    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null){
            Toast.makeText(requireActivity(),"....Welcome....",Toast.LENGTH_SHORT)
        }
        else{

        }

    }





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video_call_conference, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var secrete_code = view.findViewById<EditText>(R.id.secrete_code_et)

        // Initialize default options for Jitsi Meet conferences.


        var serverURL: URL


        try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            val serverURL = URL("https://meet.jit.si")

            val defaultOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build()

            JitsiMeet.setDefaultConferenceOptions(defaultOptions)
        }catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }




       /* try {
            // When using JaaS, replace "https://meet.jit.si" with the proper serverURL
            val serverURL = URL("https://meet.jit.si")
            val options: JitsiMeetConferenceOptions = JitsiMeetConferenceOptions.Builder()
                .setServerURL(serverURL)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeet.setDefaultConferenceOptions(options)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }*/



        //on click share button
        view.findViewById<Button>(R.id.share_btn).setOnClickListener {

            if (secrete_code.text.toString().length >= 6) {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, secrete_code.text.toString());
                startActivity(Intent.createChooser(shareIntent,getString(R.string.send_to)))
            }else{
                Toast.makeText(requireActivity(),"Code must be greater than 5",Toast.LENGTH_SHORT).show()
            }

        }

        //on click join button
        view.findViewById<Button>(R.id.join_btn).setOnClickListener {

            if (secrete_code.text.toString().length >= 6) {

                val options = JitsiMeetConferenceOptions.Builder()
                    .setRoom(secrete_code.text.toString())
                    .setWelcomePageEnabled(false)
                    .build()
                JitsiMeetActivity.launch(requireActivity(), options)
            }else{
                Toast.makeText(requireActivity(),"Code must be greater than 5",Toast.LENGTH_SHORT).show()
            }
        }


    }

    override fun checkPermission(p0: String?, p1: Int, p2: Int): Int {
        TODO("Not yet implemented")
    }

    override fun checkSelfPermission(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun requestPermissions(p0: Array<out String>?, p1: Int, p2: PermissionListener?) {
        TODO("Not yet implemented")
    }




}