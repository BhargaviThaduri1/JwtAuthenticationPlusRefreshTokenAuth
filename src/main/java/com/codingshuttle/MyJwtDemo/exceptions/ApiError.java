package com.codingshuttle.MyJwtDemo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

@Data

public class ApiError<T> {

    private Instant timestamp;

    private T data;

    private HttpStatusCode httpStatusCode;

    public ApiError(){
        this.timestamp = Instant.now();
    }
    public ApiError(T data, HttpStatusCode httpStatusCode){
        this();
        this.data = data;
        this.httpStatusCode = httpStatusCode;
    }
}
