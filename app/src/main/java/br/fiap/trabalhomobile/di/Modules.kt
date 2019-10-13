package br.fiap.trabalhomobile.di

import android.content.Context
import br.fiap.trabalhomobile.api.AuthInterceptor
import br.fiap.trabalhomobile.api.NullOnEmptyConverterFactory
import br.fiap.trabalhomobile.api.ProdutoService
import br.fiap.trabalhomobile.repository.ProdutoRepository
import br.fiap.trabalhomobile.repository.ProdutoRepositoryImpl
import br.fiap.trabalhomobile.ui.form.FormProdutoViewModel
import br.fiap.trabalhomobile.ui.list.ListProdutosViewModel
import br.fiap.trabalhomobile.utils.URLProvider
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel { ListProdutosViewModel(get()) }
    viewModel { FormProdutoViewModel(get()) }
}

val repositoryModule = module {
    single<ProdutoRepository> { ProdutoRepositoryImpl(get()) }
}

val networkModule = module {
    single<Interceptor> { AuthInterceptor() }
    single { createOkhttpClientAuth(get()) }

    single { createNetworkClient(get(),
        get(named("baseURL"))
    ).create(ProdutoService::class.java) }

    single { createPicassoAuth(get(), get()) }
    single(named("baseURL")) { URLProvider.baseURL }
}

private fun createPicassoAuth(context: Context, client: OkHttpClient): Picasso {
    return Picasso.Builder(context)
        .downloader(OkHttp3Downloader(client))
        .build()
}

private fun createNetworkClient(okHttpClient: OkHttpClient,
                                baseURL: String): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(baseURL)
        .addConverterFactory(NullOnEmptyConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

private fun createOkhttpClientAuth(authInterceptor: Interceptor): OkHttpClient {
    val builder = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
    return builder.build()
}
