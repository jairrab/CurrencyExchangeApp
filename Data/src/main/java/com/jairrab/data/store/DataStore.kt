package com.jairrab.data.store

import javax.inject.Inject

class DataStore @Inject constructor(
    private val cacheDataStore: CacheDataStore,
    private val remoteDataStore: RemoteDataStore
) {

    fun getDataStore(dataSource: DataSource) = when (dataSource) {
        DataSource.REMOTE -> remoteDataStore
        DataSource.CACHE  -> cacheDataStore
    }
}

enum class DataSource {
    CACHE,
    REMOTE
}