package com.aditya.railway.repository;
import com.aditya.railway.dto.TrainRequest;
import com.aditya.railway.model.Train;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.*;
@Repository
public class TrainRepository {
 private final JdbcTemplate jdbc;
 public TrainRepository(JdbcTemplate jdbc){this.jdbc=jdbc;}
 private final RowMapper<Train> mapper=(r,n)->new Train(r.getLong("id"),r.getString("train_number"),r.getString("train_name"),r.getString("source_station"),r.getString("destination_station"),r.getTime("departure_time").toLocalTime(),r.getTime("arrival_time").toLocalTime(),r.getString("duration"),r.getBigDecimal("fare"),r.getInt("total_seats"),r.getInt("available_seats"),r.getBoolean("active"));
 public List<Train> findAll(){return jdbc.query("SELECT * FROM trains WHERE active=TRUE ORDER BY departure_time",mapper);}
 public List<Train> search(String source,String destination){return jdbc.query("SELECT * FROM trains WHERE active=TRUE AND (? IS NULL OR LOWER(source_station) LIKE LOWER(?)) AND (? IS NULL OR LOWER(destination_station) LIKE LOWER(?)) ORDER BY departure_time",mapper,source,source==null?null:"%"+source+"%",destination,destination==null?null:"%"+destination+"%");}
 public Optional<Train> findById(long id){return jdbc.query("SELECT * FROM trains WHERE id=?",mapper,id).stream().findFirst();}
 public Train create(TrainRequest x){jdbc.update("INSERT INTO trains(train_number,train_name,source_station,destination_station,departure_time,arrival_time,duration,fare,total_seats,available_seats,active) VALUES(?,?,?,?,?,?,?,?,?,?,?)",x.trainNumber(),x.trainName(),x.sourceStation(),x.destinationStation(),java.sql.Time.valueOf(x.departureTime()),java.sql.Time.valueOf(x.arrivalTime()),x.duration(),x.fare(),x.totalSeats(),x.availableSeats(),x.active());return findById(jdbc.queryForObject("SELECT id FROM trains WHERE train_number=?",Long.class,x.trainNumber())).orElseThrow();}
 public Train update(long id,TrainRequest x){int count=jdbc.update("UPDATE trains SET train_number=?,train_name=?,source_station=?,destination_station=?,departure_time=?,arrival_time=?,duration=?,fare=?,total_seats=?,available_seats=?,active=? WHERE id=?",x.trainNumber(),x.trainName(),x.sourceStation(),x.destinationStation(),java.sql.Time.valueOf(x.departureTime()),java.sql.Time.valueOf(x.arrivalTime()),x.duration(),x.fare(),x.totalSeats(),x.availableSeats(),x.active(),id);if(count==0)return null;return findById(id).orElse(null);}
 public boolean delete(long id){return jdbc.update("DELETE FROM trains WHERE id=?",id)>0;}
}
