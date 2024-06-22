//import com.movies.streamy.view.movies.MoviesViewState
//
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.haroldadmin.cnradapter.NetworkResponse
//import com.haroldadmin.cnradapter.executeWithRetry
//import com.movies.streamy.R
////import com.movies.streamy.di.IoDispatcher
//import com.movies.streamy.model.dataSource.network.data.response.MovieId
//import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
//import com.movies.streamy.utils.AppUtil
//import dagger.hilt.android.lifecycle.HiltViewModel
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.launch
//import javax.inject.Inject
//
//@HiltViewModel
//
//
//class HomeViewModel @Inject constructor(
//    private val homeRepository: MoviesRepositoryImpl,
//    @IoDispatcher private val iODispatcher: CoroutineDispatcher,
//) : ViewModel() {
//
//    private val _movieIds = MutableLiveData<List<MovieId?>?>()
//    val movieIds: LiveData<List<MovieId?>?>
//        get() = _movieIds
//
//    private val _viewState = MutableLiveData<MoviesViewState>()
//    val viewState: LiveData<MoviesViewState>
//        get() = _viewState
//
//    fun getMovieIds() {
//        _viewState.postValue(MoviesViewState.Loading)
//        viewModelScope.launch(iODispatcher) {
//            val result = executeWithRetry(times = 3) {
//                homeRepository.getMovieIds()
//            }
//
//            when (result) {
//                is NetworkResponse.Success -> {
//                    _viewState.postValue(MoviesViewState.Success)
//                    val data = result.body
//
//                    _movieIds.postValue(data.results)
//                }
//
//                is NetworkResponse.NetworkError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            null,
//                            R.string.network_error_msg,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.ServerError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            AppUtil.getErrorResponse(result.body),
//                            null,
//                            null
//                        )
//                    )
//                }
//
//                is NetworkResponse.UnknownError -> {
//                    _viewState.postValue(
//                        MoviesViewState.Error(
//                            null,
//                            R.string.unknown_error_msg,
//                            null
//                        )
//                    )
//                }
//
//            }
//        }
//    }
//
//}
