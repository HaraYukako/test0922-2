package com.example.stock.ui;

import static com.example.common.constant.MessageConstant.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.common.ui.BaseUi;
import com.example.goods.exception.NoGoodsException;
import com.example.stock.domain.Stock;
import com.example.stock.service.StockService;

public class StockFindUi extends BaseUi {

	//コンソールから文字を入力
	private final Scanner console;
	private final StockService stockService;

	//メソッド
	public StockFindUi(Scanner console, StockService stockService) {
		this.console = console;
		this.stockService = stockService;
	}

	void findStock() throws SQLException, ClassNotFoundException, NoGoodsException {
		//商品コードを入力させる
		while (true) {
			//int goods_code = goodsCodeInput(console);

			findExecute();

			if (isNextOrEndInput(console)) {
				break;
			}
		}
	}

	//全件検索をするメソッド
	void findAllStock() throws ClassNotFoundException, SQLException {
		System.out.println(COMMON_FIND_ALL_START);
		try {
			List<Stock> stockList = stockService.findAllStock();
			for (Stock stock : stockList) {
				System.out.println(stock);
			}
			System.out.println(COMMON_FIND_END);
		} catch (NoGoodsException e) {
			System.out.println(STOCK_NO_DATA);
		}

	}

	//単件検索をするメソッド
	void findExecute() throws ClassNotFoundException, SQLException, NoGoodsException {
		//"-- 検索表示
		System.out.println(COMMON_FIND_START);
		//"--- 商品コード表示
		//System.out.println(GOODS_CODE);
		//Stockクラスのインスタンスを生成
		Stock stock = null;

		try {

			//コンソールから商品コードを入力し、商品コードの情報をstockに入れる
			stock = stockService.findStock(goodsCodeInput(console));
			//stockの中身を表示
			System.out.println(stock);
			System.out.println(COMMON_FIND_END);
			return;

			//続ける終了を表示
			//if (isNextOrEndInput(console)) {

			//}
			//stockの中身がない時
		} catch (NoGoodsException e) {
			System.out.println(STOCK_NO_DATA);

		}
	}

}
