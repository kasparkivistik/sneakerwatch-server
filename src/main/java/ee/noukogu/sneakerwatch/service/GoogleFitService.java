package ee.noukogu.sneakerwatch.service;

import ee.noukogu.sneakerwatch.model.GoogleFitData;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class GoogleFitService {

    private List<String> sports = Arrays.asList("Running", "Tennis", "Skate", "Training", "Basketball", "Football", "Hiking");

    public GoogleFitData getGoogleFitData() {
        GoogleFitData googleFitData = new GoogleFitData();
        Random random = new Random();
        googleFitData.setMainSport(sports.get(random.nextInt(sports.size())));
        googleFitData.setTrainingsThisWeek(random.nextInt(7));
        return googleFitData;
    }
}
