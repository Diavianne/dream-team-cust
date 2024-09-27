package co.simplon.dreamteamcust.dtbusiness.services;
import co.simplon.dreamteamcust.dtbusiness.entities.Developer;
import co.simplon.dreamteamcust.dtbusiness.repositories.DeveloperRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    private static final String CSV_EXTENSION = ".csv";
//    private static final int REQUIRED_FIELDS_COUNT = 4;
//    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");

    public void saveDevelopersFromCsv(MultipartFile file) throws Exception {
//        if (file.isEmpty()) {
//            throw new Exception("File is empty");
//        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            throw new Exception("File size exceeds the limit of 5MB");
        }

        // Validate CSV format
        if (!file.getOriginalFilename().endsWith(CSV_EXTENSION)) {
            throw new Exception("Invalid file format. Please upload a CSV file.");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CSVReader csvReader = new CSVReader(reader);
            List<String[]> records = csvReader.readAll();
            List<Developer> developers = new ArrayList<>();

//            for (String[] fields : records) {
//                validateCsvFields(fields);
//                developers.add(mapToDeveloper(fields));
//            }

            developerRepository.saveAll(developers);
        } catch (CsvException e) {
            throw new Exception("Error reading CSV file: " + e.getMessage());
        }
    }

//    private void validateCsvFields(String[] fields) throws Exception {
//        if (fields.length != REQUIRED_FIELDS_COUNT) {
//            throw new Exception("Invalid number of fields in line: " + String.join(", ", fields));
//        }


//        String email = fields[3]; // Assuming email is the fourth field
//        if (!EMAIL_PATTERN.matcher(email).matches()) {
//            throw new Exception("Invalid email format: " + email);
//        }


    }

    private Developer mapToDeveloper(String[] fields) {
        Developer developer = new Developer();
        developer.setFirstName(fields[0]);
        developer.setLastName(fields[1]);
        developer.setInternalNumber(fields[2]);
        developer.setEmail(fields[3]);
        return developer;
    }
}

