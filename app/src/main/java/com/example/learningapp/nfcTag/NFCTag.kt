package com.example.learningapp.nfcTag

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningapp.R
import com.example.learningapp.databinding.ActivityNfctagBinding


class NFCTag : AppCompatActivity() {

    private lateinit var binding: ActivityNfctagBinding
    private var nfcAdapter: NfcAdapter? = null
    private var writeMode = false
    private var isLive = true
    private var defaultUrl = "https://www.linkedin.com/company/bitcot"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityNfctagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Handle window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize NFC adapter
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "This device does not support NFC tag", Toast.LENGTH_LONG).show()
            Log.e("NFCTag", "NFC adapter not available")
        } else {
            Log.d("NFCTag", "NFC adapter available")
        }

        // Log the URL that will be written to the NFC tag
        Log.d("NFCTag", "URL to write: ${binding.txtViewUrl.text}")

        // Set up the button to enable write mode when clicked

        binding.btEnableWriteMode.setOnClickListener {
            writeMode = true
            Log.d("NFCTag", "Write mode enabled")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("NFCTag", "onResume called")
        enableForegroundDispatch() // Enable foreground dispatch to detect NFC tags while the app is in the foreground
    }

    override fun onPause() {
        super.onPause()
        Log.d("NFCTag", "onPause called")
        disableForegroundDispatch() // Disable foreground dispatch to avoid processing NFC tags while the app is in the background
    }

    private fun enableForegroundDispatch() {
        // Prepare an intent for the foreground dispatch system
        val intent = Intent(this, javaClass).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)
        val filters = arrayOf<IntentFilter>() // Optionally filter for specific intents
         val techList = arrayOf<Array<String>>()

        // Enable foreground dispatch on the NFC adapter
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, filters, techList)
        Log.d("NFCTag", "Foreground dispatch enabled")
    }

    private fun disableForegroundDispatch() {
        // Disable foreground dispatch on the NFC adapter
        nfcAdapter?.disableForegroundDispatch(this)
        Log.d("NFCTag", "Foreground dispatch disabled")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        Log.d("NFCTag", "onNewIntent called with action: ${intent.action}")

        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            val techList = tag.techList.joinToString(", ")
            Log.d("NFCTag", "Tag detected with technologies: $techList")

            val ndef = Ndef.get(tag)
            if (ndef != null) {
                if (writeMode) {
                    if(binding.txtViewUrl.text.toString()!="") {
                        NfcHelperClass.writeUrlToNfcTag(
                            ndef,
                            binding.txtViewUrl.text.toString(),
                            this
                        )
                        writeMode = false
                        Log.d("NFCTag", "Write mode disabled after writing")
                    }else{
                        NfcHelperClass.writeUrlToNfcTag(
                            ndef,
                            defaultUrl,
                            this
                        )
                        writeMode = false
                        Log.d("NFCTag", "Write mode disabled after writing")
                    }
                } else {
                    NfcHelperClass.readNfcTag(ndef,isLive,this)
                }
            } else {
                val ndefFormatable = NdefFormatable.get(tag)
                if (ndefFormatable != null) {
                    // Format the tag and write the URL
                    NfcHelperClass.formatTagAndWriteNdef(tag, binding.txtViewUrl.text.toString(),this)
                } else {
                    Log.e("NFCTag", "Ndef and NdefFormatable are both null or unsupported")
                }
            }
        } else {
            Log.e("NFCTag", "No NFC tag detected")
        }
    }


/*
    private fun formatTagAndWriteNdef(tag: Tag, url: String) {
        val ndefFormatable = NdefFormatable.get(tag)
        if (ndefFormatable != null) {
            try {
                ndefFormatable.connect()
                val uriRecord = NdefRecord.createUri(url)
                val ndefMessage = NdefMessage(arrayOf(uriRecord))
                ndefFormatable.format(ndefMessage) // Format the tag and write the NDEF message
                Log.d("NFCTag", "Tag formatted and written with URL: $url")
                Toast.makeText(this, "NFC tag formatted and written with URL", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NFCTag", "Error formatting NFC tag: ${e.message}")
                Toast.makeText(this, "Failed to format NFC tag", Toast.LENGTH_LONG).show()
            } finally {
                try {
                    ndefFormatable.close()
                } catch (e: Exception) {
                    Log.e("NFCTag", "Error closing NFC tag connection: ${e.message}")
                }
            }
        } else {
            Log.e("NFCTag", "Tag is not NDEF formatable")
            Toast.makeText(this, "Tag is not NDEF formatable", Toast.LENGTH_LONG).show()
        }
    }



    private fun readNfcTag(ndef: Ndef) {
        try {
            Log.d("NFCTag", "Connecting to NFC tag")
            ndef.connect() // Connect to the NFC tag

            val ndefMessage = ndef.ndefMessage
            if (ndefMessage != null) {
                val record = ndefMessage.records[0] // Retrieve the first NDEF record
                val uri = parseUriFromRecord(record) // Parse the URI from the NDEF record
                Log.d("NFCTag", "NFC tag read with URI: $uri")

                uri?.let {
                    // Ensure the URI has a scheme (http:// or https://)
                    var uriToOpen = it.toString()
                    if (!uriToOpen.startsWith("http://") && !uriToOpen.startsWith("https://")) {
                        uriToOpen = "https://$uriToOpen"
                    }

                    if (isLive) { // Check if the app is live before opening the browser
                        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uriToOpen))
                        startActivity(browserIntent) // Open the URL in the browser
                    }
                    Log.d("NFCTag", "Browser opened with URI: $uriToOpen")
                } ?: run {
                    Log.e("NFCTag", "Error: URI is null")
                }

            } else {
                Log.w("NFCTag", "NDEF message is null")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NFCTag", "Error reading NFC tag: ${e.message}")
        } finally {
            ndef.close() // Close the NFC tag connection
            Log.d("NFCTag", "NFC tag connection closed")
        }
    }

    private fun parseUriFromRecord(record: NdefRecord): Uri? {
        return try {
            // Parse the URI from the NDEF record payload, skipping the first byte (URI identifier code)
            val payload = record.payload
            val uriString = String(payload, 1, payload.size - 1, Charset.forName("UTF-8"))
            Log.d("NFCTag", "URI parsed from NFC tag: $uriString")
            Uri.parse(uriString) // Return the parsed URI
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NFCTag", "Error parsing URI from NFC record: ${e.message}")
            null // Return null if parsing fails
        }
    }

    private fun writeUrlToNfcTag(ndef: Ndef, url: String) {
        try {
            Log.d("NFCTag", "Connecting to NFC tag for writing")
            ndef.connect() // Connect to the NFC tag for writing

            // Create an NDEF record containing the URL
                val uriRecord = NdefRecord.createUri(url)
                // Create an NDEF message with the URL record
                val ndefMessage = NdefMessage(arrayOf(uriRecord))
                // Write the NDEF message to the NFC tag
                ndef.writeNdefMessage(ndefMessage)

            Log.d("NFCTag", "NFC tag written with URL: $url")
            Toast.makeText(this, "NFC tag updated with URL", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NFCTag", "Error writing NFC tag: ${e.message}")
            Toast.makeText(this, "Failed to write NFC tag", Toast.LENGTH_LONG).show()
        } finally {
            ndef.close() // Close the NFC tag connection after writing
            Log.d("NFCTag", "NFC tag connection closed after writing")
//        }
//    }

    */

}
