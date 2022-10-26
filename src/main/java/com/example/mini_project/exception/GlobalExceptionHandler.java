package com.example.mini_project.exception;

import com.example.mini_project.entity.Error;
import com.example.mini_project.exception.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.SizeLimitExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundMemberException.class)
    public ResponseEntity<Error> handleNotFoundMemberException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_FOUND_MEMBER.getCode(), ErrorCode.NOT_FOUND_MEMBER.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundBoardException.class)
    public ResponseEntity<Error> handleNotFoundBoardException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_FOUND_BOARD.getCode(), ErrorCode.NOT_FOUND_BOARD.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundCommentException.class)
    public ResponseEntity<Error> handleNotFoundCommentException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_FOUND_COMMENT.getCode(), ErrorCode.NOT_FOUND_COMMENT.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundImageException.class)
    public ResponseEntity<Error> handleNotFoundImageException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_FOUND_IMAGE.getCode(), ErrorCode.NOT_FOUND_IMAGE.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicatedNameException.class)
    public ResponseEntity<Error> handleDuplicatedNameException(){
        return new ResponseEntity<>(new Error(ErrorCode.DUPLICATED_NAME.getCode(), ErrorCode.DUPLICATED_NAME.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> handleNotValidFormatException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_VALID_FORMAT.getCode(), ErrorCode.NOT_VALID_FORMAT.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotMatchedPasswordException.class)
    public ResponseEntity<Error> handleNotMatchedPasswordException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_MATCHED_PASSWORD.getCode(), ErrorCode.NOT_MATCHED_PASSWORD.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotValidMemberException.class)
    public ResponseEntity<Error> handleNotValidMemberException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_VALID_MEMBER.getCode(), ErrorCode.NOT_VALID_MEMBER.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NotValidWriterException.class)
    public ResponseEntity<Error> handleNotValidWriterException(){
        return new ResponseEntity<>(new Error(ErrorCode.NOT_VALID_WRITER.getCode(), ErrorCode.NOT_VALID_WRITER.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(SizeLimitExceededException.class)
    public ResponseEntity<Error> handleSizeLimitException(){
        return new ResponseEntity<>(new Error(ErrorCode.EXCEED_FILE_SIZE.getCode(), ErrorCode.EXCEED_FILE_SIZE.getMessage()),
                HttpStatus.FORBIDDEN);
    }

}
