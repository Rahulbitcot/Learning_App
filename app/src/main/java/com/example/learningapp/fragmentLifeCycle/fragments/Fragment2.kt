package com.example.learningapp.fragmentLifeCycle.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.learningapp.R

class Fragment2 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Fragment_B_Log" , "Fragment B is on  onCreate")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Fragment_B_Log" , "Fragment B is on  onCreateView")
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Text Receive from FragmentLifeCycle Activity
        val message = arguments?.getString("textForFragmentSecond")
        val textviewIntentData = view.findViewById<TextView>(R.id.txtView_intent_data)
        textviewIntentData?.text = message

        Log.d("Fragment_B_Log" , "Fragment B is on  onViewCreated ")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.d("Fragment_B_Log" , "Fragment B is on  onViewStateRestored ")

    }

    override fun onStart() {
        super.onStart()
        Log.d("Fragment_B_Log" , "Fragment B is on  OnStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Fragment_B_Log" , "Fragment B is on  onResume ")


    }

    override fun onPause() {
        super.onPause()
        Log.d("Fragment_B_Log" , "Fragment B is on  onPause ")

    }

    override fun onStop() {
        super.onStop()
        Log.d("Fragment_B_Log" , "Fragment B is on  onStop ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d("Fragment_B_Log" , "Fragment B is on  onSaveInstanceState ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("Fragment_B_Log" , "Fragment B is on  onDestroyView ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Fragment_B_Log" , "Fragment B is on  onDestroy ")

    }
}
//Check log using   package:mine (tag:Fragment_A_Log | tag:Fragment_B_Log) Filter

