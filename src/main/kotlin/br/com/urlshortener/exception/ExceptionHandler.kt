package br.com.urlshortener.exception

import br.com.urlshortener.dto.ErrorDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(exception: NotFoundException,
                       request: HttpServletRequest): ErrorDTO {
        return ErrorDTO(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(exception: MethodArgumentNotValidException,
                              request: HttpServletRequest): ErrorDTO {
        val errorMessage = HashMap<String, String?>()
        exception.bindingResult.fieldErrors.forEach{
            errorMessage.put(it.field, it.defaultMessage)
        }
        return ErrorDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleServerError(exception: Exception,
                       request: HttpServletRequest): ErrorDTO {
        return ErrorDTO(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = HttpStatus.INTERNAL_SERVER_ERROR.name,
            message = exception.message,
            path = request.servletPath
        )
    }
}