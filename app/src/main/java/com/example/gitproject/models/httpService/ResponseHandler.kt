package com.example.gitproject.models.httpService

public interface ResponseHandler<T> {
    fun response(response: T)
}
