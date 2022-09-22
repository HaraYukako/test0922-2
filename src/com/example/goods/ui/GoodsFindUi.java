package com.example.goods.ui;

import static com.example.common.constant.MessageConstant.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.example.common.ui.BaseUi;
import com.example.goods.domain.Goods;
import com.example.goods.exception.NoGoodsException;
import com.example.goods.service.GoodsService;

class GoodsFindUi extends BaseUi {

	private final Scanner console;
	private final GoodsService goodsService;

	GoodsFindUi(Scanner console, GoodsService goodsService) {
		this.console = console;
		this.goodsService = goodsService;
	}

	void findAllGoods() throws ClassNotFoundException, SQLException {
		System.out.println(COMMON_FIND_ALL_START);
		try {
			List<Goods> goodsList = goodsService.findAllGoods();
			for (Goods goods : goodsList) {
				System.out.println(goods);
			}
		} catch (NoGoodsException e) {
			System.out.println(GOODS_NO_DATA);
		}
		System.out.println(COMMON_FIND_END);
	}

	void findGoods() throws ClassNotFoundException, SQLException {
		//TODO この部分を作成してください。
		while (true) {
			System.out.println(COMMON_FIND_START);
			//System.out.println(GOODS_CODE);
			Goods goods = null;

			try {
				goods = goodsService.findGoods(goodsCodeInput(console));

				System.out.println(goods);
			} catch (NoGoodsException e) {
				System.out.println(GOODS_NO_DATA);
			}

			System.out.println(COMMON_FIND_END);
			System.out.println(COMMON_NEXT_OR_END);
			if (isNextOrEndInput(console))
				break;
		}
		//TODO この部分を作成してください。
		/*
		 * goodsコードを取得　どっかのメソッド使う？
		 * findGoods(goodsCode)
		 * if(nullかどうか)
		 *
		 *
		 */

		/*
		 * 終了か続けるかのやつ
		*/
		//System.out.println("作成中です");
	}
}
