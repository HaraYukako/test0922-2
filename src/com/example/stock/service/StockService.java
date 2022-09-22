package com.example.stock.service;

import java.sql.SQLException;
import java.util.List;

import com.example.goods.exception.GoodsCodeDupulicateException;
import com.example.goods.exception.NoGoodsException;
import com.example.stock.domain.Stock;

public interface StockService {
	void insertStock(int goods_code)
			throws SQLException, GoodsCodeDupulicateException, ClassNotFoundException, NoGoodsException;

	List<Stock> findAllStock() throws SQLException, NoGoodsException, ClassNotFoundException;

	Stock findStock(int goods_code) throws SQLException, NoGoodsException, ClassNotFoundException;

}
