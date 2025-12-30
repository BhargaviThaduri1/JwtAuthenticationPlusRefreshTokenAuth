package com.codingshuttle.MyJwtDemo.dto;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class PostDTO {
    Long id;

    String title;

    String description;
}
