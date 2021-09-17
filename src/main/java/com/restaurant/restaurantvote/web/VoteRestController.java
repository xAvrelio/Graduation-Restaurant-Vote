package com.restaurant.restaurantvote.web;

import com.restaurant.restaurantvote.service.VoteService;
import com.restaurant.restaurantvote.util.ClockUtil;
import com.restaurant.restaurantvote.util.DateTimeUtil;
import com.restaurant.restaurantvote.util.exception.ToLateToVoteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;

@RestController
@Slf4j
@RequestMapping(value = VoteRestController.pathUrl, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {

    public static final String pathUrl = "/rest/vote";
    private final VoteService service;

    @Autowired
    public VoteRestController(VoteService service) {
        this.service = service;
    }

    @PostMapping("/{restaurantId}")
    public void vote(@PathVariable(name = "restaurantId") int restaurantId) {
        ClockUtil clockUtil = ClockUtil.getInstance();
        LocalTime currentTime = LocalTime.now(clockUtil.getClock());
        if (currentTime.isAfter(DateTimeUtil.limitVoteTime)) {
            throw new ToLateToVoteException("to late to vote serverTime=" + currentTime);
        }
        log.info("user = {} Vote for restaurantId = {}", SecurityUtil.get(), restaurantId);
        service.vote(restaurantId, SecurityUtil.authUserId());
    }

}
