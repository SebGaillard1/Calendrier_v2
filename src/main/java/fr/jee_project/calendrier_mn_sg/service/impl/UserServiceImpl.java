package fr.jee_project.calendrier_mn_sg.service.impl;

import fr.jee_project.calendrier_mn_sg.business.UserCal;
import fr.jee_project.calendrier_mn_sg.repository.UserRepository;
import fr.jee_project.calendrier_mn_sg.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    @Override
    public Page<UserCal> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCal user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return user;
    }

    @Override
    public UserCal findByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    @Override
    public UserCal findById(Long id) {
        return this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    @Override
    public void save(UserCal user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSolde(500);

        this.userRepository.save(user);
    }

    @Override
    public void delete(UserCal user) {
        this.userRepository.delete(user);
    }

    @Override
    public UserCal subractPoints(UserCal user, int points) {
        user.setSolde(user.getSolde() - points);
        return this.userRepository.save(user);
    }

    @Override
    public UserCal utilisateurFromSecurityContext(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return (UserCal) authentication.getPrincipal();
    }
}
