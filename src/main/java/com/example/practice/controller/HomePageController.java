package com.example.practice.controller;

import com.example.practice.Race;
import com.example.practice.Stop;
import com.example.practice.repository.JDBCStopRepos;
import com.example.practice.repository.JDBCRaceRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class HomePageController {
    List<String> arrl = new ArrayList<>();
    List<String> depl = new ArrayList<>();
    List<Stop> stops = new ArrayList<>();
    @Autowired
    JDBCRaceRepos jdbcRaceRepos = new JDBCRaceRepos();
    @Autowired
    JDBCStopRepos jdbcStopRepos = new JDBCStopRepos();
    @GetMapping(value = "/hp")
    public String homePageController(Model model,@Param("arrival_place") String arrival_place, @Param("departure_place")  String departure_place){

        List<Race> races = jdbcRaceRepos.fill(arrival_place, departure_place);
        model.addAttribute("races", races);
        model.addAttribute("arrival_place", arrival_place);
        model.addAttribute("departure_place", departure_place);
        return "home-page";
    }
    @GetMapping(value = "/race/{id}")
    public ModelAndView showStops( @PathVariable(name = "id") int id)
    {
        stops.clear();
        arrl.clear();
        depl.clear();
        ModelAndView mav = new ModelAndView("race-page");
        stops = jdbcStopRepos.fillTableStop(id);
        mav.addObject("stops",stops);
        for(int i = 0; i < stops.size() - 1; i ++)
        {
            arrl.add((stops.get(i).getNext_place()));

            depl.add((stops.get(i).getStop_place()));

        }
        mav.addObject("arrst",arrl );
        mav.addObject("depst", depl);

        return mav;
    }
    @RequestMapping(value = "/getbilet/")
    public ModelAndView getBilet(@RequestParam(name = "depddlist") String dep,@RequestParam( name = "arrddlist") String arr )
    {
        List<Stop> arrstoplist = new  ArrayList<>();
        List<Stop> depstoplist = new ArrayList<>();
        for (int i = 0; i < stops.size(); i++) {
            if (Objects.equals(stops.get(i).getStop_place(), dep)) {
                depstoplist.add(stops.get(i));
            }
            if (Objects.equals(stops.get(i).getStop_place(), arr)) {
                arrstoplist.add(stops.get(i));
            }
        }
        ModelAndView mav = new ModelAndView("get-bilet");

        mav.addObject("deps", dep);
        mav.addObject("arrs", arr);
        mav.addObject("deptm",depstoplist.get(0).getDep_time());
        mav.addObject("arrtm",arrstoplist.get(0).getArr_time());
        mav.addObject("place",depstoplist.get(0).getAv_places());
        mav.addObject("pr", jdbcStopRepos.Price(depstoplist.get(0).getId(),arrstoplist.get(0).getId()));
        System.out.println();
        return mav;
    }
    @GetMapping(value = "/admin/" )
    public ModelAndView adminhp(@Param("arrival_place") String arrival_place, @Param("departure_place")  String departure_place){
        List<Race> races = jdbcRaceRepos.fill(arrival_place, departure_place);
        ModelAndView mav = new ModelAndView("home-page-admin");
        mav.addObject("races", races);
        mav.addObject("arrival_place", arrival_place);
        mav.addObject("departure_place", departure_place);
        return mav;
    }
    @RequestMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("edit-form");
        Race race = jdbcRaceRepos.getbyID(id);
        mav.addObject("race",race);

        return mav;
    }
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ModelAndView update(Model model,@ModelAttribute("race") Race race) {
        jdbcRaceRepos.update(race);

        return adminhp(null,null);
    }
    @GetMapping(value = "/adminstop/{id}")
    public ModelAndView showStopsAdmin( @PathVariable(name = "id") int id)
    {
        stops.clear();

        ModelAndView mav = new ModelAndView("stop-admin");
        stops = jdbcStopRepos.fillTableStop(id);
        mav.addObject("stops",stops);

        return mav;
    }
    @RequestMapping("/stedit/{id}")
    public ModelAndView showStopEditForm(@PathVariable(name = "id") int id) {
        ModelAndView mav = new ModelAndView("stop-edit");
        Stop stop = jdbcStopRepos.getByID(id);
        mav.addObject("stop",stop);

        return mav;
    }
    @RequestMapping(value = "/stopedit", method = RequestMethod.POST)
    public ModelAndView stopedit(Model model,@ModelAttribute("stop") Stop stop) {
        jdbcStopRepos.update(stop);

        return showStopsAdmin(stop.getRace_id());
    }
    @RequestMapping("/newrace")
    public String showNewForm(Model model) {
        Race race = new Race();
        model.addAttribute("race", race);

        return "new-race";
    }
    @RequestMapping(value = "/saverace", method = RequestMethod.POST)
    public ModelAndView saveRace(@ModelAttribute("race") Race race) {
        jdbcRaceRepos.saveRace(race);

        return adminhp(null,null);
    }
    @RequestMapping("/deleterace/{id}")
    public ModelAndView deleteRace(@PathVariable(name = "id") int id) {
        jdbcRaceRepos.deleteRace(id);
        return adminhp(null,null);
    }
    @RequestMapping("/deletestop/{id}")
    public ModelAndView deleteStop(@PathVariable(name = "id") int id) {
        jdbcStopRepos.deleteStop(id);
        return adminhp(null,null);
    }
    @RequestMapping("/newstop")
    public String newStop(Model model) {
        Stop stop = new Stop();
        model.addAttribute("stop", stop);

        return "new-stop";
    }
    @RequestMapping(value = "/savestop", method = RequestMethod.POST)
    public ModelAndView saveStop(@ModelAttribute("stop") Stop stop) {
        jdbcStopRepos.saveStop(stop);

        return showStopsAdmin(stop.getRace_id());
    }
}
