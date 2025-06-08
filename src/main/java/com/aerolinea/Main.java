package com.aerolinea;

import com.aerolinea.gui.MainWindow;
import com.aerolinea.util.InicializadorBD;

public class Main {
    public static void main(String[] args) {
        InicializadorBD.inicializar();
        new MainWindow();
    }
}