package com.example;

import static org.junit.Assert.*;

import java.util.Scanner;

import org.junit.Test;

import com.example.common.ui.BaseUi;

public class BaseUiTest extends BaseUi {
	private final Scanner console = new Scanner(System.in);

	@Test
	public void test() {
		String str = nextOrEndInput(console, "aaaa");
		assertEquals("e", str);
	}

}
