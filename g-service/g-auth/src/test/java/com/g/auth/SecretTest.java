package com.g.auth;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.g.commons.base.entity.po.User;
import com.g.commons.utils.JwtUtils;
import com.g.oauth.entity.Role;
import com.g.oauth.entity.UserDetailsDto;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.Instant;
import java.util.ArrayList;

/**
 * @Author: Gtf
 * @Date: 2022/5/12-05-12-18:10
 * @Description: com.g.auth
 * @Version: 1.0
 */
//@SpringBootTest
public class SecretTest {
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//    @Test
//    public void secret(){
//        String secret = bCryptPasswordEncoder.encode("secret");
//        System.out.println(secret);
//    }
    @Test
    public void jwt(){
        String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJsX3R5cGUiOiJwYXNzd29yZCIsImF1ZCI6WyJyZXMxIl0sInVzZXJfbmFtZSI6ImFkbWluIiwic2NvcGUiOlsiQzEiXSwiZXhwIjoxNjU1Mzk2NTQ1LCJhdXRob3JpdGllcyI6WyJwMSIsInAyIl0sImp0aSI6IjNhNjUxYjI5LTYxMjctNGI1ZS04ZTIzLTVjYjliZjhiZWM1OSIsImNsaWVudF9pZCI6ImMxIn0.EPnx9K7QmdUXfLeIjD-c1cLi7qVr2o6cRF_JTNxBIpQ8OD4kDYaCCthnO0UJRZCubAjQFSJj2jb0DrQxZDY7rhD5yoad8-4gmgo2cryl309yhaE7maQ2Zj0x3PU25iOHfyDA3MFR_V7W8oFSh91hAleqzkSHmdQjsC3T5LRc0JAZlC3qwg-STJf3tQ6otRH7SFN0YK2HykpEYKBv9DDY4lJZ8grk-sgj1_pikJ5tOx0igN_fU0hNBeHHD-cc25kdOJ9dA8llHW0I8BSMSmH0H2gPuqpvTbw9dX0vEPAHACNfSN9FDD0nNJ_vNoqaFmnlsX0esggzHZSGEHa5Tl6nKA";
        JSONObject jwtPayload = JwtUtils.getJwtPayload(token);
        int exp = (int)jwtPayload.get("exp");
        long l = exp - Instant.now().getEpochSecond();
        System.out.println(l);
    }
    @Test
    public void json(){
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        SimpleGrantedAuthority p1 = new SimpleGrantedAuthority("p1");
        SimpleGrantedAuthority p2 = new SimpleGrantedAuthority("p2");
        authorities.add(p1);
        authorities.add(p2);
        ArrayList<Role> roles = new ArrayList<>();
        Role admin = new Role("admin", authorities);
        roles.add(admin);
        UserDetailsDto userDetailsDto = new UserDetailsDto(new User(), roles);
        System.out.println(userDetailsDto.toString());
        JSON parse = JSONUtil.parse(userDetailsDto);
        System.out.println(parse);
        Object user = parse.getByPath("user");
        JSONArray roles2 = JSONUtil.parseArray(parse.getByPath("roles"));
        System.out.println(roles2);


    }

}
