package com.frodas.cine.admin.presentation.advice;

import com.frodas.cine.admin.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

import static com.frodas.cine.admin.util.enums.StatusType.*;
import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler //extends ResponseEntityExceptionHandler
{

    @Value("${app.show.log}")
    private boolean showLog;

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public @ResponseBody
    HttpErrorInfo handleBusinessException(BusinessException ex, WebRequest request) {
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex, MESSAGE_ERROR_BUSINESS.getString());
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DatabaseException.class)
    public @ResponseBody
    HttpErrorInfo handleDatabaseException(DatabaseException ex, WebRequest request) {
        StringWriter exception = new StringWriter();
        ex.printStackTrace(new PrintWriter(exception));
        var detailException = showLog ? exception.toString() : MESSAGE_ERROR_BUSINESS.getString() + "- Enable show exception information";
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex, detailException);
    }

    @ExceptionHandler(EmptyException.class)
    public final ResponseEntity<Void> handleEmptyException(EmptyException ex) {
        return ResponseEntity.status(NO_CONTENT).header("Mensaje", ex.getMessage().isEmpty() ? MESSAGE_NO_CONTENT.getString() : ex.getMessage()).build();
    }

    // Validacion general
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody
    HttpErrorInfo handleInternalServerError(Exception ex, WebRequest request) {
        return createHttpErrorInfo(INTERNAL_SERVER_ERROR, request, ex, "");
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public @ResponseBody
    HttpErrorInfo handleBadRequestExceptions(BadRequestException ex, WebRequest request) {
        return createHttpErrorInfo(BAD_REQUEST, request, ex, "");
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public @ResponseBody
    HttpErrorInfo handleNotFoundExceptions(NotFoundException ex, WebRequest request) {
        return createHttpErrorInfo(NOT_FOUND, request, ex, "");
    }

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(InvalidInputException.class)
    public @ResponseBody
    HttpErrorInfo handleInvalidInputException(InvalidInputException ex, WebRequest request) {
        return createHttpErrorInfo(UNPROCESSABLE_ENTITY, request, ex, "");
    }

    private HttpErrorInfo createHttpErrorInfo(HttpStatus httpStatus, WebRequest request, Exception ex, String message) {
        final String path = request.getDescription(false);
        final String exceptionMessage = ex.getMessage();
        log.debug("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new HttpErrorInfo(httpStatus, path, exceptionMessage, message);
    }

    // Validacion de parametros - @Valid
//    @Override
//    protected final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        List<String> errors = new ArrayList<>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//        ErrorDetail errorDetail = ErrorDetail.builder().message(MESSAGE_REQUEST_INVALID_MESSAGE.getString()).timestamp(LocalDateTime.now()).statusCode(BAD_REQUEST.value()).errors(errors).build();
//        return ResponseEntity.status(BAD_REQUEST).body(errorDetail);
//    }
//
//    // Validacion de parametros - @PathVariable
//    @Override
//    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetail errorDetail = ErrorDetail.builder().message(MESSAGE_ERROR_EXCEPTION_BAD_REQUEST.getString()).timestamp(LocalDateTime.now()).statusCode(STATUS_CODE_ERROR_BUSINESS.getNumber()).build();
//        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
//    }
//
//    // Validacion de parametros - @RequestParam
//    @Override
//    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        ErrorDetail errorDetail = ErrorDetail.builder().message(MESSAGE_ERROR_EXCEPTION_BAD_REQUEST.getString()).timestamp(LocalDateTime.now()).statusCode(STATUS_CODE_ERROR_BUSINESS.getNumber()).build();
//        return new ResponseEntity<>(errorDetail, BAD_REQUEST);
//    }

}
