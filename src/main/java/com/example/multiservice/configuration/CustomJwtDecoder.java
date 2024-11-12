package com.example.multiservice.configuration;

import com.example.multiservice.dto.request.IntrospectRequest;
import com.example.multiservice.dto.response.IntrospectResponse;
import com.example.multiservice.service.AuthenService;
import com.nimbusds.jose.JOSEException;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
public class CustomJwtDecoder implements JwtDecoder {

    @NonFinal// marking to do not let Inject vào Contructor
    @Value("${jwt.secret.key}")
    protected String secrect;
    private static final Logger logger = LoggerFactory.getLogger(CustomJwtDecoder.class);


    @Autowired
    private AuthenService authenService;

    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        logger.debug("Starting JWT decode process");

         IntrospectResponse introspectResponse = authenService.introspect(
                IntrospectRequest.builder().token(token).build());

        if (!introspectResponse.isValid()) {
            logger.warn("Token invalid, aborting decode");
            throw new JwtException("Invalid token");
        }

         if (Objects.isNull(nimbusJwtDecoder)) {
            initializeDecoder();
        }

        try {
            logger.debug("Decoding token with NimbusJwtDecoder");
            return nimbusJwtDecoder.decode(token);
        } catch (JwtException ex) {
            logger.error("Failed to decode JWT", ex);
            throw new JwtException("Failed to decode JWT", ex);
        }
    }

    private void initializeDecoder() {
        logger.debug("Initializing NimbusJwtDecoder");
        SecretKeySpec secretKeySpec = new SecretKeySpec(secrect.getBytes(), "HmacSHA512");
        this.nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

//    @Override
//    public Jwt decode(String token) throws JwtException {
//
//
//        try {
//            IntrospectResponse introspectResponse = authenService.introspect(IntrospectRequest.builder().token(token).build());
//
//            if (!introspectResponse.isValid()) throw new JwtException("Token invalid");// if false throw exception and dont let request
//        } catch (JOSEException  | ParseException e) {
//            throw new JwtException(e.getMessage());
//        }
//
//
//
//
//
//
//
//        if (Objects.isNull(nimbusJwtDecoder)) {
//            SecretKeySpec secretKeySpec = new SecretKeySpec(secrect.getBytes(), "HS512");
//            nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
//                    .macAlgorithm(MacAlgorithm.HS512)
//                    .build();
//        }
//
//        return nimbusJwtDecoder.decode(token);
//
//
//    }
}


/**
 * Mục đích của JwtDecoder:
 *
 * <ul>
 *     <li><b>1. Giải mã JWT:</b> JwtDecoder nhận JWT từ yêu cầu HTTP và giải mã token này
 *         bằng khóa bí mật hoặc khóa công khai tương ứng (trong trường hợp token sử dụng mã hóa đối xứng
 *         hoặc bất đối xứng). Điều này giúp đảm bảo tính toàn vẹn của token.</li>
 *
 *     <li><b>2. Xác thực chữ ký JWT:</b> JwtDecoder kiểm tra chữ ký của token để xác nhận rằng nó chưa
 *         bị chỉnh sửa và được phát hành bởi một nguồn đáng tin cậy. Nếu token có chữ ký không hợp lệ,
 *         JwtDecoder sẽ ném ra ngoại lệ (JwtException) và từ chối token.</li>
 *
 *     <li><b>3. Kiểm tra các claims trong token:</b> JwtDecoder phân tích và kiểm tra các claims của JWT,
 *         bao gồm thông tin về người dùng, thời gian hết hạn, quyền truy cập, và các thông tin khác mà ứng dụng yêu cầu.
 *         Điều này giúp xác định quyền truy cập của người dùng đối với tài nguyên.</li>
 *
 *     <li><b>4. Ủy quyền trong Spring Security:</b> Sau khi giải mã và xác thực, JwtDecoder sẽ cung cấp một
 *         đối tượng Jwt chứa các claims của token. Spring Security sau đó sẽ sử dụng thông tin này để xác định
 *         và kiểm tra quyền truy cập cho người dùng.</li>
 * </ul>
 * <p>
 * Quy trình sử dụng JwtDecoder trong Spring Security:
 *
 * <ol>
 *     <li><b>1. Nhận token từ yêu cầu:</b> JWT thường được truyền trong header Authorization của yêu cầu HTTP.</li>
 *
 *     <li><b>2. Giải mã và xác thực token:</b> JwtDecoder giải mã JWT, kiểm tra chữ ký, và kiểm tra các claims
 *         như thời gian hết hạn hoặc thông tin về người dùng.</li>
 *
 *     <li><b>3. Xác thực quyền truy cập:</b> Dựa trên thông tin trong JWT, Spring Security sẽ xác thực
 *         và gán các quyền truy cập cho người dùng (thông qua GrantedAuthority).</li>
 *
 *     <li><b>4. Xử lý ủy quyền:</b> Dựa vào quyền truy cập của người dùng trong token, Spring Security sẽ
 *         quyết định xem người dùng có thể truy cập tài nguyên hay không.</li>
 * </ol>
 */



