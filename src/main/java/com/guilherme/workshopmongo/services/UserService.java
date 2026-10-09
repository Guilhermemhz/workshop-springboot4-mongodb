package com.guilherme.workshopmongo.services;

import com.guilherme.workshopmongo.DTO.UserDTO;
import com.guilherme.workshopmongo.domain.User;
import com.guilherme.workshopmongo.repositories.UserRepository;
import com.guilherme.workshopmongo.services.exception.ObjectNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public List<User> findAll(){
        return repository.findAll();
    }

    public User findById(String id) {
        Optional<User> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User insert(User obj) {
        return repository.insert(obj);
    }

    public User fromDTO(UserDTO objDto) {
        return new User(objDto.getId(), objDto.getName(), objDto.getEmail());
    }

    public void delete(String id) {
        findById(id);
        repository.deleteById(id);
    }

    public User update(User obj) {
        User newObj = findById(obj.getId());
        updateData(newObj, obj);
        return repository.save(newObj);
    }

    private void updateData(User newObj, User obj) {
        BeanUtils.copyProperties(obj, newObj);
    }

}
