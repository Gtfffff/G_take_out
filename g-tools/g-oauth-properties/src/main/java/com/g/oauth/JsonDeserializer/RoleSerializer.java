package com.g.oauth.JsonDeserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.g.oauth.entity.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;

/**
 * @Author: Gtf
 * @Date: 2022/6/19-06-19-23:45
 * @Description: com.g.commons.JsonDeserializer
 * @Version: 1.0
 */
public class RoleSerializer extends JsonDeserializer<Role> {

    @Override
    public Role deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        Role role = new Role();
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        role.setName(node.get("name").asText());

        Iterator<JsonNode> elements = node.get("authorities").elements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            role.getAuthorities().add(new SimpleGrantedAuthority(authority.asText()));
        }
        return role;
    }
}