/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graficos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author EngComp
 */
public class GeradorDeGraficosBarras {

    private OutputStream grafi;
    private String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
    private DefaultCategoryDataset ds;
    private String titulox;
    private String tituloy;
    private String titulografico;
    private String tituloplotagem;
    private int tamanhografix;
    private int tamanhografiy;
    

    public GeradorDeGraficosBarras() {

    }

    public void plotagem(GeradorDeGraficosBarras ger) throws IOException {
        this.ds = ger.ds;
        this.titulox = ger.titulox;
        this.tituloy = ger.tituloy;
        this.titulografico = ger.titulografico;
        this.tituloplotagem = ger.tituloplotagem;
        this.tamanhografix = ger.tamanhografix;
        this.tamanhografiy = ger.tamanhografiy;
        this.grafi = new FileOutputStream(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Graficos" + File.separator + tituloplotagem +".png");
        JFreeChart grafico = ChartFactory.createStackedBarChart3D(this.titulografico, this.titulox, this.tituloy, this.ds, PlotOrientation.VERTICAL, true, true, false);
        ChartUtilities.writeChartAsPNG(this.grafi, grafico, this.tamanhografix, this.tamanhografiy);
        this.grafi.close();
        //return new javax.swing.ImageIcon((diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Graficos" + File.separator + tituloplotagem +".png"));
        //  graficolabel.setIcon(new javax.swing.ImageIcon((diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Graficos" + File.separator + "grafico.png")));
    }

    public DefaultCategoryDataset getDs() {
        return ds;
    }

    public void setDs(DefaultCategoryDataset ds) {
        this.ds = ds;
    }

    public String getTitulox() {
        return titulox;
    }

    public void setTitulox(String titulox) {
        this.titulox = titulox;
    }

    public String getTituloy() {
        return tituloy;
    }

    public void setTituloy(String tituloy) {
        this.tituloy = tituloy;
    }

    public String getTitulografico() {
        return titulografico;
    }

    public void setTitulografico(String titulografico) {
        this.titulografico = titulografico;
    }

    public String getTituloplotagem() {
        return tituloplotagem;
    }

    public void setTituloplotagem(String tituloplotagem) {
        this.tituloplotagem = tituloplotagem;
    }

    public int getTamanhografix() {
        return tamanhografix;
    }

    public void setTamanhografix(int tamanhografix) {
        this.tamanhografix = tamanhografix;
    }

    public int getTamanhografiy() {
        return tamanhografiy;
    }

    public void setTamanhografiy(int tamanhografiy) {
        this.tamanhografiy = tamanhografiy;
    }

}
