package elaborato_ing_sw;

import java.io.IOException;
import elaborato_ing_sw.dataManager.ManagerDaoImpl;
import elaborato_ing_sw.dataManager.UserDaoImpl;
import elaborato_ing_sw.model.Manager;
import elaborato_ing_sw.utils.ShowView;
import elaborato_ing_sw.view.LoginController;
import elaborato_ing_sw.view.ManagerDashboardController;
import elaborato_ing_sw.view.ManagerEditDialogController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainApp extends Application {
	
    private static Stage primaryStage;
    private static BorderPane rootLayout;

	private UserDaoImpl userDao = UserDaoImpl.getUserDaoImpl();
	private ManagerDaoImpl managerDao = ManagerDaoImpl.getManagerDaoImpl();

    @Override
    public void start(Stage primaryStage) {
        MainApp.primaryStage = primaryStage;
        MainApp.primaryStage.setTitle("Shopping Online");

        System.out.println("Users from file: ");
        System.out.println(userDao.getAllUsers());
        
//        User u1 = new User("test", "test", LocalDate.of(1999, 8, 30), new Credentials("test", "test"), "street", "city", 123, "456");
//        userDao.addUser(u1);
        
//        Manager m1 = new Manager("man", "ager", LocalDate.of(1999, 8, 30), "pwd", 12356, Role.ADMIN);
//        System.out.println(m1);
//        managerDao.addUser(m1);
          
        System.out.println("Managers from file: ");
        System.out.println(managerDao.getAllUsers());
        
        initRootLayout();

//        showLoginView();
        showManagerDashboard();
    }
    
    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void showLoginView() {
		FXMLLoader loader = ShowView.showView("view/Login.fxml");
		LoginController controller = loader.getController();
		controller.setMainApp(this);
    }
    
    public void showManagerDashboard() {
		FXMLLoader loader = ShowView.showView("view/ManagerDashboard.fxml");
		ManagerDashboardController controller = loader.getController();
		controller.setMainApp(this);
	}
    
    public boolean showManagerEditDialog(Manager manager) {
		try {
			// Load the fxml file and create a new stage for the popup dialog.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/ManagerEditDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Edit Manager");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			
			// Set the person into the controller.
			ManagerEditDialogController controller = loader.getController();
			controller.setDialogStage(dialogStage);
			controller.setManager(manager);

			// Show the dialog and wait until the user closes it
			dialogStage.showAndWait();

			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    public static BorderPane getRootLayout() {
    	return MainApp.rootLayout;
    }
    
	public static Stage getPrimaryStage() {
		return MainApp.primaryStage;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
