package com.ftr.WorkitemService.entity;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.*;

public class WorkitemIdGenerator implements IdentifierGenerator {

    private long counter;
   // TODO : WE need to use redis for storing the last terminal_id, so that when we restart the service then we have key-value store
   // Temperory I an using a file to store the count
    private final String FILE_NAME = "workitemId-counter.txt";

    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
        File file = new File(FILE_NAME);
        // System.out.println("File exists at: " + file.getAbsolutePath());
        if (file.exists() && !file.isDirectory()) {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                String line = br.readLine();
                counter = Long.parseLong(line);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
                writer.write(Long.toString(counter + 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "J" + counter;
    }
}
