package batallanaval;
import PaqueteLectura.GeneradorAleatorio;
import PaqueteLectura.Lector;

public class BatallaNaval {
    
    public static final int CANTIDAD_BARCOS = 3; // Esta constante indica la cantidad de barcos con la que se juega
    
    public static void limpiarConsola(int x){
        for(int i=0; i<=x; i++){
            System.out.println();
        }
    }
    
    public static void imprimirMatriz(boolean[][] matrizJugador, int dimensionX, int dimensionY){
        System.out.print("  | ");
        for(int i=1; i<=dimensionX; i++){
            System.out.print(i + " ");
        }
        System.out.print("|");
        System.out.println();
        System.out.println("---------------"); 
        for(int i=0; i<dimensionY; i++){
           System.out.print((i+1) + " | ");
           for(int j=0; j<dimensionX; j++){
               if(matrizJugador[i][j] == false){
                   System.out.print("0 ");
               } else {
                   System.out.print("1 ");
               }
           }
           System.out.println("| ");
        }
        System.out.println("---------------");
    }
    
    public static void imprimirMatrices(boolean[][] matriz1, char[][] matriz2, int filas, int columnas){
    System.out.print("  | ");
    for(int i = 1; i < 3; i++){
        for(int j = 1; j<=columnas; j++){
            System.out.print(j   + " ");
        }
        System.out.print("| ");
    }
    System.out.println();
    System.out.println("---------------------------");
    for(int i = 0; i < filas; i++){
        System.out.print(i+1 + " | ");
        for (int j = 0; j < columnas; j++) {
            if (matriz1[i][j]) {
                System.out.print("1 ");
            } else {
                System.out.print("0 ");
            }
        }

        System.out.print("| ");

        for (int j = 0; j < columnas; j++) {
            System.out.print(matriz2[i][j] + " ");
        }
        System.out.print("|");
        System.out.println();
        }
    System.out.println("---------------------------");
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
        
        GeneradorAleatorio.iniciar();
        
        limpiarConsola(1);
        System.out.println("Bienvenido al clasico juego de mesa 'Batalla Naval' pero esta vez en consola!\n(en blanco y negro y tan solo con caracteres ASCII)");
        System.out.println("Primero debes colocar tu flota en posicion:");
        limpiarConsola(1);
        
        // Dimensiones y cantidad de barcos
        int dimensionX = 5;
        int dimensionY = 5;
        int cantBarcos = CANTIDAD_BARCOS;
        
        // Declaramos 2 matrices, una para el jugador y otra para el oponente
        boolean[][] matrizJugador = new boolean[dimensionY][dimensionX];
        char[][] matrizBombasJugador = new char[dimensionY][dimensionX];
        boolean[][] matrizMaquina = new boolean[dimensionY][dimensionX];
        char[][] matrizBombasMaquina = new char[dimensionY][dimensionX];
        
        // Inicializamos ambas matrices
        inicializarMatriz(matrizJugador, dimensionX, dimensionY);
        inicializarMatriz(matrizMaquina, dimensionX, dimensionY);
        for(int i=0; i<dimensionX; i++){
            for(int j=0; j<dimensionY; j++){
                matrizBombasJugador[i][j] = '#';
                matrizBombasMaquina[i][j] = '#';
            }
        }
        
        // Estructura de control para que el usuario coloque los barcos
        empezarJugador(matrizJugador, dimensionX, dimensionY, cantBarcos);
        
        System.out.println("Has terminado de colocar tus barcos, es turno de la maquina!");
        limpiarConsola(2);
        
        cantBarcos = CANTIDAD_BARCOS;
        
        empezarMaquina(matrizMaquina, dimensionX, dimensionY, cantBarcos);
        pantallaCarga();
        limpiarConsola(3);
        System.out.println("La maquina termino de colocar sus barcos, es hora de jugar!");
        System.out.flush();
        
        int barcosHundidosJugador = cantBarcos;
        int barcosHundidosMaquina = cantBarcos;
        int filaJ;
        int columnaJ;
        
        while((barcosHundidosMaquina != 0)&&(barcosHundidosJugador != 0)){
            System.out.println("\n===TU TURNO!===");
            
            System.out.print("Ingrese la fila donde va a caer la bomba " + "(1-" + dimensionX + ")" + ": ");
            filaJ = Lector.leerInt();
            while((filaJ < 1)||(filaJ > dimensionY)){
                System.out.println("POSICION INVALIDA, RECORDA QUE EL TABLERO ES DE " + dimensionX + "X" + dimensionY + " POSICIONES!");
                filaJ = Lector.leerInt();
            }
            
            System.out.print("Ingrese la columna donde va a caer la bomba " + "(1-" + dimensionX + ")" + ": ");
            columnaJ = Lector.leerInt();
            while((columnaJ < 1)||(columnaJ > dimensionY)){
                System.out.println("POSICION INVALIDA, RECORDA QUE EL TABLERO ES DE " + dimensionX + "X" + dimensionY + " POSICIONES!");
                filaJ = Lector.leerInt();
            }
            
            System.out.println("Bomba en camino!");
            limpiarConsola(2);
            
            pantallaCarga();
            if(matrizMaquina[filaJ-1][columnaJ-1] == true){
                barcosHundidosJugador--;
                matrizBombasJugador[filaJ-1][columnaJ-1] = 'X';
                matrizMaquina[filaJ-1][columnaJ-1] = false;
                limpiarConsola(3);
                System.out.println("Has dado en el blanco!");
                System.out.println("El oponente tiene " + barcosHundidosJugador + " barcos restantes!");
                limpiarConsola(1);
            } else {
                limpiarConsola(3);
                System.out.println("Agua!");
                limpiarConsola(1);
                matrizBombasJugador[filaJ-1][columnaJ-1] = 'O';
            }
            
            imprimirMatrices(matrizJugador, matrizBombasJugador, dimensionX, dimensionY);
            
            limpiarConsola(1);
            System.out.println("===TURNO DEL OPONENTE===");
            limpiarConsola(2);
            pantallaCarga();
            
            int filaM = GeneradorAleatorio.generarInt(dimensionX);
            int columnaM = GeneradorAleatorio.generarInt(dimensionX);
            while((matrizBombasMaquina[filaM][columnaM] == 'O')||(matrizBombasMaquina[filaM][columnaM] == 'X')){
                filaM = GeneradorAleatorio.generarInt(dimensionX);
                columnaM = GeneradorAleatorio.generarInt(dimensionX);
            }
            limpiarConsola(3);
            System.out.println("El oponente ha elegido la fila " + (filaM + 1) + " y la columna " + (columnaM + 1) + "\n");
            limpiarConsola(1);
            if(matrizJugador[filaM][columnaM] == true){
                barcosHundidosMaquina--;
                matrizJugador[filaM][columnaM] = false;
                System.out.println("El oponente ha dado en el blanco!");
                System.out.println("Tienes " + barcosHundidosMaquina + " barcos restantes!");
            } else {
                limpiarConsola(1);
                System.out.println("Agua!");
                limpiarConsola(1);
            }
        }
        
        if(barcosHundidosJugador == 0){
            limpiarConsola(5);
            System.out.println("=====HAS GANADO!=====");
            String fin = Lector.leerString();
        } else {
            limpiarConsola(5);
            System.out.println("=====HAS PERDIDO!=====");
            String fin = Lector.leerString();
        }
    }
}