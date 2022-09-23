package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import com.hsba.bi.fitnessstudio.Fitnessstudio.appointment.entity.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
/**
 * @abakhtiari Service-Klasse für User-Objekt
 */

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

    public Trainer getTrainer(Long id) {
        return userRepository.findTrainerById(id).orElse(null);
    }

}