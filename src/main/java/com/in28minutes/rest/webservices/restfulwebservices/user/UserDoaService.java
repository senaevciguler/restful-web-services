package com.in28minutes.rest.webservices.restfulwebservices.user;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
public class UserDoaService {

    private static List<User> users = new ArrayList<>();
    int userCount = 3;

    static {
        users.add(new User(1, "Sena", new Date()));
        users.add(new User(2, "Mehmet", new Date()));
        users.add(new User(3, "Alp", new Date()));
    }

    public List<User> findAl() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(++userCount);
        }
        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for (User user : users) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User deleteById(int id) {
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getId() == id) {
                iterator.remove();
                return user;
            }
        }
        return null;
    }
    public User update(User userUp) {
        for(User user: users){
            if(user.getId() == userUp.getId()) {
                users.remove(user);
                users.add(userUp);
            }
        }
        return userUp;
    }
}
