package org.mla.cms.service;

import org.mla.cms.model.Users;
import org.mla.cms.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    private UserRepository usersRepository;

    public Users createUser(Users user) {
        return usersRepository.save(user);
    }

    public Users getUserById(Integer UsersId) {
        Optional<Users> optionalUsers = usersRepository.findById(UsersId);
        return optionalUsers.get();
    }

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    public Users updateUser(Users Users) {
        Users existingUsers = usersRepository.findById(Users.getUserId()).get();
        existingUsers.setUserName(Users.getUserName());
        existingUsers.setCity(Users.getCity());
        existingUsers.setContactNumber(Users.getContactNumber());
        existingUsers.setState(Users.getState());
        existingUsers.setEmailId(existingUsers.getEmailId());
        Users updatedUsers = usersRepository.save(existingUsers);
        return updatedUsers;
    }

    public void deleteUser(Integer UsersId) {
        usersRepository.deleteById(UsersId);
    }
}
