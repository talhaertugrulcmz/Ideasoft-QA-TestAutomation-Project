package step;

import com.thoughtworks.gauge.Gauge;
import com.thoughtworks.gauge.Step;
import driver.Driver;
import method.Methods;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

import static org.assertj.core.api.Assertions.*;


public class StepImplementation extends Driver {

    Methods methods;
    public StepImplementation() {
        methods = new Methods();

    }


    // Bekleme Süresi
    @Step("<int> saniye kadar bekle")
    public void waitBySeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    // URL Kontrolü
    @Step("Sayfa Kontrolü")
    public void UrlKontrol() {

        String SayfaKonrolu = driver.getCurrentUrl();


        if (SayfaKonrolu.contains("https://testcase.myideasoft.com/")) {
            logger.info("Dogru URL Adresi...");
        } else {
            logger.info("URL adresi dogru degil !! Lutfen kontrol ediniz...");
        }
    }



    // Ürün İsmi Yazma
    @Step("<css> li arama text alanina tiklama <text> ürün ismi yazma")
    public void UrunIsmi(String css, String text){

        WebElement UrunIsmi = methods.findElement(By.cssSelector(css));
        UrunIsmi.sendKeys(text);

    }



    // Ürün Arama
    @Step("<xpath> li butona tikla")
    public void UrunArama(String xpath){

        methods.hoverElement(By.xpath(xpath));
        methods.findElement(By.xpath(xpath)).click();

    }


    // Ürüne Kadar Sayfa Scroll
    @Step("<css> li ürüne kadar sayfayı kaydır")
    public void SayfaScroll(String css) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scroll(0,500)");

    }


    // Ürün Sepete Ekleme
    @Step("<css1> li ürüne tıkla ,<id> li kutucuga tikla <xpath> li adet sec ve <css3> lü sepete ekle butona tıkla")
    public void UrunSepetEkleme(String css1, String id, String xpath, String css2) throws InterruptedException {

        methods.findElement(By.cssSelector(css1)).click();
        Thread.sleep(1000);

        methods.hoverElement(By.id(id));
        methods.findElement(By.id(id)).click();
        Thread.sleep(1000);


        methods.findElement(By.xpath(xpath)).click();
        Thread.sleep(1000);

        methods.hoverElement(By.cssSelector(css2));
        methods.findElement(By.cssSelector(css2)).click();

    }



    // SEPETİNİZE EKLENMİŞTİR Pop-Up Mesajı Kontrolü
    @Step("Pop-up Kontrolü")
    public void MesajKontrolu(){

        String PopupMesaj = driver.findElement(By.cssSelector("body[class=\"current-page-product-detail\"]")).getText();

        if (PopupMesaj.contains("SEPETİNİZE EKLENMİŞTİR")) {
            logger.info("SEPETINIZE EKLENMISTIR ... Urununuz basarili bir sekilde sepete eklenmistir.");
        } else {
            logger.info("Sectiginiz Urun Sepete Eklenemedi... ");
        }

    }



    // Sepete Git ve Ürün Sayı Kontrolu
    @Step("<css> li butona tikla ve sayi kontrol yap")
    public void SepetKontrol(String css){


        methods.findElement(By.cssSelector(css)).click();

        WebElement UrunText = driver.findElement(By.xpath("//*[@id=\"cart-items\"]/div/div/div[2]/div/div/div[2]/div/div/div/input"));
        int UrunValue = Integer.parseInt(UrunText.getAttribute("value"));

        logger.info("Sepete Eklediginiz Urun Adedi = " + UrunValue);


        Assertions.assertEquals(UrunValue,  5);
        logger.info("Sepetteki Mevcut Urun Adediniz = 5'tir\n" +
                    "Sepete Eklediginiz Urun Adedi Sepetteki Mevcut Urun Adedi Ile Ayni...");


    }

}
