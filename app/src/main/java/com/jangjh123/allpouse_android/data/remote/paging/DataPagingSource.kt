package com.jangjh123.allpouse_android.data.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jangjh123.allpouse_android.data.model.Perfume
import com.jangjh123.allpouse_android.data.remote.NetworkHelper
import com.jangjh123.allpouse_android.data.remote.parseToType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class DataPagingSource @Inject constructor(
    private val networkHelper: NetworkHelper,
) : PagingSource<Int, Perfume>() {
    override fun getRefreshKey(state: PagingState<Int, Perfume>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Perfume> {
        val page = params.key ?: 0
        return try {
            val data = networkHelper.client()
                .fetchPagedPerfumeList(
                    page = page,
                    size = 4
                ).get("pages").asJsonObject.get("content").asJsonArray.map { perfume ->
                    parseToType(
                        type = Perfume::class.java,
                        perfume.asJsonObject
                    )
                }

            val nextKey = if (data.isEmpty()) null else page + 1

            LoadResult.Page(
                data = data,
                prevKey = if (page == 0) 0 else page + 1,
                nextKey = nextKey!!
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}