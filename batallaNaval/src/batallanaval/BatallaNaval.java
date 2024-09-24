package batallanaval;
import PaqueteLectura.GeneradorAleatorio;
import PaqueteLectura.Lector;

public class BatallaNaval {
    
    public static final int CANTIDAD_BARCOS = 3; // Esta constante indica la cantidad de barcos con la que se juega
    
    public static void imprimirMatriz(boolean[][] matrizJugador, int dimensionX, int dimensionY){
        //System.out.println("  =TU-TABLERO==");
        System.out.println("  | " + 1 + " " + 2 + " " + 3 + " " + 4 + " " + 5 + " |");
        System.out.println("==============="); 
        for(int i=0; i<dimensionY; i++){
           System.out.print((i+1) + " | ");
           for(int j=0; j<dimensionX; j++){
               if(matrizJugador[i][j] == false){
                   System.out.print("0 ");
               } else {
                   System.out.print("1 ");
               }
           }
           System.out.println("|");
        }
        System.out.println("===============");
    }
    
    public static void imprimirMatrices(boolean[][] matriz1, boolean[][] matriz2, int filas, int columnas){
    for(int i = 0; i < filas; i++){
        // Imprimir fila de la primera matriz
        for (int j = 0; j < columnas; j++) {
            if (matriz1[i][j]) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
        }

        // Separar ambas matrices con un espacio
        System.out.print("\t"); // Esto añade un tabulador entre las dos matrices

        // Imprimir fila de la segunda matriz
        for (int j = 0; j < columnas; j++) {
            if (matriz2[i][j]) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
        }

        // Mover a la siguiente línea
        System.out.println();
        }
    }
    
    public static void inicializarMatriz(boolean[][] matrizJugador, int dimensionX, int dimensionY){
        for(int i=0; i<dimensionY; i++){
           for(int j=0; j<dimensionX; j++){
               matrizJugador[i][j] = false;
           } 
        }
    }
    
    public static void empezarJugador(boolean[][] matrizJugador, int dimensionX, int dimensionY, int cantBarcos){
        while(cantBarcos != 0){
            System.out.print("Ingrese la fila " + "(1-" + dimensionX + ")" + ": ");
            int fila = Lector.leerInt();
            System.out.print("Ingrese la columna " + "(1-" + dimensionY + ")" + ": ");
            int columna = Lector.leerInt();
            System.out.println(" ");
            
            if (matrizJugador[fila-1][columna-1] == true){
                System.out.println("Ya hay un barco en esa posicion!");
            } else {
                matrizJugador[fila-1][columna-1] = true;
                System.out.flush();
                imprimirMatriz(matrizJugador, dimensionX, dimensionY);
                System.out.println("\nHas colocado un barco en: " + fila + ", " + columna + "\n");
                cantBarcos--;
            }
        }
    }
    
    public static void empezarMaquina(boolean[][] matrizMaquina, int dimensionX, int dimensionY, int cantBarcos){
        while(cantBarcos != 0){
            int fila = GeneradorAleatorio.generarInt(dimensionY); // Generamos una posicion aleatoria
            int columna = GeneradorAleatorio.generarInt(dimensionX);
            
            if(matrizMaquina[fila][columna] == false){ // Verificamos si no hay barco en la posicion
                matrizMaquina[fila][columna] = true;
                cantBarcos--;
            }
        }
    }
    
    public static void pantallaCarga(){
        char[] spinner = {'-', '/', '|', '\\'};
        int tiempoTotal = 3500;
        int intervalo = 250;
        int iteraciones = tiempoTotal/intervalo;
        
        for(int i=0; i<iteraciones; i++){
            System.out.print("\r" + spinner[i % spinner.length]);
            System.out.flush(); // Forzar actualizacion inmediata de la consola
            try {
                Thread.sleep(intervalo);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        
        // Dimensiones y cantidad de barcos
        int dimensionX = 5;
        int dimensionY = 5;
        int cantBarcos = CANTIDAD_BARCOS;
        
        // Declaramos 2 matrices, una para el jugador y otra para el oponente
        boolean[][] matrizJugador = new boolean[dimensionY][dimensionX];
        boolean[][] matrizBombasJugador = new boolean[dimensionY][dimensionX];
        boolean[][] matrizMaquina = new boolean[dimensionY][dimensionX];
        boolean[][] matrizBombasMaquina = new boolean[dimensionY][dimensionX];
        
        // Inicializamos ambas matrices
        inicializarMatriz(matrizJugador, dimensionX, dimensionY);
        inicializarMatriz(matrizMaquina, dimensionX, dimensionY);
        
        // Estructura de control para que el usuario coloque los barcos
        empezarJugador(matrizJugador, dimensionX, dimensionY, cantBarcos);
        
        System.out.println("Has terminado de colocar tus barcos, es turno de la maquina!\n\n");
        
        cantBarcos = CANTIDAD_BARCOS;
        
        empezarMaquina(matrizMaquina, dimensionX, dimensionY, cantBarcos);
        pantallaCarga();
        System.out.println("\n\n\nLa maquina termino de colocar sus barcos, es hora de jugar!");
        System.out.flush();
        
        int barcosHundidosJugador = cantBarcos;
        int barcosHundidosMaquina = cantBarcos;
        
        while((barcosHundidosMaquina != 0)&&(barcosHundidosJugador != 0)){
            System.out.println("\n===TU TURNO!===");
            System.out.print("Ingrese la fila donde va a caer la bomba " + "(1-" + dimensionX + ")" + ": ");
            int filaJ = Lector.leerInt();
            System.out.print("Ingrese la columna donde va a caer la bomba " + "(1-" + dimensionX + ")" + ": ");
            int columnaJ = Lector.leerInt();
            
            System.out.println("Bomba en camino!\n\n");
            
            pantallaCarga();
            if(matrizMaquina[filaJ-1][columnaJ-1] == true){
                barcosHundidosJugador--;
                matrizMaquina[filaJ-1][columnaJ-1] = false;
                System.out.println("\n\n\nHas dado en el blanco!");
                System.out.println("El oponente tiene " + barcosHundidosJugador + " barcos restantes!\n");
            } else {
                System.out.println("\n\n\nAgua!\n");
            }
            
            imprimirMatrices(matrizJugador, matrizMaquina, dimensionX, dimensionY);
            
            System.out.println("\n===TURNO DEL OPONENTE===\n\n");
            pantallaCarga();
            
            int filaM = GeneradorAleatorio.generarInt(dimensionX);
            int columnaM = GeneradorAleatorio.generarInt(dimensionX);
            System.out.println("\n\n\nEl oponente ha elegido la fila " + (filaM + 1) + " y la columna " + (columnaM + 1) + "\n");
            if(matrizJugador[filaM-1][columnaM-1] == true){
                barcosHundidosMaquina--;
                matrizJugador[filaM-1][columnaM-1] = false;
                System.out.println("El oponente ha dado en el blanco!");
                System.out.println("Tienes " + barcosHundidosMaquina + " barcos restantes!");
            } else {
                System.out.println("\nAgua!\n");
            }
        }
        
        if(barcosHundidosJugador == 0){
            System.out.println("\n\n\n\n\n=====HAS GANADO!=====");
            String fin = Lector.leerString();
        } else {
            System.out.println("\n\n\n\n\n=====HAS PERDIDO!=====");
            String fin = Lector.leerString();
        }
    }
}
