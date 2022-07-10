package com.g.security.state;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Service
public class ExceptionStateManager {

    @Resource
    private List<ExceptionState> states;

    /**
     * 找出是否有满足条件的策略并处理
     * @param request
     * @param response
     * @param e
     * @return
     * @throws IOException
     */
    public boolean execute(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        for (ExceptionState state : states){
            if (state.supports(e.getClass())){
                state.execute(request,response,e);
                return true;
            }
        }
        return false;
    }
}