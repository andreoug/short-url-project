package com.neueda.shorty.stats.controller;

import com.neueda.shorty.stats.model.Stats;
import com.neueda.shorty.stats.model.Url;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandreou on 16/05/2018.
 */
@RestController
@RequestMapping("/stats")
public class StatsController {

    @RequestMapping("/single")
    public Stats getStatsByName(@RequestParam(value = "name", defaultValue = "requestsFromGreetingOfStats") String name) {
        return new Stats(1, name, "10");
    }

    @RequestMapping("/all")
    public List<?> getAllLog() {
        List<Stats> statsList = new ArrayList<>();
        statsList.add(new Stats(1, "1", "10"));
        statsList.add(new Stats(1, "1", "10"));
        statsList.add(new Stats(1, "1", "10"));
        return statsList;
    }
}
