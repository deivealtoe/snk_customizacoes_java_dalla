package br.com.dalla.deive.util;

import java.time.LocalDate;

public class Main {
	public static void main(String[] args) {
		LocalDate dataDeDemissao = LocalDate.of(2020, 4, 10);
		LocalDate dataDeHojeSubtraida = LocalDate.now();
		
		dataDeHojeSubtraida = dataDeHojeSubtraida.minusMonths(20);
		
		System.out.println("Data de demiss�o: " + dataDeDemissao);
		System.out.println("20 meses atr�s a partir de hoje: " + dataDeHojeSubtraida);
		
		if (dataDeHojeSubtraida.compareTo(dataDeDemissao) == 1) {
			System.out.println("Pode contratar novamente!");
		} else {
			System.out.println("N�o pode contratar!");
		}
	}
}
