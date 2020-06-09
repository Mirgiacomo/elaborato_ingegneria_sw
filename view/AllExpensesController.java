package elaborato_ing_sw.view;

import java.time.LocalDate;
import java.util.function.Predicate;

import elaborato_ing_sw.dataManager.ExpensesDaoImpl;
import elaborato_ing_sw.model.Delivery;
import elaborato_ing_sw.model.Expense;
import elaborato_ing_sw.model.Payment;
import elaborato_ing_sw.model.TimeSlot;
import elaborato_ing_sw.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AllExpensesController {
	@FXML
	TableView<Expense> expensesTable;
	@FXML
	TableColumn<Expense, LocalDate> date;
	@FXML
	TableColumn<Expense, TimeSlot> timeslot;
	@FXML
	TableColumn<Expense, Double> priceTot;
	@FXML
	TableColumn<Expense, Payment> payment;
	@FXML
	TableColumn<Expense, Delivery> delivery;
	
	private ExpensesDaoImpl expensesDao = ExpensesDaoImpl.getExpensesDaoImpl();
	ObservableList<Expense> tableExpenses;

	private Stage dialogStage;
	private User loggedUser;
	private String user;

	@FXML
	private void handleTable() {
		date.setCellValueFactory(cellData -> cellData.getValue().getDateProperty());
		timeslot.setCellValueFactory(cellData -> cellData.getValue().getTimeSlotProperty());
		priceTot.setCellValueFactory(cellData -> cellData.getValue().getPriceTotProperty());
		payment.setCellValueFactory(cellData -> cellData.getValue().getPaymentProperty());
		delivery.setCellValueFactory(cellData -> cellData.getValue().getDeliveryProperty());
		
		this.user = loggedUser.getCredentials().getUser();
		
		if (expensesDao.getItem(user) == null) {
			System.out.println("No expenses available");
		} else {
			this.tableExpenses = FXCollections.observableArrayList(expensesDao.getAllItems().filtered(new Predicate<Expense>() {
				@Override
				public boolean test(Expense expense) {
					return expense.getUser().equals(loggedUser);
				}
			}));
			
			expensesTable.setItems(this.tableExpenses);
		}
	}
	
	@FXML
	private void handleClose() {
		dialogStage.close();
	}

	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
		handleTable();
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
}
