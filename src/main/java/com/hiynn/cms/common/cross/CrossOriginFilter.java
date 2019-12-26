package com.hiynn.cms.common.cross;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 跨域过滤器
 *
 * @author 张朋
 * @date 2019/11/1 15:10
 */
@Slf4j
public class CrossOriginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
        log.info("CrossOriginFilter init ...");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Headers", "*");
        resp.setHeader("Access-Control-Allow-Methods", "*");
        resp.setHeader("Access-Control-Allow-Credentials", "true");
        resp.setHeader("Access-Control-Max-Age", "3600");
        chain.doFilter(request, resp);
    }

    @Override
    public void destroy() {

    }
}
