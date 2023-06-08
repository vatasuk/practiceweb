package com.example.practice.repository;

import com.example.practice.Race;
import com.example.practice.Stop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JDBCStopRepos {
    @Autowired
    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    public List<Stop> fillTableStop(int id)
    {
        Object[] args = {id};
        return jdbcTemplate.query("select * from stop where race_id = ? order by arr_time", args, BeanPropertyRowMapper.newInstance(Stop.class));
    }
    public int Price(int stopid, int arrid)
    {

        Object[] args = {stopid, arrid};
        return jdbcTemplate.queryForObject("select sum(cost_pr_stop) from stop where (stop.id >=? and stop.id <= ?)", args, Integer.class);
    }
    public Stop getByID(int id)
    {
        Object[] args = {id};
        Stop stop = jdbcTemplate.queryForObject("select * from stop where id = ?", args,
                BeanPropertyRowMapper.newInstance(Stop.class));
        return stop;
    }
    public void update(Stop stop) {
        Object[] args = {stop.getStop_place(), stop.getNext_place(),stop.getArr_time(),stop.getDep_time(),stop.getCost_pr_stop(),stop.getAv_places(),stop.getId()};
        jdbcTemplate.update("UPDATE stop SET stop_place = ?,next_place = ?,\n" +
                "arr_time = ?,dep_time = ?,cost_pr_stop = ?,av_places = ? WHERE id=?",args );
    }
    public void deleteStop(int id) {

        jdbcTemplate.update("DELETE FROM stop WHERE id = ?", id);
    }
    public void saveStop(Stop stop) {
        SimpleJdbcInsert insertActor = new SimpleJdbcInsert(jdbcTemplate);
        insertActor.withTableName("stop").usingColumns("race_id", "stop_place", "arr_time","dep_time", "cost_pr_stop", "av_places", "next_place");
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(stop);
        insertActor.execute(param);
    }
}
