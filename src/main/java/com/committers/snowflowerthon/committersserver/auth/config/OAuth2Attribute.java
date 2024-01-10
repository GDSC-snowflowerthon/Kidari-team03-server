package com.committers.snowflowerthon.committersserver.auth.config;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@ToString
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String nickname;

    public static OAuth2Attribute of(String attributeKey, Map<String, Object> attributes) {
        String nickname = (String) attributes.get("login");
        return OAuth2Attribute.builder()
                .nickname(String.valueOf(nickname))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    public Map<String, Object> convertToMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("nickname", attributeKey);
        map.put("nickname", nickname);

        return map;
    }
}

