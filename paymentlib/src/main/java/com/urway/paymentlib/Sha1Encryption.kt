package com.urway.paymentlib

import org.apache.commons.codec.binary.Hex
import java.io.UnsupportedEncodingException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

internal class Sha1Encryption {
    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun hash(text: String): ByteArray {
        var md: MessageDigest
        md = MessageDigest.getInstance("SHA-1")
        md = MessageDigest.getInstance("SHA-1")
        md.update(text.toByteArray(charset("utf-8")))
        return md.digest()
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun SHA384(text: String): String {
        val md: MessageDigest
        md = MessageDigest.getInstance("SHA-384")
        md.update(text.toByteArray(charset("utf-8")))
        val sha384 = md.digest()
        return String(Hex.encodeHex(sha384))
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun SHA256(text: String): String {
        val md: MessageDigest
        md = MessageDigest.getInstance("SHA-256")
        md.update(text.toByteArray(charset("utf-8")))
        val sha384 = md.digest()
        return String(Hex.encodeHex(sha384))
    }
}