package com.information;

import com.information.base.ErrorCode;
import com.information.pojo.VipUser;
import com.mysql.cj.util.StringUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenHelper {

    private static TokenHelper instance;

    // Token免登陆时间 单位：天（一周）
    @Value("${jwt.token.timeout}")
    private static int timeout;
    public static final String key = "fc63d491-b502-40d8-958c-04fb79b50005";
    public static final String PRO_AUTHOR = "pro-author";
    public static final String PRO_ACCESS_TOKEN = "pro-access";


    public static void putHeaderVipToken(VipUser adminUser, HttpServletResponse response) {

        DateTime start = DateTime.now();
        DateTime end = DateTime.now().plusDays(timeout);
        end = new DateTime(end.getYear(), end.getMonthOfYear(), end.getDayOfMonth(), 23, 59, 59, 999);

        String auth_token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
                .setSubject(String.valueOf(adminUser.getId()))
//                .setIssuer(adminUser.getaAccount())//发行人
                .setIssuedAt(start.toDate())
                .setExpiration(end.toDate())//到期时间
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        String access_authority_token = Jwts.builder()
                .setId(UUID.randomUUID().toString())
//                .setIssuer(adminUser.getaAccount())
                .setIssuedAt(start.toDate())
                .setExpiration(end.toDate())
                .signWith(SignatureAlgorithm.HS512, key).compact();

        response.setHeader(PRO_AUTHOR, auth_token);
        response.setHeader(PRO_ACCESS_TOKEN, access_authority_token);

    }


    public static Integer checkToken(HttpServletRequest request) {
        try {
            String proAuthor = request.getHeader(PRO_AUTHOR);
            String proAccessToken = request.getHeader(PRO_ACCESS_TOKEN);

            if (StringUtils.isNullOrEmpty(proAuthor) || StringUtils.isNullOrEmpty(proAccessToken)) {
                throw new HaltException(ErrorCode.ERROR_RELOGIN, "请重新登录");
            }

            Jwt auth = Jwts.parser().setSigningKey(key).parse(proAuthor);
            Jwt access = Jwts.parser().setSigningKey(key).parse(proAccessToken);

            Map authMap = (Map) auth.getBody();
            String userId = (String) authMap.get("sub");
            return Integer.parseInt(userId);

        } catch (ExpiredJwtException e) {
            throw new HaltException(HttpStatus.UNAUTHORIZED.value(), "请重新登录");
        } catch (Exception e) {
            throw new HaltException(HttpStatus.UNAUTHORIZED.value(), "请重新登录");
        }


    }
}
