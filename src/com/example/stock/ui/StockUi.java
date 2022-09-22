package com.example.stock.ui;

import static com.example.common.constant.DataConstant.*;
import static com.example.common.constant.MessageConstant.*;

import java.sql.SQLException;
import java.util.Scanner;

import com.example.common.ui.BaseUi;
import com.example.goods.exception.NoGoodsException;
import com.example.stock.service.StockService;
import com.example.stock.util.StockServiceAndRepositoryFactory;

public class StockUi extends BaseUi {
	private final Scanner console = new Scanner(System.in);
	private final StockService stockService = StockServiceAndRepositoryFactory.getStockService();
	private final StockInsertUi stockInsertUi = new StockInsertUi(console, stockService);
	private final StockFindUi stockFindUi = new StockFindUi(console, stockService);

	public static void main(String[] args) {
		StockUi stockUi = new StockUi();
		stockUi.start();
	}

	public void start() {
		try {
			while (true) {
				String ans = nextOrEndInput(console, STOCK_NEXT_OR_END);
				String ans2 = null;
				//[n:続ける]
				if (ans.equals(NEXT)) {
					while (true) {
						ans2 = selectOperationOnetoThreeInput(console, STOCK_SELECT_OPERATION);
						//[1:管理業務]
						if (ans2.equals(ONE)) {
							selectOperation();
							//[2:入出荷]
						} else if (ans2.equals(TWO)) {

							//[3:入出荷履歴]
						} else if (ans2.equals(THREE)) {

							//[e:終了]
						} else if (ans2.equals(END)) {
							break;
						}
					}
					//[e:終了]
				} else if (ans.equals(END)) {
					System.out.println(COMMON_END);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//[1:管理業務]の選択処理
	private void selectOperation() throws ClassNotFoundException, SQLException, NoGoodsException {
		while (true) {
			String ans = selectOperationOnetoFourInput(console);
			//[1:登録]処理
			if (ans.equals(ONE)) {
				stockInsertUi.insertStock();
			} else if (ans.equals(TWO)) {
				stockFindUi.findAllStock();
			} else if (ans.equals(THREE)) {
				stockFindUi.findStock();
				//}else if (ans.equals(FOUR)){
				//stockUpdateUi.deleteStock();
			} else if (ans.equals(END)) {
				break;
			}
		}
	}
}
