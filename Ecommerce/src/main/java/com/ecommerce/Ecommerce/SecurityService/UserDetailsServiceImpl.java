package com.ecommerce.Ecommerce.SecurityService;

import com.ecommerce.Ecommerce.Model.User;
import com.ecommerce.Ecommerce.Repository.UserJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserJPARepository userJPARepositoryRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userJPARepositoryRepository.findByUserName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not found with username " + username));
        return UserDetailsImpl.build(user);
    }
}
