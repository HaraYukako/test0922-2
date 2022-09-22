package com.example.stock.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.example.goods.exception.NoGoodsException;
import com.example.stock.domain.Stock;

public interface StockRepository {
	/*public boolean isGoodsDeactive(Connection connection, int goods_code)
	throws SQLException, GoodsDeletedException, GoodsCodeDupulicateException;
	*/
	public void insertStock(Connection connection, int goods_code) throws SQLException;

	public boolean isStockSearch(Connection connection, int goods_code) throws SQLException;

	List<Stock> findAllStock(Connection connection) throws SQLException, NoGoodsException;

	Stock findStock(Connection connection, int goods_code) throws SQLException, NoGoodsException;

}
