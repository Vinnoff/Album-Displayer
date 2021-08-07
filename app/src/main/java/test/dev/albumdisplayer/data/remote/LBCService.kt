package test.dev.albumdisplayer.data.remote

import retrofit2.Response
import retrofit2.http.GET
import test.dev.albumdisplayer.data.remote.response.GetAlbumListResponse

interface LBCService {
    @GET("/img/shared/technical-test.json")
    suspend fun getAlbumList(): Response<List<GetAlbumListResponse>>

}
