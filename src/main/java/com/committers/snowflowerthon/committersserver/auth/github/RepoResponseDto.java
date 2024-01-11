package com.committers.snowflowerthon.committersserver.auth.github;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RepoResponseDto {
    String full_name; // repo 이름만 받아옴
}
