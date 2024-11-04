package com.example.backend.config;

import com.example.backend.service.TokenService;
import com.example.backend.utility.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.*;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    @Override // Củ lìn này sẽ chạy khi SecurityConfig bắt xác thực,
    // bình thường thì đại đa số api từ frontend gọi tới đều phải cần xác thực,
    // bọn nó thông qua một bước lọc là củ lìn này nè!!!
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("AuthenticationToken");

        if (SecurityContextHolder.getContext().getAuthentication() == null ) { //  Chưa xác thực thì thực hiện xác thực
            if (authHeader != null && authHeader.startsWith("Bearer ")) { // Chả hiểu tại sao frontend phải gửi về kèm theo chữ người người gấu(Bearer) phía trước =))
                String token = authHeader.substring(7);

                if (!tokenService.isValidToken(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Unauthorized access: Invalid token.");
                    return;
                }

                String username = jwtUtil.extractName(token); // Giải mã lấy name cho t!!!!!
                List<GrantedAuthority> role = getGrantedAuthorities(token);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, role);

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication); // Lưu thông tin token đã giải mã vào, cần thì kiếm nó rồi lụm!
            }
        }

        filterChain.doFilter(request, response);
    }

    public List<GrantedAuthority> getGrantedAuthorities(String token) { // Thực ra web này mỗi tài khoản chỉ có 1 role
                                                                        // nhưng GPT nó bắt t viết List để dễ mở rộng!!!!
                                                                        // Nhưng mà t đel làm theo!!!! Ha ha
        String role =  jwtUtil.extractRole(token);
        return List.of(new SimpleGrantedAuthority(role));
    }

}
