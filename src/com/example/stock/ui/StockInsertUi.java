package com.example.stock.ui;

import static com.example.common.constant.MessageConstant.*;

import java.sql.SQLException;
import java.util.Scanner;

import com.example.common.ui.BaseUi;
import com.example.goods.exception.GoodsCodeDupulicateException;
import com.example.goods.exception.NoGoodsException;
import com.example.stock.service.StockService;

public class StockInsertUi extends BaseUi {

	private final Scanner console;
	private final StockService stockService;

	public StockInsertUi(Scanner console, StockService stockService) {
		this.console = console;
		this.stockService = stockService;
	}

	void insertStock() throws SQLException, ClassNotFoundException {
		System.out.println(COMMON_CREATE_START);
		//商品コードを入力させる
		while (true) {
			int goods_code = goodsCodeInput(console);

			insertExecute(goods_code);

			if (isNextOrEndInput(console))
				break;
		}
	}

	private void insertExecute(int goods_code) throws SQLException, ClassNotFoundException {
		if (!isYesNoInput(console, COMMON_CREATE_YES_OR_NO)) {
			System.out.println(COMMON_CREATE_CANCEL);
			return;
		}
		try {
			stockService.insertStock(goods_code);
			System.out.println(COMMON_CREATE_END);
		} catch (GoodsCodeDupulicateException e) {
			System.out.println(STOCK_GOODS_CODE_DUPULICATE);
		} catch (NoGoodsException e) {
			System.out.println(GOODS_NO_DATA);
		}
	}
}
