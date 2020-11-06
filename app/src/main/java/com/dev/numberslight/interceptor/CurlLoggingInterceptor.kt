package com.dev.numberslight.interceptor

import android.annotation.SuppressLint
import androidx.annotation.VisibleForTesting
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Buffer
import timber.log.Timber
import java.io.IOException
import java.nio.charset.Charset

class CurlLoggingInterceptor(
    private val curlOptions: String? = null
) : Interceptor {
    @SuppressLint("VisibleForTests")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Timber.d("""╭--- cURL (%s)""", request.url())
        Timber.d(request.buildCurlCommand(curlOptions))
        Timber.d("""╰--- (copy and paste the above line to a terminal)""")
        val response = chain.proceed(request)
        Timber.d("Response : ")
        return response.body()?.let {
            val contentType = it.contentType()
            val content = it.string()
            Timber.d(content)
            response.newBuilder().body(ResponseBody.create(contentType, content)).build()
        } ?: response
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")

        @VisibleForTesting
        internal fun Request.buildCurlCommand(curlOptions: String? = null) = buildString {
            var compressed = false
            append("curl")
            curlOptions?.let {
                append(" ").append(it)
            }
            append(" -X ").append(method())
            val headers = headers()
            for (i in 0 until headers.size()) {
                val name = headers.name(i)
                val value = headers.value(i)
                if ("Accept-Encoding".equals(name, ignoreCase = true) && "gzip".equals(value, ignoreCase = true)) {
                    compressed = true
                }
                append(" -H ").append(name).append(": ").append(value)
            }

            body()?.let { requestBody ->
                val buffer = Buffer()
                requestBody.writeTo(buffer)
                val charset = requestBody.contentType()?.charset(UTF8) ?: UTF8
                // try to keep to a single line and use a subshell to preserve any line breaks
                append(" --data $'").append(buffer.readString(charset).replace("\n", "\\n")).append("'")
            }
            append(if (compressed) " --compressed " else " ").append(url())
        }
    }
}