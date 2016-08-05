package com.burger.king;
 
import java.io.File;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

 
@FixMethodOrder (MethodSorters.NAME_ASCENDING)
 
public class survey {
    private static WebDriver driver;
	
	static String receiptNum="";
	static String url="https://kor.tellburgerking.com";
	static int speed_int = 100;
	
	public void inputNumber(String number, String txt_speed){
	      // 텍스트 필드값 가져오기
		receiptNum = number;
		System.out.println("speed.equals "+txt_speed );
		speed_int = Integer.parseInt(txt_speed);	
		
		
	}
	
 
    @BeforeClass
    public static void setUp() throws Exception {
    	
    	File file = new File("c:/chromedriver.exe");
    	File file2 = new File("d:/chromedriver.exe");
   		if(file.isFile()){         
   			System.setProperty("webdriver.chrome.driver", "c:/chromedriver.exe"); //크롬 드라이버 파일 경로설정
		}else if(file2.isFile()){
			System.setProperty("webdriver.chrome.driver", "d:/chromedriver.exe"); //크롬 드라이버 파일 경로설정	
		}else{
			//안내문
			runner.labelIntroduce.setText("C 또는 D드라이브에 chromedriver가 없습니다.\n");
		}
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
		driver.get(url); 
		 driver.manage().window().maximize();
//	    System.setProperty("selenide.browser", "Chrome");
//	    open(url);
		

    }
    
    public static void login() throws InterruptedException   {
    	boolean boo = true;
    	WebElement NextButton = driver.findElement(By.id("NextButton"));
    	NextButton.click();   
    	
        driver.findElement(By.id("CN1")).sendKeys(receiptNum);  //ID
		Thread.sleep(1000);
		driver.findElement(By.id("NextButton")).click();
        
		
		int i = 0;
        while(boo){
        	try{
				boolean radioButtonHolder = driver.findElements(By.className("radioButtonHolder")).size() > 0;
				boolean checkboxBranded = driver.findElements(By.className("checkboxBranded")).size() > 0;
				boolean radioBranded = driver.findElements(By.className("radioBranded")).size() > 0;
				boolean checkCode = driver.findElements(By.className("ValCode")).size() > 0;
				boolean Next = driver.findElements(By.id("NextButton")).size() > 0;
             	if(radioButtonHolder){
             		driver.findElement(By.className("radioButtonHolder")).click();
             		Thread.sleep(speed_int);
             	}
             	else if(checkboxBranded){
                 	driver.findElement(By.className("checkboxBranded")).click();
                 	Thread.sleep(speed_int);
             	}
             	else if(radioBranded){
             		driver.findElement(By.className("radioBranded")).click();
             		Thread.sleep(speed_int);
             	}
             	if(Next){
             		driver.findElement(By.id("NextButton")).click();
             		Thread.sleep(speed_int);
             	}
             		
             	
             	if(checkCode)
             		boo = false;
//             	System.out.println(i++);
        	}catch (NoSuchElementException e){
        		
        	}
           
        }
        
        String checkCode = driver.findElement(By.xpath("//*[@id='FNSfinishText']/div/p[2]")).getText();

        runner.labelIntroduce.setText(checkCode);
    }
    
 
    @Test
    public static void run() throws Exception {
    	login();
		Thread.sleep(500);
    }
 
    @AfterClass
    public static void tearDown() throws Exception {
        driver.quit();
    }
 
}