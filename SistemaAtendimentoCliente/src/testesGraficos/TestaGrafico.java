/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testesGraficos;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.swing.filechooser.FileSystemView;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class TestaGrafico {

    public static void main(String[] args) throws IOException {
        String diretorioUsuario = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
        OutputStream fis = new FileOutputStream(diretorioUsuario + File.separator + "InoveSystems" + File.separator + "Graficos" + File.separator + "grafico.png");
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        ds.addValue(40.5, "maximo", "dia 1");
        ds.addValue(38.2, "minimo", "dia 2");
        ds.addValue(37.3, "minimo", "dia 3");
        ds.addValue(31.5, "maximo", "dia 4");
        ds.addValue(35.7, "maximo", "dia 5");
        ds.addValue(42.5, "minimo", "dia 6");

        // cria o gráfico
        JFreeChart grafico = ChartFactory.createLineChart("Atendimentos Sanar", "Dia","Quantidade", ds, PlotOrientation.HORIZONTAL, true, true, false);
        // OutputStream arquivo = new FileOutputStream("grafico.png");        
        ChartUtilities.writeChartAsPNG(fis, grafico, 550, 400);
        fis.close();

    }

}
