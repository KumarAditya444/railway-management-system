package com.aditya.railway.service;
import com.aditya.railway.dto.TrainRequest;import com.aditya.railway.exception.ApiException;import com.aditya.railway.model.Train;import com.aditya.railway.repository.*;import org.junit.jupiter.api.Test;import org.springframework.jdbc.core.JdbcTemplate;
import java.math.BigDecimal;import java.time.LocalTime;import static org.junit.jupiter.api.Assertions.*;import static org.mockito.Mockito.*;
class RailwayServiceTest {
 @Test void rejectsAvailableSeatsGreaterThanCapacity(){TrainRepository tr=mock(TrainRepository.class);BookingRepository br=mock(BookingRepository.class);RailwayService s=new RailwayService(tr,br,mock(JdbcTemplate.class));TrainRequest req=new TrainRequest("12345","Test Express","Delhi","Mumbai",LocalTime.NOON,LocalTime.of(20,0),"8h",new BigDecimal("100"),10,11,true);assertThrows(ApiException.class,()->s.createTrain(req));verifyNoInteractions(tr);}
 @Test void rejectsSameSourceAndDestination(){TrainRepository tr=mock(TrainRepository.class);RailwayService s=new RailwayService(tr,mock(BookingRepository.class),mock(JdbcTemplate.class));TrainRequest req=new TrainRequest("12345","Test Express","Delhi","Delhi",LocalTime.NOON,LocalTime.of(20,0),"8h",new BigDecimal("100"),10,10,true);assertThrows(ApiException.class,()->s.createTrain(req));}
}
