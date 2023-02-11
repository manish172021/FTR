package com.ftr.WorkitemService.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CountryValidator implements ConstraintValidator<ValidCountry, String> {

    private static final String CSV_FILE_NAME = "countries.csv";
    private InputStream csvFileResourceAsStream = getClass().getClassLoader().getResourceAsStream(CSV_FILE_NAME);
    private Set<String> countrySet;

    @Override
    public void initialize(ValidCountry constraintAnnotation) {

        Optional<InputStream> inputStream = Optional.ofNullable(csvFileResourceAsStream);

        if (!inputStream.isPresent()) {
            throw new IllegalStateException("File " + CSV_FILE_NAME + " not found in classpath");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream.get()))) {
            countrySet = reader.lines()
                    .map(line -> line.split(",")[0]
                            .replace("\"",""))
                    .collect(Collectors.toSet());
        } catch (IOException ex) {
            throw new IllegalStateException("Error reading " + CSV_FILE_NAME + " from classpath", ex);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // because we already added null check in model email
        if(value == null) {
            return true;
        }
        return countrySet.contains(value);
    }
}
