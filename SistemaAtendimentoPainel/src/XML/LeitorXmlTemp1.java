/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import XML.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author EngComp
 */
public class LeitorXmlTemp1 {

    String atual = " ";
    InputStream is;
    String min;
    String max;
    String Feed;

    public LeitorXmlTemp1() {

    }

    public String adicionar() {
        //http://api.openweathermap.org/data/2.5/weather?q=Pinheiro-Machado,BR&units=metric&,BR&mode=xml
        // toop    http://www.yr.no/sted/Brasil/Rio_Grande_do_Sul/Pinheiro_Machado/varsel.xml
        String urlstring = "http://www.yr.no/sted/Brasil/Rio_Grande_do_Sul/Pinheiro_Machado/varsel.xml";//este é o rss do meu blog
        try {
            is = new URL(urlstring).openConnection().getInputStream();
        } catch (UnknownHostException ex) {

        } catch (IOException ex) {
            Logger.getLogger(LeitorXmlTemp1.class.getName()).log(Level.SEVERE, null, ex);
        }

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LeitorXmlTemp1.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document documento = null;
        try {
            documento = documentBuilder.parse(is);
        
        } catch (IllegalArgumentException ex) {

        } catch (SAXException ex) {
            
        } catch (IOException ex) {
           
        }
        try {
            NodeList list = documento.getElementsByTagName("temperature");
            Node node = list.item(0);
            Element element = (Element) node;
            atual = element.getAttribute("value");
            min = element.getAttribute("min");
            max = element.getAttribute("max");
            //String fonte = element.getElementsByTagName("time").item(i).getTextContent();
            System.out.println("Temperatura Atual: " + atual + "°C" + " Min: " + min + "°C" + " Max: " + max + "°C");
            Feed = atual + "°C";
            return Feed;
        } catch (NullPointerException ex) {
            return "-- °C"; 
        }

    }

}
