package com.movies.streamy.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.executeWithRetry
import com.movies.streamy.R
import com.movies.streamy.di.IoDispatcher
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
import com.movies.streamy.model.repository.abstraction.IHomeRepository
import com.movies.streamy.utils.AppUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: IHomeRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _movieList = MutableLiveData<List<HomeResult?>?>()
    val movieList: LiveData<List<HomeResult?>?>
        get() = _movieList

    private val _viewState = MutableLiveData<HomeViewState>()
    val viewState: LiveData<HomeViewState>
        get() = _viewState

    fun getMovieLists() {
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(ioDispatcher) {
            val result = executeWithRetry(times = 3) {
                homeRepository.getMovieLists()
            }

            when (result) {
                is NetworkResponse.Success -> {
                    _viewState.postValue(HomeViewState.Success)
                    val data = result.body
                    _movieList.postValue(data.results)
                }

                is NetworkResponse.NetworkError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.network_error_msg,
                            null
                        )
                    )
                }

                is NetworkResponse.ServerError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            AppUtil.getErrorResponse(result.body),
                            null,
                            null
                        )
                    )
                }

                is NetworkResponse.UnknownError -> {
                    _viewState.postValue(
                        HomeViewState.Error(
                            null,
                            R.string.unknown_error_msg,
                            null
                        )
                    )
                }
            }
        }
    }
}

//package com.movies.streamy.view.home
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.haroldadmin.cnradapter.NetworkResponse
//import com.haroldadmin.cnradapter.executeWithRetry
//import com.movies.streamy.R
//import com.movies.streamy.di.IoDispatcher
//import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
//import com.movies.streamy.model.repository.abstraction.IHomeRepository
//import com.movies.streamy.utils.AppUtil
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//class HomeViewModel @Inject constructor(
//    private val homeRepository: IHomeRepository,
//    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
//) : ViewModel() {
//
//    private val _movieList = MutableLiveData<List<HomeResult?>?>()
//    val movieList: LiveData<List<HomeResult?>?>
//        get() = _movieList
//
//    private val _viewState = MutableLiveData<HomeViewState>()
//    val viewState: LiveData<HomeViewState>
//        get() = _viewState
//
//    fun getMovieLists() {
//        _viewState.postValue(HomeViewState.Loading)
//        viewModelScope.launch(ioDispatcher) {
//            val result = executeWithRetry(times = 3) {
//                homeRepository.getMovieLists()
//            }
//
//            when (result) {
//                is NetworkResponse.Success -> {
//                    _viewState.postValue(HomeViewState.Success)
//                    val data = result.body
//                    _movieList.postValue(data.results)
//                }
//
//                is NetworkResponse.NetworkError -> {
//                    _viewState.postValue(
//                        HomeViewState.Error(
//                            null,
//                            R.string.network_error_msg,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.ServerError -> {
//                    _viewState.postValue(
//                        HomeViewState.Error(
//                            AppUtil.getErrorResponse(result.body),
//                            null,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.UnknownError -> {
//                    _viewState.postValue(
//                        HomeViewState.Error(
//                            null,
//                            R.string.unknown_error_msg,
//                            null
//                        )
//                    )
//                }
//            }
//        }
//    }
//}
