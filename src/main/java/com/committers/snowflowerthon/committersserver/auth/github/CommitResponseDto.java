package com.committers.snowflowerthon.committersserver.auth.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommitResponseDto {
    String sha; // 커밋 해시("sha")
    Author author; // 커밋 작성자("author")
}
