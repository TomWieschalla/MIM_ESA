package org.dieschnittstelle.jee.esa.ejbs.client;

import java.io.IOException;

public class Util {

	public static void step() {
		try {
			System.out.println("/>");
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
