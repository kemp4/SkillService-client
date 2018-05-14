package pl.kemp.interceptor;

        import org.apache.tomcat.util.codec.binary.Base64;
        import org.springframework.http.HttpHeaders;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.http.HttpRequest;
        import org.springframework.http.client.ClientHttpRequestExecution;
        import org.springframework.http.client.ClientHttpRequestInterceptor;
        import org.springframework.http.client.ClientHttpResponse;
        import org.springframework.security.core.Authentication;
        import org.springframework.security.core.context.SecurityContextHolder;
        import java.io.IOException;
        import java.nio.charset.Charset;

public class SecurityHeadersInterceptor implements ClientHttpRequestInterceptor {

    @Value("#{properties.systemKey}")
    private String systemKey;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        HttpHeaders headers = request.getHeaders();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        String auth = name + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );
        headers.add("Authorization",authHeader);
        headers.add("Accept","*/*");
        return execution.execute(request, body);
    }
}