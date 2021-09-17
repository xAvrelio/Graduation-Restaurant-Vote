package com.restaurant.restaurantvote.web;

import com.restaurant.restaurantvote.AuthUser;
import com.restaurant.restaurantvote.model.User;
import com.restaurant.restaurantvote.repository.UserRepository;
import com.restaurant.restaurantvote.to.UserTo;
import com.restaurant.restaurantvote.util.ValidationUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
@RestController
@RequestMapping(AccountController.URL)
@AllArgsConstructor
@Slf4j
//@Tag(name = "Account Controller")
public class AccountController implements RepresentationModelProcessor<RepositoryLinksResource> {

    static final String URL = "/rest/account";

    @SuppressWarnings("unchecked")
    private static final RepresentationModelAssemblerSupport<UserTo, EntityModel<UserTo>> ASSEMBLER =
            new RepresentationModelAssemblerSupport<>(AccountController.class, (Class<EntityModel<UserTo>>) (Class<?>) EntityModel.class) {
                @Override
                public EntityModel<UserTo> toModel(UserTo user) {
                    return EntityModel.of(user, linkTo(AccountController.class).withSelfRel());
                }
            };

    private final UserRepository userRepository;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public EntityModel<UserTo> get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get {}", authUser);
        return ASSEMBLER.toModel(authUser.getUserTo());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", key = "#authUser.username")
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete {}", authUser);
        userRepository.deleteById(authUser.getId());
    }

//    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @ResponseStatus(value = HttpStatus.CREATED)
//    public ResponseEntity<EntityModel<UserTo>> register(@Valid @RequestBody User user) {
//        log.info("register {}", user);
//        ValidationUtil.checkNew(user);
//        user.setRoles(EnumSet.of(Role.USER));
//        user = userRepository.save(user);
//        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/api/account")
//                .build().toUri();
//        return ResponseEntity.created(uriOfNewResource).body(ASSEMBLER.toModel(user));
//    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CachePut(value = "users", key = "#authUser.username")
    public User update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} to {}", authUser, user);
        UserTo oldUser = authUser.getUserTo();
        ValidationUtil.assureIdConsistent(user, oldUser.getId());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        return userRepository.save(user);
    }

/*
    @GetMapping(value = "/pageDemo", produces = MediaTypes.HAL_JSON_VALUE)
    public PagedModel<EntityModel<User>> pageDemo(Pageable page, PagedResourcesAssembler<User> pagedAssembler) {
        Page<User> users = userRepository.findAll(page);
        return pagedAssembler.toModel(users, ASSEMBLER);
    }
*/

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        resource.add(linkTo(AccountController.class).withRel("account"));
        return resource;
    }
}
