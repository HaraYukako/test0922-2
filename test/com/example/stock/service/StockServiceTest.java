package com.example.stock.service;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.assertion.DiffCollectingFailureHandler;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.example.goods.exception.GoodsCodeDupulicateException;
import com.example.goods.exception.NoGoodsException;
import com.example.stock.util.StockServiceAndRepositoryFactory;

public class StockServiceTest {

	//ファイルのpackageを定数化
	private static final String TEST_DATA_DIRECTORY = "./test/data/stock/";

	//テスト用のデータベースを呼び出す変数
	private IDatabaseTester databaseTester;
	//
	private StockService stockService = StockServiceAndRepositoryFactory.getStockService();

	//StockServiceクラスのテストを行う。
	public StockServiceTest() throws Exception {
		databaseTester =
				// MySQLの場合
				new JdbcDatabaseTester("com.mysql.jdbc.Driver",
						"jdbc:mysql://localhost:3306/warehouse?useSSL=false", "root", "root");
		// HSQLDBの場合
		//				new JdbcDatabaseTester("org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost", "sa", "");
	}

	@Before
	//テスト実行前の初期化
	//初期化用データを読み込んで、RDBのデータをクリアしてから、データを登録。
	public void before() throws Exception {
		IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(TEST_DATA_DIRECTORY + "INPUT_STOCK_DATA.xml"));
		databaseTester.setDataSet(dataSet);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onSetup();
	}

	@After
	//テスト実行後にデータを"INPUT_STOCK_DATA"にする
	public void after() throws Exception {
		databaseTester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.onTearDown();
	}

	@Test
	//商品コードが商品テーブルにあり、在庫テーブルにない時
	public void testInsertStock_正常系() throws Exception {

		int code = 300;
		stockService.insertStock(code);

		//RDBからメソッド実行後のデータを取得（実際値）
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("STOCK");

		//メソッド実行後の結果比較用データを読み込む(期待値）
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(new File(TEST_DATA_DIRECTORY + "EXPECTED_CREATE_STOCK_DATA.xml"));
		ITable expectedTable = expectedDataSet.getTable("STOCK");

		DiffCollectingFailureHandler myHandler = new DiffCollectingFailureHandler();
		//比較
		Assertion.assertEquals(expectedTable, actualTable, myHandler);

	}

	@Test
	//商品コードが在庫テーブルに存在する
	public void testInsertStock_異常系_商品コードの重複() throws ClassNotFoundException, SQLException, NoGoodsException {

		int code = 100;

		try {
			stockService.insertStock(code);

		} catch (GoodsCodeDupulicateException e) {
			assertTrue(true);
		}
	}

}