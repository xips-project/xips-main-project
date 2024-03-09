package trastu.dev.xips.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import trastu.dev.xips.dto.AuthenticationRequest;
import trastu.dev.xips.dto.AuthenticationResponse;
import trastu.dev.xips.dto.UserDTO;
import trastu.dev.xips.entities.User;
import trastu.dev.xips.repositories.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getUsername(), authenticationRequest.getPassword()
        );

        authenticationManager.authenticate(authenticationToken);

        User user = userRepository.findByUsername(authenticationRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));

        return new AuthenticationResponse(jwt);
    }


    private Map<String, Object> generateExtraClaims(User user) {

         Map<String, Object> extraClaims = new HashMap<>();
         extraClaims.put("role", user.getRole());
         extraClaims.put("permissions", user.getAuthorities());

         return extraClaims;
    }

}
