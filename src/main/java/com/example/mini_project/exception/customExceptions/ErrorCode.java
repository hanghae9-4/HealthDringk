package com.example.mini_project.exception.customExceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    NOT_FOUND_MEMBER("NOT_FOUND_MEMBER", "회원을 찾을 수 없습니다."),
    NOT_FOUND_BOARD("NOT_FOUND_BOARD", "게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT("NOT_FOUND_COMMENT", "댓글을 찾을 수 없습니다."),
    NOT_FOUND_IMAGE("NOT_FOUND_IMAGE", "이미지를 첨부해주세요."),
    DUPLICATED_NAME("DUPLICATED_NAME", "중복된 아이디 입니다."),
    NOT_MATCHED_PASSWORD("NOT_MATCHED_PASSWORD", "비밀번호가 일치하지 않습니다."),
    NOT_VALID_FORMAT("NOT_VALID_FORMAT", "회원가입 양식을 확인해주세요."),
    NOT_VALID_MEMBER("NOT_VALID_MEMBER", "인증이 유효하지 않습니다."),
    NOT_VALID_WRITER("NOT_VALID_WRITER", "작성자가 아닙니다."),
    EXCEED_FILE_SIZE("EXCEED_FILE_SIZE", "파일의 용량은 5MB 까지 가능합니다.");


    private final String code;
    private final String message;


}
