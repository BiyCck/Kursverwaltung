package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Trainer> findAllTrainer(){
        return userRepository.findAllTrainer();
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Trainer save(Trainer trainer) {
        return userRepository.save(trainer);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public Trainer getTrainer(Long id) {
        return userRepository.findTrainerById(id).orElse(null);
    }

    public Trainer getTrainerByName(String username) {
        return userRepository.findTrainerByUsername(username).orElse(null);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username){
        return userRepository.findDistinctByUsername(username);
    }

}
