package main;
import java.util.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Jorge Hernán Castaño
 */
public class ProblemaCarguero {

	Vector<Elemento> almacen = new Vector<Elemento>();
	Vector<Elemento> mochila = new Vector<Elemento>();
	ArrayList<ArrayList<Integer>> arreglo = new ArrayList(); ;
	final double pesoMaximo;
	String ruta;
	int numDias;
	int elemPorDia;

	public ProblemaCarguero(int pm) throws IOException {
		pesoMaximo = pm;
		cargarDatos();
		llenarArreglo();
	}


	public static void main(String[] args) throws IOException{

		ProblemaCarguero pm = new ProblemaCarguero(20);
		pm.mostrarMochila();
		pm.resolverProblema();


	}


	public void cargarDatos() throws IOException {

		// muestra el cuadro de diálogo de archivos, para que el usuario pueda elegir el archivo a abrir
		JFileChooser selectorArchivos = new JFileChooser();
		selectorArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		//Creamos el filtro
		FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.TXT", "txt");
		//Le indicamos el filtro
		selectorArchivos.setFileFilter(filtro);
		// indica cual fue la accion de usuario sobre el jfilechooser
		int resultado = selectorArchivos.showOpenDialog(selectorArchivos);
		File archivo = selectorArchivos.getSelectedFile(); // obtiene el archivo seleccionado
		//Si el usuario, pincha en aceptar
		if(resultado==JFileChooser.APPROVE_OPTION){
			ruta = archivo.getAbsolutePath();
			//muestraContenido(ruta);
			System.out.println("Si se cargo el archivos \n");
		}

		String cadena;
		FileReader f = new FileReader(archivo);
		BufferedReader b = new BufferedReader(f);
		while((cadena = b.readLine())!=null) {
			//System.out.println(cadena);
			almacen.add(new Elemento(Integer.parseInt(cadena), Integer.parseInt(cadena)));
		}
		b.close();


	}


	public void llenarArreglo(){

		numDias = almacen.get(0).valor;

		int aux = numDias;
		while (aux != 0) {
			arreglo.add(new ArrayList());
			aux--;
		}

		int numElemDia = almacen.get(1).valor;
		int elemento = 2;

		for (ArrayList<Integer> a : arreglo) {

			while (numElemDia != 0) {
				a.add(almacen.get(elemento).valor);
				elemento++;
				numElemDia--;

			}

			if(elemento < almacen.size()){
				numElemDia = almacen.get(elemento).valor;
				elemento++;
			}else
				break; 

		}

	}

	public void mostrarMochila() {
		System.out.println();
		for(ArrayList<Integer> e: arreglo) {
			System.out.println(e);
		}
	}



	public void resolverProblema() {
		int i = 1;
		for (ArrayList<Integer> a : arreglo) {
			int numero = maxRecorridos(a);
			System.out.println("Caso#"+i+": "+numero);
			i++;
		}
	}


	public int maxRecorridos (ArrayList<Integer> sub){
		int cont = 0;
		int numMayor = calcularNumeroMayor(sub);
		double tamaño = sub.size();
		boolean parar = false;
		ArrayList<Integer> aux = sub;
		int i = 2;

		for (int j = 0; j < aux.size(); j++) {
			if(aux.get(j) >= 50){
				cont++;
				aux.remove(j);
				j--;
			}
		}

		numMayor = calcularNumeroMayor(aux);
		tamaño = aux.size();

		while (parar == false) {
			if((numMayor * tamaño)>= 50){
				borrarNumMayor(aux);
				numMayor = calcularNumeroMayor(aux);
				tamaño = Math.ceil(tamaño/i);
				cont++;
				i++;
			}else
				parar = true;

		}
		return cont;
	}


	private void borrarNumMayor(ArrayList<Integer> aux) {
		int numMayor = calcularNumeroMayor(aux);
		for (Integer i : aux) {
			if(numMayor == i){
				aux.remove(i);
				break;
			}
		}
	}


	private int calcularNumeroMayor(ArrayList<Integer> sub) {

		int max = 0;
		for (int i = 0; i < sub.size(); i++) {
			if (sub.get(i) > max) {
				max = sub.get(i);
			}
		}
		return max;
	}


}
