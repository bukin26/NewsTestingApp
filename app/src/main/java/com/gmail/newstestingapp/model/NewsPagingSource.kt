package com.gmail.newstestingapp.model

import androidx.paging.PagingSource
import androidx.paging.PagingState

class NewsPagingSource constructor(
    private val newsService: NewsService
) : PagingSource<Int, NewsResponseItem>() {
    override fun getRefreshKey(state: PagingState<Int, NewsResponseItem>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsResponseItem> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val news = newsService.getNews(pageNumber)

        return try {
            return LoadResult.Page(
                data = news,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (news.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 0
    }
}