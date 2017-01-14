/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.UnknownHostException;
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
public class LeitorXml {

    String channel = "";
    String item = "";
    InputStream is;

    public LeitorXml() {

    }

    public String adicionar() {
        channel="";
        item="";
        is=null;
        String urlstring = "http://g1.globo.com/dynamo/rs/rio-grande-do-sul/rss2.xml ";//este é o rss do meu blog
        try {
            is = new URL(urlstring).openConnection().getInputStream();
        } catch (IOException ex) {
            return "FEED DE NOTÍCIAS SEM RETORNO, VERIFIQUE SUA CONEXÃO COM A INTERNET, SE O PROBLEMA PERCISTIR ENTRE EM CONTATO COM O SUPORTE TECNICO!";
        }
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(LeitorXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document documento = null;
        try {
            documento = documentBuilder.parse(is);
        } catch (UnknownHostException ex) {
            return "VERIFIQUE SUA CONEXÃO COM A INTERNET E RECEBA NOTÍCIAS ATUALIZADAS!";
        } catch (SAXException ex) {

        } catch (IOException ex) {
            return "VERIFIQUE SUA CONEXÃO COM A INTERNET E RECEBA NOTÍCIAS ATUALIZADAS!";
        } catch (IllegalArgumentException ex) {

        }
        try {
            NodeList list = documento.getElementsByTagName("channel");
            NodeList lista = documento.getElementsByTagName("item");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String fonte = element.getElementsByTagName("title").item(i).getTextContent();
//                System.out.println("Fonte: " + fonte);
//                System.out.println("Link: " + element.getElementsByTagName("link").item(i).getTextContent());
//                System.out.println(" ");
                    channel = "..:: " + fonte + " ::.. " + element.getElementsByTagName("link").item(i).getTextContent() + " ";

                    for (int a = 0; a < lista.getLength(); a++) {
                        Node nodeA = lista.item(a);
                        if (nodeA.getNodeType() == Node.ELEMENT_NODE) {
//                        System.out.println(fonte + ": " + element.getElementsByTagName("title").item(a + 2).getTextContent() + ". ");
                            item = item + fonte + ": " + element.getElementsByTagName("title").item(a + 2).getTextContent() + ". ";
                        }

                    }

                }
            }
            String Feed = channel + item;
            return Feed;
        } catch (NullPointerException ex) {
            return "VERIFIQUE SUA CONEXÃO COM A INTERNET E RECEBA NOTÍCIAS ATUALIZADAS!";
        }
    }

}
