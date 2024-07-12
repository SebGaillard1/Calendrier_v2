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
    private UserRepository utilisateurRepository;

    @Override
    public Page<UserCal> findAll(Pageable pageable) {
        return this.utilisateurRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserCal utilisateur = utilisateurRepository.findByEmail(username);

        if (utilisateur == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }

        return utilisateur;
    }

    @Override
    public UserCal findByEmail(String email) {
        return this.utilisateurRepository.findByEmail(email);
    }

    @Override
    public UserCal findById(Long id) {
        return this.utilisateurRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }

    @Override
    public void save(UserCal utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        utilisateur.setSolde(500);

        this.utilisateurRepository.save(utilisateur);
    }

    @Override
    public void delete(UserCal utilisateur) {
        this.utilisateurRepository.delete(utilisateur);
    }

    @Override
    public UserCal subractPoints(UserCal utilisateur, int points) {
        utilisateur.setSolde(utilisateur.getSolde() - points);
        return this.utilisateurRepository.save(utilisateur);
    }

    @Override
    public UserCal utilisateurFromSecurityContext(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        if (authentication == null) {
            throw new UsernameNotFoundException("Utilisateur non trouvé");
        }
        return (UserCal) authentication.getPrincipal();
    }
}
