package com.cricmantic.parsing;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class scrap {
	private static String result = "";

	public String second(String uri) throws AWTException, InterruptedException {

		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(uri));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TimeUnit.SECONDS.sleep(10);

			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.delay(100);
			robot.keyPress(KeyEvent.VK_C);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_C);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			try {
				result = (String) clipboard.getData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}

	public String summary(String uri) throws AWTException, InterruptedException {

		TimeUnit.SECONDS.sleep(3);

		Robot robot = new Robot();
		robot.mouseMove(445, 322);
		robot.delay(200);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(100);
		// robot.keyPress(KeyEvent.VK_CONTROL);
		// robot.keyPress(KeyEvent.VK_A);
		// robot.keyRelease(KeyEvent.VK_CONTROL);
		// robot.keyRelease(KeyEvent.VK_A);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_C);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_C);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public String first(String uri) throws AWTException, InterruptedException {

		TimeUnit.SECONDS.sleep(2);

		Robot robot = new Robot();
		robot.mouseMove(370, 322);
		robot.delay(200);
		robot.mousePress(InputEvent.BUTTON1_MASK);
		robot.mouseRelease(InputEvent.BUTTON1_MASK);
		robot.delay(100);
		// robot.keyPress(KeyEvent.VK_CONTROL);
		// robot.keyPress(KeyEvent.VK_A);
		// robot.keyRelease(KeyEvent.VK_CONTROL);
		// robot.keyRelease(KeyEvent.VK_A);
		robot.delay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.delay(100);
		robot.keyPress(KeyEvent.VK_C);
		robot.delay(1000);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_C);
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		try {
			result = (String) clipboard.getData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	public String facts(String uri) throws AWTException, InterruptedException {

		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI(uri));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			TimeUnit.SECONDS.sleep(8);

			Robot robot = new Robot();
			robot.mouseMove(844, 270);
			robot.delay(200);
			robot.mousePress(InputEvent.BUTTON1_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_MASK);
			robot.delay(8000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.keyPress(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.delay(1000);
			robot.keyPress(KeyEvent.VK_CONTROL);
			robot.delay(100);
			robot.keyPress(KeyEvent.VK_C);
			robot.delay(1000);
			robot.keyRelease(KeyEvent.VK_CONTROL);
			robot.keyRelease(KeyEvent.VK_A);
			robot.keyRelease(KeyEvent.VK_C);
			Toolkit toolkit = Toolkit.getDefaultToolkit();
			Clipboard clipboard = toolkit.getSystemClipboard();
			try {
				result = (String) clipboard.getData(DataFlavor.stringFlavor);
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return result;
	}
}