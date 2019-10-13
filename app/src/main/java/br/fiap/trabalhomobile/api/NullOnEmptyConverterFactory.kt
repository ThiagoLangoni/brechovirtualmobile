package br.fiap.trabalhomobile.api

import androidx.annotation.Nullable
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class NullOnEmptyConverterFactory private constructor() : Converter.Factory() {

    @Nullable
    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, Any> {
        val delegate = retrofit.nextResponseBodyConverter<Any>(this, type, annotations)
        return Converter<ResponseBody, Any> { body -> if (body.contentLength() == 0L) null else delegate.convert(body) }
    }

    companion object {
        fun create(): Converter.Factory {
            return NullOnEmptyConverterFactory()
        }
    }
}