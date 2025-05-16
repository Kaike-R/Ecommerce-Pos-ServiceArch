package br.com.kaikedev.userservice.Service;


import br.com.kaikedev.userservice.Entity.UserEntity;
import br.com.kaikedev.userservice.Repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private UserRepository userRepository;

    private ArrayList<UserEntity> buffer = new ArrayList<UserEntity>();

    private final Integer BUFFER_SIZE = 100;
    private final Integer DELAY = 1000;//millisegundos o parametro irá rodar cada segundo

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Boolean deleteUser(Integer id) {
        return userRepository.deleteUserById(id);
    }


    public Collection<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Integer updateUser(UserEntity userEntity) {
        return userRepository.update(userEntity);
    }

    public Boolean insertUser(UserEntity user) {

        buffer.add(user);

        if (buffer.size() >= BUFFER_SIZE) {
            flush();
        }

        return true;
    }

    //Esse metodo serve para gerar uma copia da lista padrão da classe e usar essa copia para fazer o insert
    //é necessario isso pois foi implementado um batch insert no serviço
    //que serve para acumular os inserts ao inves de fazer eles de um em um
    @Scheduled(fixedRateString = "${app.scheduleFlushTime}")
    private synchronized void flush() {

        if (buffer.size() > 0) {
            ArrayList<UserEntity> users = (ArrayList<UserEntity>) buffer.clone();
            log.info("Rodando o flush de {} usuarios ...", users.size());
            buffer.clear();
            userRepository.bulkInsert(users);
        }
    }



}
