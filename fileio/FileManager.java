package fileio;

import entity.*;
import java.io.*;
import java.security.*;
import java.util.*;

public class FileManager {
    private static final String USER_FILE = "data/users.txt";
    private static final String TRAIN_FILE = "data/trains.txt";
    private static final String TICKET_FILE = "data/tickets.txt";

    private static String hash(String input) 
	{
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashBytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) 
		{
            return input;
        }
    }

    public static boolean authenticate(String username, String password)
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            String hashed = hash(password);
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(hashed))
				{
                    return true;
                }
            }
        } catch (IOException e) 
		{
            e.printStackTrace();
        }
        return false;
    }

    public static boolean registerUser(String username, String password) {
        if (userExists(username))
			return false;
        try (PrintWriter out = new PrintWriter(new FileWriter(USER_FILE, true))) 
		{
            out.println(username + "|" + hash(password));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static boolean userExists(String username) 
	{
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE)))
		{
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts[0].equals(username)) 
					return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Train> getAllTrains() 
	{
        List<Train> trains = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TRAIN_FILE))) 
		{
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 6) {
                    Train t = new Train(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim(), Integer.parseInt(p[4].trim()));
                    t.sellTickets(Integer.parseInt(p[5].trim())); 
                    trains.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trains;
    }

    public static void saveTrains(List<Train> trains) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRAIN_FILE))) {
            for (Train t : trains) {
                writer.println(t.getTrainNo() + "|" + t.getName() + "|" + t.getFrom() + "|" +
                        t.getTo() + "|" + t.getTotalSeats() + "|" + t.getTicketsSold());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTicket(Ticket ticket) {
        try (PrintWriter out = new PrintWriter(new FileWriter(TICKET_FILE, true))) {
            out.println(ticket.getUsername() + "|" + ticket.getTrainNo() + "|" + ticket.getQuantity());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Ticket> getTicketsByUser(String username) {
        List<Ticket> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] p = line.split("\\|");
                if (p.length >= 3 && p[0].equals(username)) {
                    result.add(new Ticket(p[0], p[1], Integer.parseInt(p[2])));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void removeTicket(String username, String trainNo, int quantity) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(TICKET_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 3 && 
                    parts[0].equals(username) && 
                    parts[1].equals(trainNo) && 
                    Integer.parseInt(parts[2]) == quantity) {
                    continue;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        try (PrintWriter writer = new PrintWriter(new FileWriter(TICKET_FILE))) {
            for (String line : lines) {
                writer.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}