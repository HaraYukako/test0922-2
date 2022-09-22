package com.example.stock.repository;

import static com.example.common.constant.DataConstant.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.goods.exception.NoGoodsException;
import com.example.stock.domain.Stock;

public class StockRepositoryImpl implements StockRepository {
	private static final String SELECT_ONE_STOCK_ALL = "SELECT GOODS_CODE, QUANTITY FROM STOCK WHERE STATUS = ?";
	private static final String SELECT_ONE_STOCK_ACTIVE = "SELECT GOODS_CODE, QUANTITY FROM STOCK WHERE GOODS_CODE = ? AND STATUS = ?";
	private static final String SELECT_ONE_STOCK = "SELECT GOODS_CODE FROM STOCK WHERE GOODS_CODE = ?";
	private static final String INSERT = "INSERT INTO STOCK(GOODS_CODE,QUANTITY,STATUS) VALUES(?, 0,'ACTIVE')";

	//在庫テーブルに新規登録処理
	public void insertStock(Connection connection, int goods_code) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
			preparedStatement.setInt(1, goods_code);
			preparedStatement.executeUpdate();
		}
	}

	@Override
	//在庫テーブルの全件検索
	public List<Stock> findAllStock(Connection connection) throws SQLException, NoGoodsException {
		List<Stock> stockList = null;

		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_STOCK_ALL)) {
			preparedStatement.setString(1, STATUS_ACTIVE);
			stockList = getStock(preparedStatement);
		}

		if (stockList.isEmpty())
			throw new NoGoodsException();

		return stockList;
	}

	//在庫テーブルの単件検索
	public Stock findStock(Connection connection, int goods_code) throws SQLException, NoGoodsException {
		Stock stock = null;
		ResultSet rs = null; //SQL実行結果取得用変数

		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_STOCK_ACTIVE)) {
			preparedStatement.setInt(1, goods_code);
			preparedStatement.setString(2, STATUS_ACTIVE);
			rs = preparedStatement.executeQuery(); //結果取得
			if (rs.next()) {
				stock = new Stock(rs.getInt("GOODS_CODE"), rs.getInt("QUANTITY"));
			} else {
				throw new NoGoodsException();
			}
		}
		return stock;

		/*
		* SQL SELECT_ONE_GOODS
		*  ?に引数を入れて検索
		*/
		//return null;
	}

	/*public boolean isGoodsDeactive(Connection connection, int goods_code) throws SQLException {
	
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_GOODS_ALL)) {
			preparedStatement.setInt(1, goods_code);
			ResultSet rs = preparedStatement.executeQuery();
	
			if (rs.next() == false) {
				return true;
			} else {
				try (PreparedStatement preparedStatement2 = connection.prepareStatement(SELECT_ONE_GOODS)) {
					preparedStatement2.setInt(1, goods_code);
					preparedStatement2.setString(2, STATUS_DEACTIVE);
					ResultSet rs2 = preparedStatement2.executeQuery();
					if (rs2.next()) {
						return true;
					}
				}
			}
			return false;
		}
	}*/
	//在庫テーブルに入力した商品コードがあるか探す処理
	public boolean isStockSearch(Connection connection, int goods_code) throws SQLException {

		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ONE_STOCK)) {
			preparedStatement.setInt(1, goods_code);
			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				return true;
			}
			return false;
		}
	}

	private List<Stock> getStock(PreparedStatement preparedStatement) throws SQLException {
		List<Stock> stockList = new ArrayList<Stock>();

		try (ResultSet resultSet = preparedStatement.executeQuery()) {
			while (resultSet.next()) {
				Stock stock = new Stock(resultSet.getInt(1), resultSet.getInt(2));
				stockList.add(stock);
			}
		}
		return stockList;
	}

}
