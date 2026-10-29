package com.aditya.railway.controller;
import com.aditya.railway.dto.TrainRequest;import com.aditya.railway.model.Train;import com.aditya.railway.service.RailwayService;
import jakarta.validation.Valid;import org.springframework.http.*;import org.springframework.web.bind.annotation.*;import java.util.*;
@RestController @RequestMapping("/api/trains") @CrossOrigin(origins="http://localhost:5173")
public class TrainController {private final RailwayService service;public TrainController(RailwayService s){service=s;}
 @GetMapping public List<Train> all(@RequestParam(required=false) String source,@RequestParam(required=false) String destination){return service.trains(source,destination);}
 @PostMapping public ResponseEntity<Train> create(@Valid @RequestBody TrainRequest r){return ResponseEntity.status(HttpStatus.CREATED).body(service.createTrain(r));}
 @PutMapping("/{id}") public ResponseEntity<Train> update(@PathVariable long id,@Valid @RequestBody TrainRequest r){Train t=service.updateTrain(id,r);if(t==null)return ResponseEntity.notFound().build();return ResponseEntity.ok(t);}
 @DeleteMapping("/{id}") public ResponseEntity<Void> delete(@PathVariable long id){return service.deleteTrain(id)?ResponseEntity.noContent().build():ResponseEntity.notFound().build();}
}
