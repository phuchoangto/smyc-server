package to.phuc.smyc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import to.phuc.smyc.domain.User;
import to.phuc.smyc.model.RegistrationRequest;
import to.phuc.smyc.repos.UserRepository;


@Service
@Slf4j
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(final UserRepository userRepository,
            final PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean usernameExists(final RegistrationRequest registrationRequest) {
        return userRepository.existsByUsernameIgnoreCase(registrationRequest.getUsername());
    }

    public void register(final RegistrationRequest registrationRequest) {
        log.info("registering new user: {}", registrationRequest.getUsername());

        final User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setEmail(registrationRequest.getEmail());
        user.setEmailVerifiedAt(registrationRequest.getEmailVerifiedAt());
        userRepository.save(user);
    }

}
