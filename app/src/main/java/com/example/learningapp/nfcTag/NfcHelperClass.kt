package com.example.learningapp.nfcTag

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import android.util.Log
import android.widget.Toast
import java.nio.charset.Charset

object NfcHelperClass {

    fun writeUrlToNfcTag(ndef: Ndef, url: String ,context : Context) {
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
            Toast.makeText(context, "NFC tag updated with URL", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NFCTag", "Error writing NFC tag: ${e.message}")
            Toast.makeText(context, "Failed to write NFC tag", Toast.LENGTH_LONG).show()
        } finally {
            ndef.close() // Close the NFC tag connection after writing
            Log.d("NFCTag", "NFC tag connection closed after writing")
        }
    }

    fun readNfcTag(ndef: Ndef ,isLive : Boolean,context: Context) {
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
                        context.startActivity(browserIntent) // Open the URL in the browser
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

    fun formatTagAndWriteNdef(tag: Tag, url: String,context: Context) {
        val ndefFormatable = NdefFormatable.get(tag)
        if (ndefFormatable != null) {
            try {
                ndefFormatable.connect()
                val uriRecord = NdefRecord.createUri(url)
                val ndefMessage = NdefMessage(arrayOf(uriRecord))
                ndefFormatable.format(ndefMessage) // Format the tag and write the NDEF message
                Log.d("NFCTag", "Tag formatted and written with URL: $url")
                Toast.makeText(context, "NFC tag formatted and written with URL", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("NFCTag", "Error formatting NFC tag: ${e.message}")
                Toast.makeText(context, "Failed to format NFC tag", Toast.LENGTH_LONG).show()
            } finally {
                try {
                    ndefFormatable.close()
                } catch (e: Exception) {
                    Log.e("NFCTag", "Error closing NFC tag connection: ${e.message}")
                }
            }
        } else {
            Log.e("NFCTag", "Tag is not NDEF formatable")
            Toast.makeText(context, "Tag is not NDEF formatable", Toast.LENGTH_LONG).show()
        }
    }


}