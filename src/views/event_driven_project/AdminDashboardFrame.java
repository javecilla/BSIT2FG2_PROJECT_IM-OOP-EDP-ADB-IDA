package views.event_driven_project;

import config.MSSQLConnection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.chart.title.TextTitle;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminDashboardFrame extends JFrame implements ActionListener {
    private EventController controller;
    private Connection connection;
    
    // Icons
    private ImageIcon dashboardIcon = new ImageIcon(getClass().getResource("/views/Images/plain.png"));
    
    // Labels
    private JLabel background = new JLabel(dashboardIcon);
    private JLabel dateTimeLabel = new JLabel();
    private JLabel userCountLabel = new JLabel("0");
    private JLabel revenueLabel = new JLabel("₱0.00");
    private JLabel salesCountLabel = new JLabel("0");
    
    // Panels for charts
    private JPanel chartPanel1;
    private JPanel chartPanel2;
    private JPanel chartPanel3;
    
    // Sidebar navigation buttons
    private JButton dashboardButton = new JButton("Dashboard");
    private JButton manageStocksButton = new JButton("Manage Stocks");
    private JButton manageCouriersButton = new JButton("Manage Couriers");
    private JButton logoutButton = new JButton("Logout");
    
    // Timer for date time update
    private Timer timer;
    
    public AdminDashboardFrame(EventController controller) {
        this.controller = controller;
        dashboardFrameConfig();
        loadDashboardData();
        
        // Start timer to update date and time
        timer = new Timer(1000, e -> updateDateTime());
        timer.start();
    }
    
    public void dashboardFrameConfig() {
        background.setLayout(null);
        this.setContentPane(background);
        
        setTitle("Admin Dashboard");
        
        // Create sidebar panel
        JPanel sidebarPanel = createSidebarPanel();
        background.add(sidebarPanel);
        
        // Adjust content to make room for sidebar
        int sidebarWidth = 220;
        int contentStartX = sidebarWidth + 10;
        int contentWidth = 930;
        
        // Set up header panel with date time and user info
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(null);
        headerPanel.setBounds(contentStartX, 20, contentWidth, 70);
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        // Date time label
        dateTimeLabel.setFont(new Font("Poppins", Font.BOLD, 16));
        dateTimeLabel.setForeground(new Color(95, 71, 214));
        dateTimeLabel.setBounds(20, 15, 300, 30);
        headerPanel.add(dateTimeLabel);
        
        // Admin label
        JLabel adminLabel = new JLabel("Admin Dashboard");
        adminLabel.setFont(new Font("Poppins", Font.BOLD, 24));
        adminLabel.setForeground(new Color(95, 71, 214));
        adminLabel.setBounds(headerPanel.getWidth() - 300, 15, 250, 30);
        headerPanel.add(adminLabel);
        
        background.add(headerPanel);
        
        // Set up quick stats panel
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(null);
        statsPanel.setBounds(contentStartX, 110, contentWidth, 100);
        statsPanel.setBackground(new Color(255, 255, 255));
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        // Users count stat
        JPanel userStat = createStatPanel("Total Users", userCountLabel, 0);
        userStat.setBounds(20, 15, 280, 70);
        statsPanel.add(userStat);
        
        // Revenue stat
        JPanel revenueStat = createStatPanel("Total Revenue", revenueLabel, 1);
        revenueStat.setBounds(320, 15, 280, 70);
        statsPanel.add(revenueStat);
        
        // Sales count stat
        JPanel salesStat = createStatPanel("Total Sales", salesCountLabel, 2);
        salesStat.setBounds(620, 15, 280, 70);
        statsPanel.add(salesStat);
        
        background.add(statsPanel);
        
        // Set up charts
        // Chart 1 - Monthly Sales Count
        chartPanel1 = new JPanel();
        chartPanel1.setLayout(new BorderLayout());
        chartPanel1.setBounds(contentStartX, 230, 450, 300);
        chartPanel1.setBackground(new Color(255, 255, 255));
        chartPanel1.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        // Chart 2 - Monthly Revenue (Area Chart)
        chartPanel2 = new JPanel();
        chartPanel2.setLayout(new BorderLayout());
        chartPanel2.setBounds(contentStartX + 470, 230, 450, 300);
        chartPanel2.setBackground(new Color(255, 255, 255));
        chartPanel2.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        // Chart 3 - Courier Distribution
        chartPanel3 = new JPanel();
        chartPanel3.setLayout(new BorderLayout());
        chartPanel3.setBounds(contentStartX, 550, contentWidth, 250);
        chartPanel3.setBackground(new Color(255, 255, 255));
        chartPanel3.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        background.add(chartPanel1);
        background.add(chartPanel2);
        background.add(chartPanel3);
        
        // Frame configuration
        int frameWidth = dashboardIcon != null ? dashboardIcon.getIconWidth() : 1200;
        int frameHeight = dashboardIcon != null ? dashboardIcon.getIconHeight() : 900;
        this.setSize(frameWidth, frameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // Set window close event
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                timer.stop(); // Stop the timer when window is closing
            }
        });
    }
    
    private JPanel createSidebarPanel() {
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(null);
        sidebarPanel.setBounds(10, 20, 200, 780);
        sidebarPanel.setBackground(new Color(95, 71, 214));
        sidebarPanel.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(95, 71, 214), 2, true),
            new EmptyBorder(10, 5, 10, 5)
        ));
        
        // Add logo or admin title at top
        JLabel adminTitle = new JLabel("Administrator");
        adminTitle.setFont(new Font("Poppins", Font.BOLD, 20));
        adminTitle.setForeground(Color.WHITE);
        adminTitle.setHorizontalAlignment(SwingConstants.CENTER);
        adminTitle.setBounds(10, 20, 180, 40);
        sidebarPanel.add(adminTitle);
        
        // Add separator
        JSeparator separator = new JSeparator();
        separator.setBounds(15, 70, 170, 2);
        separator.setForeground(new Color(255, 255, 255));
        separator.setBackground(new Color(255, 255, 255));
        sidebarPanel.add(separator);
        
        // Configure navigation buttons
        int buttonY = 100;
        int buttonHeight = 50;
        int buttonSpacing = 20;
        
        // Configure dashboard button as currently selected
        dashboardButton.setBounds(15, buttonY, 170, buttonHeight);
        styleNavButton(dashboardButton, true);
        dashboardButton.addActionListener(this);
        sidebarPanel.add(dashboardButton);
        
        buttonY += buttonHeight + buttonSpacing;
        
        manageStocksButton.setBounds(15, buttonY, 170, buttonHeight);
        styleNavButton(manageStocksButton, false);
        manageStocksButton.addActionListener(this);
        sidebarPanel.add(manageStocksButton);
        
        buttonY += buttonHeight + buttonSpacing;
        
        manageCouriersButton.setBounds(15, buttonY, 170, buttonHeight);
        styleNavButton(manageCouriersButton, false);
        manageCouriersButton.addActionListener(this);
        sidebarPanel.add(manageCouriersButton);
        
        // Add logout button at bottom
        logoutButton.setBounds(15, sidebarPanel.getHeight() - 70, 170, buttonHeight);
        styleNavButton(logoutButton, false);
        logoutButton.setBackground(new Color(220, 20, 60));
        logoutButton.addActionListener(this);
        sidebarPanel.add(logoutButton);
        
        return sidebarPanel;
    }
    
    private void styleNavButton(JButton button, boolean isSelected) {
        button.setFont(new Font("Poppins", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        
        if (isSelected) {
            button.setBackground(Color.WHITE);
            button.setForeground(new Color(95, 71, 214));
        } else {
            button.setBackground(new Color(95, 71, 214));
        }
        
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!isSelected && e.getSource() == button) {
                    button.setBackground(Color.WHITE);
                    button.setForeground(new Color(95, 71, 214));
                }
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                if (!isSelected && e.getSource() == button) {
                    if (button == logoutButton) {
                        button.setBackground(new Color(220, 20, 60));
                        button.setForeground(Color.white);
                    } else {
                        button.setBackground(new Color(95, 71, 214));
                        button.setForeground(Color.white);
                    }
                }
            }
        });
    }
    
    private JPanel createStatPanel(String title, JLabel valueLabel, int colorIndex) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(250, 250, 255));
        panel.setBorder(new LineBorder(getColorByIndex(colorIndex), 2, true));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Poppins", Font.BOLD, 14));
        titleLabel.setForeground(getColorByIndex(colorIndex));
        titleLabel.setBounds(15, 5, 150, 20);
        panel.add(titleLabel);
        
        valueLabel.setFont(new Font("Poppins", Font.BOLD, 22));
        valueLabel.setForeground(getColorByIndex(colorIndex));
        valueLabel.setBounds(15, 30, 250, 30);
        panel.add(valueLabel);
        
        return panel;
    }
    
    private Color getColorByIndex(int index) {
        switch (index) {
            case 0: return new Color(95, 71, 214); // Purple
            case 1: return new Color(46, 139, 87); // Green
            case 2: return new Color(220, 20, 60); // Red
            default: return new Color(95, 71, 214);
        }
    }
    
    private void updateDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
        String dateTime = dateFormat.format(Calendar.getInstance().getTime());
        dateTimeLabel.setText(dateTime);
    }
    
    private void loadDashboardData() {
        try {
            connection = MSSQLConnection.getConnection();
            
            // Load total users count
            PreparedStatement userStmt = connection.prepareStatement("SELECT * FROM TOTAL_USER");
            ResultSet userRs = userStmt.executeQuery();
            if (userRs.next()) {
                userCountLabel.setText(userRs.getString("Number of users"));
            }
            userRs.close();
            userStmt.close();
            
            // Load total revenue (sum of all months)
            PreparedStatement revenueStmt = connection.prepareStatement(
                "SELECT SUM([Total Revenue]) AS TotalRevenue FROM TOTAL_REVENUE_PER_MONTH");
            ResultSet revenueRs = revenueStmt.executeQuery();
            if (revenueRs.next()) {
                double revenue = revenueRs.getDouble("TotalRevenue");
                revenueLabel.setText(String.format("₱%.2f", revenue));
            }
            revenueRs.close();
            revenueStmt.close();
            
            // Load total sales count (sum of all months)
            PreparedStatement salesStmt = connection.prepareStatement(
                "SELECT SUM([Total Number of Sale]) AS TotalSales FROM TOTAL_SALE_PER_MONTH");
            ResultSet salesRs = salesStmt.executeQuery();
            if (salesRs.next()) {
                salesCountLabel.setText(salesRs.getString("TotalSales"));
            }
            salesRs.close();
            salesStmt.close();
            
            // Create charts
            createMonthlySalesChart();
            createMonthlyRevenueAreaChart(); // Changed to Area Chart
            createCourierDistributionChart();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading dashboard data: " + e.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createMonthlySalesChart() {
        try {
            // Create dataset for monthly sales
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Query for monthly sales data
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT TOP 6 [Year], [Month], [Total Number of Sale] FROM TOTAL_SALE_PER_MONTH ORDER BY [Year] DESC, [Month] DESC");
            ResultSet rs = stmt.executeQuery();
            
            // Populate dataset (reversing the order to show chronologically)
            HashMap<String, Integer> monthData = new HashMap<>();
            while (rs.next()) {
                int year = rs.getInt("Year");
                int month = rs.getInt("Month");
                int totalSales = rs.getInt("Total Number of Sale");
                
                // Create month-year label
                String monthName = getMonthName(month);
                String period = monthName + " " + year;
                
                // Store in map to later add in reverse order
                monthData.put(period, totalSales);
            }
            
            // Add data to dataset in reverse order
            Object[] periods = monthData.keySet().toArray();
            for (int i = periods.length - 1; i >= 0; i--) {
                String period = (String) periods[i];
                dataset.addValue(monthData.get(period), "Sales", period);
            }
            
            rs.close();
            stmt.close();
            
            // Create chart
            JFreeChart chart = ChartFactory.createBarChart(
                "Monthly Sales Count",  // chart title
                "Month",                // domain axis label
                "Number of Sales",      // range axis label
                dataset,                // data
                PlotOrientation.VERTICAL,
                false,                  // include legend
                true,                   // tooltips
                false                   // URLs
            );
            
            // Customize chart
            chart.setBackgroundPaint(new Color(250, 250, 255));
            chart.getTitle().setFont(new Font("Poppins", Font.BOLD, 18));
            chart.getTitle().setPaint(new Color(95, 71, 214));
            
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(new Color(220, 220, 220));
            plot.setRangeGridlinePaint(new Color(220, 220, 220));
            
            BarRenderer renderer = (BarRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(95, 71, 214));
            renderer.setDrawBarOutline(false);
            renderer.setShadowVisible(false);
            
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLabelFont(new Font("Poppins", Font.BOLD, 8));
            domainAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 8));
            
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setLabelFont(new Font("Poppins", Font.BOLD, 14));
            rangeAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));
            
            // Add chart to panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(430, 280));
            chartPanel1.removeAll();
            chartPanel1.add(chartPanel, BorderLayout.CENTER);
            chartPanel1.revalidate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error creating sales chart: " + e.getMessage(),
                "Chart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createMonthlyRevenueAreaChart() {
        try {
            // Create dataset for monthly revenue
            DefaultCategoryDataset dataset = new DefaultCategoryDataset();
            
            // Query for monthly revenue data
            PreparedStatement stmt = connection.prepareStatement(
                "SELECT TOP 6 [Year], [Month], [Total Revenue] FROM TOTAL_REVENUE_PER_MONTH ORDER BY [Year] DESC, [Month] DESC");
            ResultSet rs = stmt.executeQuery();
            
            // Populate dataset (reversing the order to show chronologically)
            HashMap<String, Double> monthData = new HashMap<>();
            while (rs.next()) {
                int year = rs.getInt("Year");
                int month = rs.getInt("Month");
                double totalRevenue = rs.getDouble("Total Revenue");
                
                // Create month-year label
                String monthName = getMonthName(month);
                String period = monthName + " " + year;
                
                // Store in map to later add in reverse order
                monthData.put(period, totalRevenue);
            }
            
            // Add data to dataset in reverse order
            Object[] periods = monthData.keySet().toArray();
            for (int i = periods.length - 1; i >= 0; i--) {
                String period = (String) periods[i];
                dataset.addValue(monthData.get(period), "Revenue", period);
            }
            
            rs.close();
            stmt.close();
            
            // Create chart - using area chart for better visibility
            JFreeChart chart = ChartFactory.createAreaChart(
                "Monthly Revenue",    // chart title
                "Month",              // domain axis label
                "Revenue (₱)",        // range axis label
                dataset,              // data
                PlotOrientation.VERTICAL,
                false,                // include legend
                true,                 // tooltips
                false                 // URLs
            );
            
            // Customize chart
            chart.setBackgroundPaint(new Color(250, 250, 255));
            chart.getTitle().setFont(new Font("Poppins", Font.BOLD, 18));
            chart.getTitle().setPaint(new Color(46, 139, 87));
            
            CategoryPlot plot = chart.getCategoryPlot();
            plot.setBackgroundPaint(Color.white);
            plot.setDomainGridlinePaint(new Color(220, 220, 220));
            plot.setRangeGridlinePaint(new Color(220, 220, 220));
            
            // Customize area renderer
            AreaRenderer renderer = (AreaRenderer) plot.getRenderer();
            renderer.setSeriesPaint(0, new Color(46, 139, 87, 180));
            renderer.setSeriesOutlinePaint(0, new Color(46, 139, 87));
            renderer.setSeriesOutlineStroke(0, new BasicStroke(2.0f));
            
            CategoryAxis domainAxis = plot.getDomainAxis();
            domainAxis.setLabelFont(new Font("Poppins", Font.BOLD, 8));
            domainAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 8));
            
            NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
            rangeAxis.setLabelFont(new Font("Poppins", Font.BOLD, 14));
            rangeAxis.setTickLabelFont(new Font("Poppins", Font.PLAIN, 12));
            
            // Add chart to panel
            ChartPanel chartPanel = new ChartPanel(chart);
            chartPanel.setPreferredSize(new Dimension(430, 280));
            chartPanel2.removeAll();
            chartPanel2.add(chartPanel, BorderLayout.CENTER);
            chartPanel2.revalidate();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error creating revenue chart: " + e.getMessage(),
                "Chart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void createCourierDistributionChart() {
        JPanel combinedPanel = new JPanel(new GridLayout(1, 2));
        
        try {
            // Create company distribution pie chart
            DefaultPieDataset companyDataset = new DefaultPieDataset();
            
            // Query for courier company data
            PreparedStatement companyStmt = connection.prepareStatement(
                "SELECT * FROM COURIER_PER_COMPANY");
            ResultSet companyRs = companyStmt.executeQuery();
            
            while (companyRs.next()) {
                String company = companyRs.getString("Company");
                int count = companyRs.getInt("Number of Riders");
                companyDataset.setValue(company, count);
            }
            
            companyRs.close();
            companyStmt.close();
            
            JFreeChart companyChart = ChartFactory.createPieChart(
                "Couriers by Company",  // chart title
                companyDataset,         // data
                true,                   // include legend
                true,                   // tooltips
                false                   // URLs
            );
            
            // Customize company chart
            companyChart.setBackgroundPaint(new Color(250, 250, 255));
            companyChart.getTitle().setFont(new Font("Poppins", Font.BOLD, 18));
            companyChart.getTitle().setPaint(new Color(95, 71, 214));
            
            PiePlot companyPlot = (PiePlot) companyChart.getPlot();
            companyPlot.setBackgroundPaint(Color.white);
            companyPlot.setOutlineVisible(false);
            companyPlot.setLabelFont(new Font("Poppins", Font.PLAIN, 12));
            companyPlot.setLabelBackgroundPaint(new Color(255, 255, 255, 180));
            
            // Create status distribution pie chart
            DefaultPieDataset statusDataset = new DefaultPieDataset();
            
            // Query for courier status data
            PreparedStatement statusStmt = connection.prepareStatement(
                "SELECT * FROM COURIER_WITH_STATUS");
            ResultSet statusRs = statusStmt.executeQuery();
            
            while (statusRs.next()) {
                String status = statusRs.getString("Status");
                int count = statusRs.getInt("Number of Riders");
                statusDataset.setValue(status, count);
            }
            
            statusRs.close();
            statusStmt.close();
            
            JFreeChart statusChart = ChartFactory.createPieChart(
                "Couriers by Status",   // chart title
                statusDataset,          // data
                true,                   // include legend
                true,                   // tooltips
                false                   // URLs
            );
            
            // Customize status chart
            statusChart.setBackgroundPaint(new Color(250, 250, 255));
            statusChart.getTitle().setFont(new Font("Poppins", Font.BOLD, 18));
            statusChart.getTitle().setPaint(new Color(220, 20, 60));
            
            PiePlot statusPlot = (PiePlot) statusChart.getPlot();
            statusPlot.setBackgroundPaint(Color.white);
            statusPlot.setOutlineVisible(false);
            statusPlot.setLabelFont(new Font("Poppins", Font.PLAIN, 12));
            statusPlot.setLabelBackgroundPaint(new Color(255, 255, 255, 180));
            
            // Add both charts to panel
            ChartPanel companyChartPanel = new ChartPanel(companyChart);
            ChartPanel statusChartPanel = new ChartPanel(statusChart);
            
            combinedPanel.add(companyChartPanel);
            combinedPanel.add(statusChartPanel);
            
            chartPanel3.removeAll();
            chartPanel3.add(combinedPanel, BorderLayout.CENTER);
            chartPanel3.revalidate();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error creating courier charts: " + e.getMessage(),
                "Chart Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String getMonthName(int month) {
        String[] monthNames = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                              "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        return monthNames[month - 1];
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            // Already on dashboard, do nothing or refresh data
            loadDashboardData();
        } else if (e.getSource() == manageStocksButton) {
            controller.showManageStockFrame(this);
        } else if (e.getSource() == manageCouriersButton) {
            controller.showManageCourierFrame(this);
        } else if (e.getSource() == logoutButton) {
            timer.stop(); // Stop the timer when logging out
            controller.showHomeFrame(this);
        }
    }
}