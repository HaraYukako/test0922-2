package com.example.stock.repository;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
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

import com.example.stock.util.StockServiceAndRepositoryFactory;

public class StockRepositoryTest {

	//ファイルのpackageを定数化
	private static final String TEST_DATA_DIRECTORY = "./test/data/stock/";
	//テスト用のデータベースを呼び出す変数
	private IDatabaseTester databaseTester;
	//インスタンスを生成
	private StockRepository stockRepository = StockServiceAndRepositoryFactory.getStockRepository();

	private Connection connection;

	//ストックリポジトリクラスのテストを行う
	public StockRepositoryTest() throws Exception {
		databaseTester =
				// MySQLの場合
				new JdbcDatabaseTester("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:3306/warehouse?useSSL=false",
						"root", "root");
	}

	@Before
	//テスト実行前の初期化
	//初期化用データを読み込んで、RDBのデータをクリアしてから、データを登録。
	public void before() throws Exception {
		connection = databaseTester.getConnection().getConnection();

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
	//商品コードが商品テーブルに存在し、在庫テーブルに存在しない時
	public void testInsertStock_正常系() throws Exception {
		int code = 300;
		stockRepository.insertStock(connection, code);

		//RDBからメソッド実行後のデータを取得（実際値）
		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("STOCK");

		//メソッド実行後の結果比較用データを読み込む(期待値）
		IDataSet expectedDataSet = new FlatXmlDataSetBuilder()
				.build(new File(TEST_DATA_DIRECTORY + "EXPECTED_CREATE_STOCK_DATA.xml"));
		ITable expectedTable = expectedDataSet.getTable("STOCK");

		//期待値と実際値を比較
		DiffCollectingFailureHandler myHandler = new DiffCollectingFailureHandler();
		Assertion.assertEquals(expectedTable, actualTable, myHandler);
	}

	@Test
	//商品コードが在庫テーブルに存在する時
	public void testInsertStock_異常系_商品コードの重複() {
		int code = 100;
		try {
			boolean stockRepBoo = stockRepository.isStockSearch(connection, code);
			assertTrue(stockRepBoo);
			return;
		} catch (SQLException e) {
			fail();
		}
	}

	@Test
	//商品コードが在庫テーブルに存在しないとき
	public void testInsertStock_異常系_商品コードがない() throws SQLException {
		int code = 300;

		boolean stockRepBoolean = stockRepository.isStockSearch(connection, code);
		assertFalse(stockRepBoolean);
		return;

	}
}