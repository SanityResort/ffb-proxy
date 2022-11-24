package com.fumbbl.ffbproxy.errorhandling

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(exception: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        return handleExceptionInternal(
            exception, exception.message,
            HttpHeaders(), HttpStatus.BAD_REQUEST, request
        )
    }
}