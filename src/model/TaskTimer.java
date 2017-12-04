/**
 *  Lop dem thoi gian choi
 */
package model;

import java.util.Optional;
import java.util.TimerTask;

import controller.Controller;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;

public class TaskTimer extends TimerTask {
	Labeled labeled;
	boolean stop;
	boolean end;

	public TaskTimer(Labeled labeled) {
		this.labeled = labeled;
		this.labeled.setText("30");
	}

	@Override
	public void run() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				if (stop && !end) {
					end = true;
					stop = true;
					dialog("Player " + controller.getPlayerFlag() + " Thua");
				
				} else {
					if (!end)
						as();
				}
			}
		});
	}

	public void as() {
		String x = (Integer.parseInt(labeled.getText()) - 1) + "";
		labeled.setText(x);
		if (x.equals("0"))
			stop = true;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	public boolean isStop() {
		return stop;
	}
// ham su dung khi tro choi ket thuc
	public void dialog(String title) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Trò chơi kết thúc");
		alert.setHeaderText(title);
		alert.setContentText("Bạn có muốn chơi lại không ?");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			if (controller.getPlayer() instanceof HumanPlayer) {
				controller.view.replayHuman();
			} else {
				controller.view.replayComputer();
			}
		} else {
			// su dung khi chon khong hoac tat hop thoai
		}
	}

	Controller controller;

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
