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
public class LeitorXmlTemp {

    String channel = " ";
    String item = " ";
    InputStream is;

    public LeitorXmlTemp() {

    }

    public String adicionar() {
        // toop    http://www.yr.no/sted/Brasil/Rio_Grande_do_Sul/Pinheiro_Machado/varsel.xml
        String urlstring = "http://servicos.cptec.inpe.br/RSS/cidade/3996/previsao.xml ";//este é o rss do meu blog
        try {
            is = new URL(urlstring).openConnection().getInputStream();
        } catch (IOException ex) {
            Logger.getLogger(LeitorXmlTemp.class.getName()).log(Level.SEVERE, null, ex);
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LeitorXmlTemp.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document documento = null;
        try {
            documento = documentBuilder.parse(is);
        } catch (SAXException ex) {
            Logger.getLogger(LeitorXmlTemp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeitorXmlTemp.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList list = documento.getElementsByTagName("channel");
        NodeList lista = documento.getElementsByTagName("title");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String fonte = element.getElementsByTagName("title").item(i).getTextContent();
                System.out.println("Fonte: " + fonte);
//                System.out.println("Link: " + element.getElementsByTagName("link").item(i).getTextContent());
//                System.out.println(" ");
                channel = "..:: " + fonte + " ::.. " + element.getElementsByTagName("link").item(i).getTextContent() + " ";

                for (int a = 0; a < lista.getLength(); a++) {
                    Node nodeA = lista.item(a);
                    if (nodeA.getNodeType() == Node.ELEMENT_NODE) {
                        try {
                            System.out.println(fonte + ": " + element.getElementsByTagName("description").item(a).getTextContent() + ". ");
                            // item = item + fonte + ": " + element.getElementsByTagName("description").item(a + 2).getTextContent() + ". ";
                        } catch (NullPointerException ex) {

                        }
                    }

                }

            }
        }
        String Feed = channel + item;
        return Feed;
    }

}
