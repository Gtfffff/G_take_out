package com.g.oauth.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.g.oauth.JsonDeserializer.RoleSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Gtf
 * @Date: 2022/6/19-06-19-20:03
 * @Description: com.g.security.authority
 * @Version: 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize(using = RoleSerializer.class)
public class Role implements GrantedAuthority {

    private String name;
    private List<SimpleGrantedAuthority> authorities = new ArrayList<>();

    @Override
    @JsonIgnore
    public String getAuthority() {
        return name;
    }
}
