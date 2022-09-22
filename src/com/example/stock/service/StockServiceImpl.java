package com.example.stock.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.common.transaction.TransactionManager;
import com.example.goods.exception.GoodsCodeDupulicateException;
import com.example.goods.exception.NoGoodsException;
import com.example.goods.repository.GoodsRepositoryImpl;
import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

public class StockServiceImpl implements StockService {
	private StockRepository stockRepository;

	//Connectionを作る
	//stockRepository(connection,code)を呼び出す
	public StockServiceImpl(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	//データの登録
	public void insertStock(int goods_code)
			throws ClassNotFoundException, SQLException, GoodsCodeDupulicateException, NoGoodsException {
		//データベース接続
		try (Connection connection = TransactionManager.getConnection()) {
			try {
				//入力した商品コードが商品テーブルにあるか探す処理
				new GoodsRepositoryImpl().findGoods(connection, goods_code);

				//在庫テーブルに入力した商品コードがあるか探す処理
				if (stockRepository.isStockSearch(connection, goods_code)) {
					throw new GoodsCodeDupulicateException();
				}

				//在庫テーブルに入力した商品コードを登録する処理
				stockRepository.insertStock(connection, goods_code);
				TransactionManager.commit(connection);
			} catch (Exception e) {
				TransactionManager.rollback(connection);
				throw e;
			}
		}
	}

	//在庫の全件検索
	public List<Stock> findAllStock() throws ClassNotFoundException, SQLException, NoGoodsException {
		//データベース接続
		try (Connection connection = TransactionManager.getReadOnlyConnection()) {
			//在庫テーブルの商品を取り出す処理
			List<Stock> stockList = stockRepository.findAllStock(connection);
			return stockList;
		}
	}

	//在庫の単件検索をする
	public Stock findStock(int goods_code) throws SQLException, NoGoodsException, ClassNotFoundException {
		Stock stock = null;
		//データベース接続
		try (Connection connection = TransactionManager.getConnection()) {
			//単件検索をするメソッドを呼び出す処理
			stock = stockRepository.findStock(connection, goods_code);

			//
			if (stock == null) {
				return stock;
			}
		}
		return stock;
	}
}
