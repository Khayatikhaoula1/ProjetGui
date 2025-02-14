package Controllers;

import Services.UserService;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import java.sql.SQLException;
import java.util.Map;

public class DashboardController {

    @FXML
    private Label totalUsersLabel;

    @FXML
    private PieChart roleDistributionChart;

    private final UserService userService = new UserService();

    @FXML
    public void initialize() {
        try {
            // Afficher le nombre total d'utilisateurs
            int totalUsers = userService.getTotalUsers();
            totalUsersLabel.setText("Total utilisateurs: " + totalUsers);

            // Afficher la répartition des rôles
            Map<String, Integer> roleStats = userService.getUserCountByRole();
            for (Map.Entry<String, Integer> entry : roleStats.entrySet()) {
                roleDistributionChart.getData().add(new PieChart.Data(entry.getKey(), entry.getValue()));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement des statistiques : " + e.getMessage());
        }
    }
}
