package life.ly.community.community.Controller;

import life.ly.community.community.dto.AccessTokenDTO;
import life.ly.community.community.dto.GithubUser;
import life.ly.community.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;
    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${guthub.redirect.url}")
    private String guthubredirecturl;


    @GetMapping("/callback")
    public String callback(@RequestParam(name ="code") String code,
                           @RequestParam(name ="state") String state)
    {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(guthubredirecturl);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.gitUser(accessToken);
        System.out.println(user.getName());

        return "index";
    }
}
