package com.jairrab.data.state

import com.jairrab.domain.entities.ServerApiError

class DataApiError(override val message: String?) : Exception(message), ServerApiError