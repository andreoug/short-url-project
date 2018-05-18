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
    public Stats getStatsByName(@RequestParam(value = "name", defaultValue = "mean_value_of_hits_per_day") String name) {
        return new Stats(1, name, "10");
    }

    @RequestMapping("/all")
    public List<?> getAllLog() {
        List<Stats> statsList = new ArrayList<>();
        statsList.add(new Stats(1, "mean_value_of_hits_per_day", "10"));
        statsList.add(new Stats(2, "mean_value_of_hits_per_week", "50"));
        statsList.add(new Stats(3, "mean_value_of_hits_per_month", "188"));
        return statsList;
    }
}
