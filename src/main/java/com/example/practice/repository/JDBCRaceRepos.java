package com.example.practice.repository;

import com.example.practice.Race;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public class JDBCRaceRepos {
    @Autowired
    private final JdbcTemplate jdbcTemplate = new JdbcTemplate();


    public List<Race> fill(String arrival_place, String departure_place)
    {
        if (arrival_place != null & departure_place != null)
        {
            return searchArrDep(departure_place,arrival_place);
        }
        if (arrival_place != null & departure_place == null)
        {
           return searchArr(arrival_place);
        }
        if ( departure_place != null & arrival_place == null  )
        {
           return searchDep(departure_place);
        }

        return  filltablerace();
    }

    public List<Race> searchArrDep(String departure_place, String arrival_place)
    {
        Object[] args = {departure_place, departure_place,arrival_place, arrival_place};
        return jdbcTemplate.query("select distinct race.id,departure_place,arrival_place,time_departure,time_arrival,race.time,all_cost,places \n" +
                "from race inner join stop on race.id=race_id \n"
                + "where (stop_place = ? or departure_place =?) or (next_place = ? or arrival_place = ?)", args,  BeanPropertyRowMapper.newInstance(Race.class));
    }
    public List<Race> searchArr( String arrival_place)
    {
        Object[] args = {arrival_place, arrival_place};
        return jdbcTemplate.query("select distinct race.id,departure_place,arrival_place,time_departure,time_arrival,race.time,all_cost,places \n" +
                "from race inner join stop on race.id=race_id \n"
                + " (next_place = ? or arrival_place = ?)",args,  BeanPropertyRowMapper.newInstance(Race.class));
    }
    public List<Race> searchDep(String departure_place)
    {
        Object[] args = {departure_place, departure_place};
        return jdbcTemplate.query("select distinct race.id,departure_place,arrival_place,time_departure,time_arrival,race.time,all_cost,places \n" +
                "from race inner join stop on race.id=race_id \n"
                + "where (stop_place = ? or departure_place =?)", args,  BeanPropertyRowMapper.newInstance(Race.class));
    }
    public List<Race> filltablerace(){
        return jdbcTemplate.query("select * from race", BeanPropertyRowMapper.newInstance(Race.class));
    }
    public Race getbyID(int id){
        Object[] args = {id};
        Race race = jdbcTemplate.queryForObject("select * from race where id = ?", args,
                BeanPropertyRowMapper.newInstance(Race.class));
        return race;
    }
    public void update(Race race) {
        Object[] args = {race.getDeparture_place(), race.getArrival_place(),race.getTime_departure(),race.getTime_arrival(),
        race.getTime(),race.getAll_cost(),race.getPlaces(),race.getId()};
        jdbcTemplate.update("UPDATE race SET departure_place = ?,arrival_place = ?,\n" +
                "time_departure = ?,time_arrival = ?,time = ?,all_cost = ?,places = ? WHERE id=?",args );
    }
    public void saveRace(Race race) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("race").usingColumns("departure_place", "arrival_place", "time_departure","time_arrival","time", "all_cost", "places");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(race);
        insertActor.execute(param);
    }
    public void deleteRace(int id) {

        jdbcTemplate.update("DELETE FROM race WHERE id = ?", id);
    }
}
