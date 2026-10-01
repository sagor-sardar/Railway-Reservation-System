package gui;

import entity.*;
import fileio.FileManager;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ReservationSystemGUI extends JFrame {
    private String username;
    private JTable trainTable;
    private DefaultTableModel tableModel;
    private List<Train> trains;
    private JTabbedPane tabbedPane;
    private JPanel mainPanel;

    public ReservationSystemGUI(String username) {
        this.username = username;
        setTitle("Railway Reservation System");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setIconImage(new ImageIcon("images/logo.png").getImage());

        
        getContentPane().setBackground(new Color(245, 245, 245));

      
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        add(mainPanel);

      
        createNavigationBar();
        
      
        createTabbedPane();

        trains = FileManager.getAllTrains();
        updateTrainTable();

        setVisible(true);
    }

    private void createNavigationBar() {
        JPanel navBar = new JPanel(new BorderLayout());
        navBar.setBackground(new Color(51, 51, 51));
        navBar.setPreferredSize(new Dimension(getWidth(), 60));
        navBar.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

    
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(51, 51, 51));
        
        JLabel logoLabel = new JLabel("🚂");
        logoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        logoLabel.setForeground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Railway Reservation");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        leftPanel.add(logoLabel);
        leftPanel.add(Box.createHorizontalStrut(10));
        leftPanel.add(titleLabel);

        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(51, 51, 51));
        
        JLabel userLabel = new JLabel("👤 " + username);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(Color.WHITE);
        
        JButton logoutBtn = createNavButton("Logout");
        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginGUI();
        });

        rightPanel.add(userLabel);
        rightPanel.add(Box.createHorizontalStrut(20));
        rightPanel.add(logoutBtn);

        navBar.add(leftPanel, BorderLayout.WEST);
        navBar.add(rightPanel, BorderLayout.EAST);
        mainPanel.add(navBar, BorderLayout.NORTH);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabbedPane.setBackground(new Color(245, 245, 245));
        
        
        JPanel trainsPanel = new JPanel(new BorderLayout(10, 10));
        trainsPanel.setBackground(new Color(245, 245, 245));
        trainsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(245, 245, 245));
        
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        JButton searchBtn = createNavButton("🔍 Search");
        searchBtn.setPreferredSize(new Dimension(100, 35));
        
        searchPanel.add(searchField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchBtn);

        
        String[] cols = {"Train No", "Name", "From", "To", "Total Seats", "Tickets Sold", "Available"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        trainTable = new JTable(tableModel);
        
        
        trainTable.setRowHeight(35);
        trainTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        trainTable.setGridColor(new Color(200, 200, 200));
        trainTable.setSelectionBackground(new Color(52, 152, 219));
        trainTable.setSelectionForeground(Color.WHITE);
        
   
        JTableHeader header = trainTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        JScrollPane scrollPane = new JScrollPane(trainTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 0, 10, 0),
            BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));

   
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        actionPanel.setBackground(new Color(245, 245, 245));

        JButton bookBtn = createActionButton("Book Ticket", new Color(46, 204, 113));
        JButton cancelBtn = createActionButton("Cancel Ticket", new Color(231, 76, 60));
        JButton refreshBtn = createActionButton("Refresh", new Color(52, 152, 219));
        JButton payBtn = createActionButton("Make Payment", new Color(155, 89, 182));

        actionPanel.add(bookBtn);
        actionPanel.add(cancelBtn);
        actionPanel.add(payBtn);
        actionPanel.add(refreshBtn);

   
        trainsPanel.add(searchPanel, BorderLayout.NORTH);
        trainsPanel.add(scrollPane, BorderLayout.CENTER);
        trainsPanel.add(actionPanel, BorderLayout.SOUTH);

        tabbedPane.addTab("🚂 Trains", new ImageIcon(), trainsPanel);
        tabbedPane.addTab("🎫 My Tickets", new ImageIcon(), createTicketsPanel());
        
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

     
        bookBtn.addActionListener(e -> bookTicket());
        cancelBtn.addActionListener(e -> cancelTicket());
        payBtn.addActionListener(e -> makePayment());
        refreshBtn.addActionListener(e -> updateTrainTable());
        searchBtn.addActionListener(e -> searchTrains(searchField.getText()));
    }

    private JPanel createTicketsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

      
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(245, 245, 245));
        
        JLabel titleLabel = new JLabel("My Tickets", JLabel.LEFT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(52, 73, 94));
        titlePanel.add(titleLabel);

        
        String[] cols = {"Train No", "Train Name", "From", "To", "Quantity", "Status"};
        DefaultTableModel ticketModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable ticketTable = new JTable(ticketModel);
        
        
        ticketTable.setRowHeight(35);
        ticketTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        ticketTable.setGridColor(new Color(200, 200, 200));
        ticketTable.setSelectionBackground(new Color(52, 152, 219));
        ticketTable.setSelectionForeground(Color.WHITE);
        
       
        JTableHeader header = ticketTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(52, 73, 94));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 40));
        
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(10, 0, 10, 0),
            BorderFactory.createLineBorder(new Color(200, 200, 200))
        ));

       
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        actionPanel.setBackground(new Color(245, 245, 245));

        JButton refreshBtn = createActionButton("Refresh", new Color(52, 152, 219));
        JButton cancelBtn = createActionButton("Cancel Ticket", new Color(231, 76, 60));
        JButton payBtn = createActionButton("Make Payment", new Color(155, 89, 182));

        actionPanel.add(cancelBtn);
        actionPanel.add(payBtn);
        actionPanel.add(refreshBtn);

       
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(actionPanel, BorderLayout.SOUTH);

       
        refreshBtn.addActionListener(e -> updateTicketTable(ticketModel));
        cancelBtn.addActionListener(e -> cancelTicket());
        payBtn.addActionListener(e -> makePayment());

       
        updateTicketTable(ticketModel);

        return panel;
    }

    private void updateTicketTable(DefaultTableModel model) {
        model.setRowCount(0);
        List<Ticket> tickets = FileManager.getTicketsByUser(username);
        List<Train> allTrains = FileManager.getAllTrains();

        for (Ticket ticket : tickets) {
            Train train = allTrains.stream()
                .filter(t -> t.getTrainNo().equals(ticket.getTrainNo()))
                .findFirst()
                .orElse(null);

            if (train != null) {
                model.addRow(new Object[]{
                    train.getTrainNo(),
                    train.getName(),
                    train.getFrom(),
                    train.getTo(),
                    ticket.getQuantity(),
                    "Confirmed"
                });
            }
        }
    }

    private JButton createNavButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(new Color(70, 70, 70));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(90, 90, 90));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(70, 70, 70));
            }
        });
        
        return button;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }

    private void searchTrains(String query) {
        tableModel.setRowCount(0);
        for (Train t : trains) {
            if (t.getName().toLowerCase().contains(query.toLowerCase()) ||
                t.getTrainNo().toLowerCase().contains(query.toLowerCase()) ||
                t.getFrom().toLowerCase().contains(query.toLowerCase()) ||
                t.getTo().toLowerCase().contains(query.toLowerCase())) {
                
                int available = t.getTotalSeats() - t.getTicketsSold();
                tableModel.addRow(new Object[]{t.getTrainNo(), t.getName(), t.getFrom(), t.getTo(),
                        t.getTotalSeats(), t.getTicketsSold(), available});
            }
        }
    }

    private void updateTrainTable() {
        tableModel.setRowCount(0);
        trains = FileManager.getAllTrains();
        for (Train t : trains) {
            int available = t.getTotalSeats() - t.getTicketsSold();
            tableModel.addRow(new Object[]{t.getTrainNo(), t.getName(), t.getFrom(), t.getTo(),
                    t.getTotalSeats(), t.getTicketsSold(), available});
        }
    }

    private void bookTicket() {
        int row = trainTable.getSelectedRow();
        if (row == -1) {
            showStyledMessage("Please select a train first.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String trainNo = (String) tableModel.getValueAt(row, 0);
        Train selectedTrain = trains.stream().filter(t -> t.getTrainNo().equals(trainNo)).findFirst().orElse(null);

        if (selectedTrain == null) return;

        String qtyStr = JOptionPane.showInputDialog(this, "Enter number of tickets to book:");
        if (qtyStr == null) return;
        int qty;
        try {
            qty = Integer.parseInt(qtyStr);
        } catch (NumberFormatException e) {
            showStyledMessage("Invalid number!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int available = selectedTrain.getTotalSeats() - selectedTrain.getTicketsSold();
        if (qty <= 0 || qty > available) {
            showStyledMessage("Invalid quantity! Only " + available + " available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        selectedTrain.sellTickets(qty);
        FileManager.saveTrains(trains);
        FileManager.addTicket(new Ticket(username, trainNo, qty));
        showStyledMessage("Ticket booked successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        updateTrainTable();
    }

    private void cancelTicket() {
        List<Ticket> myTickets = FileManager.getTicketsByUser(username);
        if (myTickets.isEmpty()) {
            showStyledMessage("You have no tickets to cancel.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = myTickets.stream()
                .map(t -> "Train: " + t.getTrainNo() + " | Qty: " + t.getQuantity())
                .toArray(String[]::new);

        String selected = (String) JOptionPane.showInputDialog(
                this, "Select a ticket to cancel:", "Cancel Ticket",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selected == null) return;

        int index = java.util.Arrays.asList(options).indexOf(selected);
        Ticket ticket = myTickets.get(index);
        Train targetTrain = trains.stream().filter(t -> t.getTrainNo().equals(ticket.getTrainNo())).findFirst().orElse(null);

        if (targetTrain != null) {
            targetTrain.refundTickets(ticket.getQuantity());
            FileManager.saveTrains(trains);
            FileManager.removeTicket(username, ticket.getTrainNo(), ticket.getQuantity());
            showStyledMessage("Cancelled " + ticket.getQuantity() + " tickets.", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            
            updateTrainTable();
            updateTicketTable((DefaultTableModel) ((JTable) ((JScrollPane) ((JPanel) tabbedPane.getComponentAt(1)).getComponent(1)).getViewport().getView()).getModel());
        } else {
            showStyledMessage("Train not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makePayment() {
        showStyledMessage("Payment simulation: All bookings marked paid.", "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showStyledMessage(String message, String title, int messageType) {
        UIManager.put("OptionPane.background", new Color(245, 245, 245));
        UIManager.put("Panel.background", new Color(245, 245, 245));
        UIManager.put("OptionPane.messageFont", new Font("Segoe UI", Font.PLAIN, 14));
        UIManager.put("OptionPane.buttonFont", new Font("Segoe UI", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}