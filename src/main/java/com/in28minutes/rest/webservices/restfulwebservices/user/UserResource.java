package com.in28minutes.rest.webservices.restfulwebservices.user;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.hateoas.*;


import javax.validation.Valid;
import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {

    @Autowired
    private UserDoaService service;

    //GET /users
    //retrieveAllUsers
    @GetMapping("/users")

    public List<User> retrieveAllUsers(){
        return service.findAl();
    }

    //GET /users/{id}
    //retrieveUser(int id)

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUser(@PathVariable int id){
        User user = service.findOne(id);
        if(user == null)
            throw new UserNotFoundException("id-" + id);

        //"all-users" , SERVER_PATH + "/users
        //retrieveAllUsers

        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        model.add(linkTo.withRel("all-users").withType("GET"));

        //HATEOAS

        return model;

    }
    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);
        if(user == null)
            throw new UserNotFoundException("id-" + id);
    }

    //input - details of user
    //output -CREATED & Return the created URI
    @PostMapping("/users")
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user){
         User savedUser = service.save(user);

         //CREATED
        // /users/{id}      savedUser.getId

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id]")
                .buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();

    }

    @PutMapping("/users/{id}")

    public @Valid User updateUser(@Valid @RequestBody User user) {
        User upUser = service.update(user);

        return upUser;

    }

}
