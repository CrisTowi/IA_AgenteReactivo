package com.ia.simulacion;

import com.ia.agente.AgenteReactivo;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author Carlos
 */
public class jTablero extends javax.swing.JFrame implements AgenteReactivo {

    private String coordenadasAgente;
    private String[][] mTablero;
    private boolean existRock = false;
    private boolean[] moveBlock = new boolean[4];
    final int UNIDAD = 70, MARCO_X = 30, MARCO_Y = 30, NO_AGENTES = 1;

    public jTablero() {
        initComponents();
        mTablero = jPlay.mTablero;
        BuscarAgente();
        CargarTablero();
        this.getContentPane().validate();
        this.getContentPane().repaint();
        ExecutorService service = Executors.newFixedThreadPool(NO_AGENTES);
        for (int i = 0; i < NO_AGENTES; i++) {
            service.execute(new Runnable() {
                @Override
                public void run() {
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            Movimientos();
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(task, 0, 500);

                }
            });
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void formMouseClicked(java.awt.event.MouseEvent evt) {                                  
        System.out.println(evt.getPoint().x + "," + evt.getPoint().y);
    }                                 

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(jTableroRespaldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(jTableroRespaldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(jTableroRespaldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(jTableroRespaldo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new jTablero().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify                     
    // End of variables declaration                   

    private void BuscarAgente() {
        for (int i = 0; i < mTablero.length; i++) {
            for (int j = 0; j < mTablero[i].length; j++) {
                if ("Agente".equals(mTablero[i][j])) {
                    coordenadasAgente = j + "," + i;
                }
            }
        }
    }

    private void CargarTablero() {
        int alto = mTablero.length;
        int ancho = mTablero[0].length;
        this.setPreferredSize(new Dimension((UNIDAD * ancho) + 50, (UNIDAD * alto) + 50));
        this.pack();
        this.setVisible(true);
    }

    @Override
    public synchronized void Movimientos() {
        StringTokenizer strinTokenizer = new StringTokenizer(coordenadasAgente, ",");
        int coordenadaX = Integer.parseInt(strinTokenizer.nextToken());
        int coordenadaY = Integer.parseInt(strinTokenizer.nextToken());
        int newCoordenadaY = Integer.MIN_VALUE, newCoordenadaX = Integer.MIN_VALUE, caseRandom;
        Random r = new Random();
        boolean flagMove;
        boolean[] isValidMove = new boolean[4];
        String[] metaDatos = Analizar();
        mTablero[coordenadaY][coordenadaX] = "NULL";
        for (int i = 0; i < metaDatos.length; i++) {
            if (!"0".equals(metaDatos[i]) && !"-1".equals(metaDatos[i])) {
                strinTokenizer = new StringTokenizer(metaDatos[i], ",");
                newCoordenadaX = Integer.parseInt(strinTokenizer.nextToken());
                newCoordenadaY = Integer.parseInt(strinTokenizer.nextToken());
            } else if ("0".equals(metaDatos[i])) {
                isValidMove[i] = true;
            }
        }
        if (existRock) {
            if (BuscarNave()) {
                existRock = false;
            }
        } else if (newCoordenadaX != Integer.MIN_VALUE && newCoordenadaY != Integer.MIN_VALUE) {
            existRock = true;
            mTablero[newCoordenadaY][newCoordenadaX] = "Agente";
            coordenadasAgente = newCoordenadaX + "," + newCoordenadaY;
        } else {
            do {
                caseRandom = r.nextInt(4);
                flagMove = false;
                if (isValidMove[caseRandom] && caseRandom == 0) {
                    flagMove = true;
                    newCoordenadaX = coordenadaX - 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                } else if (isValidMove[caseRandom] && caseRandom == 1) {
                    flagMove = true;
                    newCoordenadaX = coordenadaX + 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                } else if (isValidMove[caseRandom] && caseRandom == 2) {
                    flagMove = true;
                    newCoordenadaY = coordenadaY - 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                } else if (isValidMove[caseRandom] && caseRandom == 3) {
                    flagMove = true;
                    newCoordenadaY = coordenadaY + 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                }
            } while (!flagMove);
        }
        for (int i = 0; i < mTablero.length; i++) {
            for (int j = 0; j < mTablero[i].length; j++) {
                System.out.print(mTablero[i][j] + "\t");
            }
            System.out.println("");
        }
        System.out.println("");
        repaint();
    }

    @Override
    public boolean BuscarNave() {
        String coordenadaNave = "";
        double[] moveTo;
        double[] distanciasEuclidianas = new double[4];
        StringTokenizer stringTokenizer = new StringTokenizer(coordenadasAgente, ",");
        int coordenadaX = Integer.parseInt(stringTokenizer.nextToken());
        int coordenadaY = Integer.parseInt(stringTokenizer.nextToken());
        int newCoordenadaY, newCoordenadaX;
        Random random = new Random();
        for (int i = 0; i < mTablero.length; i++) {
            for (int j = 0; j < mTablero[i].length; j++) {
                if ("Nave".equals(mTablero[i][j])) {
                    coordenadaNave = j + "," + i;
                }
            }
        }
        for (int i = 0; i < distanciasEuclidianas.length; i++) {
            distanciasEuclidianas[i] = Double.MAX_VALUE - (i * 10000000);
        }
        newCoordenadaX = coordenadaX - 1;
        if (validarCoordenadaX(newCoordenadaX)) {
            distanciasEuclidianas[0] = distanciaEuclidiana(coordenadaNave, newCoordenadaX + "," + coordenadaY);
        }
        newCoordenadaX = coordenadaX + 1;
        if (validarCoordenadaX(newCoordenadaX)) {
            distanciasEuclidianas[1] = distanciaEuclidiana(coordenadaNave, newCoordenadaX + "," + coordenadaY);
        }
        newCoordenadaY = coordenadaY - 1;
        if (validarCoordenadaX(newCoordenadaY)) {
            distanciasEuclidianas[2] = distanciaEuclidiana(coordenadaNave, coordenadaX + "," + newCoordenadaY);
        }
        newCoordenadaY = coordenadaY + 1;
        if (validarCoordenadaX(newCoordenadaY)) {
            distanciasEuclidianas[3] = distanciaEuclidiana(coordenadaNave, coordenadaX + "," + newCoordenadaY);
        }
        String[] metaDatos = Analizar();
        moveTo = ordeminetoBurbujaDistancia(distanciasEuclidianas);
        Arrays.sort(distanciasEuclidianas, 0, distanciasEuclidianas.length);
        int iMove = 0;
        int distanciaRepeatPos = -1;
        boolean flagProbalidiad = true;
        for (String string : metaDatos) {
            System.out.print("_ " + string);
        }
        while (iMove < moveTo.length) {
            //------------PARCHE--------------///
            if (flagProbalidiad) {
                for (int i = 1; i < distanciasEuclidianas.length; i++) {
                    System.out.println(distanciasEuclidianas[i - 1] + "_" + distanciasEuclidianas[i]);
                    if (distanciasEuclidianas[i - 1] == distanciasEuclidianas[i]) {
                        distanciaRepeatPos = i - 1;
                    }
                }
                if (distanciaRepeatPos != -1) {
                    distanciaRepeatPos = distanciaRepeatPos + random.nextInt(2);
                    System.out.println("Probabilidad:  " + distanciaRepeatPos);
                }
                flagProbalidiad = false;
            }
            //------------PARCHE-----------------/

            //
            if ("0".equals(metaDatos[(int) moveTo[iMove]])) {
                if ((int) moveTo[iMove] == 0 && (int) distanciasEuclidianas[0] != 0 && moveBlock[0] == true) {
                    newCoordenadaX = coordenadaX - 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 1 && (int) distanciasEuclidianas[1] != 0 && moveBlock[1] == true) {
                    newCoordenadaX = coordenadaX + 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 2 && (int) distanciasEuclidianas[2] != 0 && moveBlock[2] == true) {
                    newCoordenadaY = coordenadaY - 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 3 && (int) distanciasEuclidianas[3] != 0 && moveBlock[3] == true) {
                    newCoordenadaY = coordenadaY + 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if (distanciasEuclidianas[0] == 0) {
                    mTablero[coordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + coordenadaY;
                    return true;
                }

            } else if (distanciaRepeatPos == -1) {
                System.out.println("moveTo[iMove] : " + (int) moveTo[iMove]);
                System.out.println((((int) moveTo[iMove] == 1)) + "" + ((int) distanciasEuclidianas[1] != 0) + "" + ("0".equals(metaDatos[(int) moveTo[iMove]])) + "" + (moveBlock[1] == true));
                if ((int) moveTo[iMove] == 0 && (int) distanciasEuclidianas[0] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveBlock[0] == true) {
                    newCoordenadaX = coordenadaX - 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 1 && (int) distanciasEuclidianas[1] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveBlock[1] == true) {
                    newCoordenadaX = coordenadaX + 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 2 && (int) distanciasEuclidianas[2] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveBlock[2] == true) {
                    newCoordenadaY = coordenadaY - 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if ((int) moveTo[iMove] == 3 && (int) distanciasEuclidianas[3] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveBlock[3] == true) {
                    newCoordenadaY = coordenadaY + 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    for (int i = 0; i < moveBlock.length; i++) {
                        moveBlock[i] = true;
                    }
                    return false;
                } else if (distanciasEuclidianas[0] == 0) {
                    mTablero[coordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + coordenadaY;
                    return true;
                }

            } else {
                for (int i = 0; i < moveBlock.length; i++) {
                    moveBlock[i] = true;
                }
                if ((int) moveTo[iMove] == 0 && (int) distanciasEuclidianas[0] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) & moveTo[distanciaRepeatPos] == 0) {
                    newCoordenadaX = coordenadaX - 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    moveBlock[1] = false;
                    return false;
                } else if ((int) moveTo[iMove] == 1 && (int) distanciasEuclidianas[1] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveTo[distanciaRepeatPos] == 1) {
                    newCoordenadaX = coordenadaX + 1;
                    mTablero[coordenadaY][newCoordenadaX] = "Agente";
                    coordenadasAgente = newCoordenadaX + "," + coordenadaY;
                    moveBlock[0] = false;
                    return false;
                } else if ((int) moveTo[iMove] == 2 && (int) distanciasEuclidianas[2] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveTo[distanciaRepeatPos] == 2) {
                    newCoordenadaY = coordenadaY - 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    moveBlock[3] = false;
                    return false;
                } else if ((int) moveTo[iMove] == 3 && (int) distanciasEuclidianas[3] != 0
                        && "0".equals(metaDatos[(int) moveTo[iMove]]) && moveTo[distanciaRepeatPos] == 3) {
                    newCoordenadaY = coordenadaY + 1;
                    mTablero[newCoordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + newCoordenadaY;
                    moveBlock[2] = false;
                    return false;
                } else if (distanciasEuclidianas[0] == 0) {
                    mTablero[coordenadaY][coordenadaX] = "Agente";
                    coordenadasAgente = coordenadaX + "," + coordenadaY;
                    return true;
                }
            }
            iMove++;
        }
        return true;

    }

    @Override
    public String[] Analizar() {
        int newCoordenadaY, newCoordenadaX;
        String[] metaDatos = new String[4];
        StringTokenizer stringTokenizer = new StringTokenizer(coordenadasAgente, ",");
        int coordenadaX = Integer.parseInt(stringTokenizer.nextToken());
        int coordenadaY = Integer.parseInt(stringTokenizer.nextToken());
        for (int i = 0; i < metaDatos.length; i++) {
            metaDatos[i] = "-1";
        }
        newCoordenadaX = coordenadaX - 1;
        if (validarCoordenadaX(newCoordenadaX)) {
            switch (mTablero[coordenadaY][newCoordenadaX]) {
                case "Piedra":
                    metaDatos[0] = newCoordenadaX + "," + coordenadaY;
                    break;
                case "Obstaculo":
                case "Nave":
                    metaDatos[0] = "-1";
                    break;
                default:
                    metaDatos[0] = "0";
                    break;
            }
        }
        newCoordenadaX = coordenadaX + 1;
        if (validarCoordenadaX(newCoordenadaX)) {
            switch (mTablero[coordenadaY][newCoordenadaX]) {
                case "Piedra":
                    metaDatos[1] = newCoordenadaX + "," + coordenadaY;
                    break;
                case "Obstaculo":
                case "Nave":
                    metaDatos[1] = "-1";
                    break;
                default:
                    metaDatos[1] = "0";
                    break;
            }
        }
        newCoordenadaY = coordenadaY - 1;
        if (validarCoordenadaY(newCoordenadaY)) {
            switch (mTablero[newCoordenadaY][coordenadaX]) {
                case "Piedra":
                    metaDatos[2] = coordenadaX + "," + newCoordenadaY;
                    break;
                case "Obstaculo":
                case "Nave":
                    metaDatos[2] = "-1";
                    break;
                default:
                    metaDatos[2] = "0";
                    break;
            }
        }
        newCoordenadaY = coordenadaY + 1;
        if (validarCoordenadaY(newCoordenadaY)) {
            switch (mTablero[newCoordenadaY][coordenadaX]) {
                case "Piedra":
                    metaDatos[3] = coordenadaX + "," + newCoordenadaY;
                    break;
                case "Obstaculo":
                case "Nave":
                    metaDatos[3] = "-1";
                    break;
                default:
                    metaDatos[3] = "0";
                    break;
            }
        }
        return metaDatos;

    }

    private double distanciaEuclidiana(String coordenadaXY1, String coordenadaXY2) {
        StringTokenizer stringTokenizer;
        stringTokenizer = new StringTokenizer(coordenadaXY1, ",");
        int x1 = Integer.parseInt(stringTokenizer.nextToken());
        int y1 = Integer.parseInt(stringTokenizer.nextToken());
        stringTokenizer = new StringTokenizer(coordenadaXY2, ",");
        int x2 = Integer.parseInt(stringTokenizer.nextToken());
        int y2 = Integer.parseInt(stringTokenizer.nextToken());
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public boolean validarCoordenadaX(int coordenada) {
        return (coordenada >= 0 && coordenada < mTablero[0].length) ? true : false;
    }

    public boolean validarCoordenadaY(int coordenada) {
        return (coordenada >= 0 && coordenada < mTablero.length) ? true : false;
    }

    public double[] ordeminetoBurbujaDistancia(double[] arrayDists) {
        double[] newOrden = new double[4];
        for (int i = 0; i < newOrden.length; i++) {
            newOrden[i] = i;
        }
        double auxDist, auxOrden;
        for (int i = 1; i < arrayDists.length; i++) {
            for (int j = 0; j < arrayDists.length - 1; j++) {
                if (arrayDists[j] > arrayDists[j + 1]) {
                    auxDist = arrayDists[j];
                    arrayDists[j] = arrayDists[j + 1];
                    arrayDists[j + 1] = auxDist;

                    auxOrden = newOrden[j];
                    newOrden[j] = newOrden[j + 1];
                    newOrden[j + 1] = auxOrden;
                }
            }
        }
        return newOrden;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        for (int i = 0; i < mTablero.length; i++) {
            for (int j = 0; j < mTablero[i].length; j++) {
                g2d.setColor(Color.BLACK);
                g2d.drawRect(MARCO_X + (j * UNIDAD), MARCO_Y + (i * UNIDAD), UNIDAD, UNIDAD);
                switch (mTablero[i][j]) {
                    case "Agente":
                        g2d.setColor(Color.GREEN);
                        g2d.drawRect(MARCO_X + (j * UNIDAD), MARCO_Y + (i * UNIDAD), UNIDAD, UNIDAD);
                        break;
                    case "Obstaculo":
                        g2d.setColor(Color.RED);
                        g2d.drawRect(MARCO_X + (j * UNIDAD), MARCO_Y + (i * UNIDAD), UNIDAD, UNIDAD);
                        break;
                    case "Piedra":
                        g2d.setColor(Color.BLUE);
                        g2d.drawRect(MARCO_X + (j * UNIDAD), MARCO_Y + (i * UNIDAD), UNIDAD, UNIDAD);
                        break;
                    case "Nave":
                        g2d.setColor(Color.WHITE);
                        g2d.drawRect(MARCO_X + (j * UNIDAD), MARCO_Y + (i * UNIDAD), UNIDAD, UNIDAD);
                        break;
                    default:
//                        throw new AssertionError();
                }
            }
        }
    }
}
