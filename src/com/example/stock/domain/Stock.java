package com.example.stock.domain;

public class Stock {
	private int code;
	private int quantity;

	public Stock(int code, int quantity) {
		this.code = code;
		this.quantity = quantity;
	}

	//codeのゲッターメソッド
	public int getCode() {
		return code;
	}

	//priceのゲッターメソッド
	public int getQuantity() {
		return quantity;
	}

	@Override
	public String toString() {
		return "・在庫 　商品コード[" + code + "] 在庫数=[" + quantity + "]";
	}
}
