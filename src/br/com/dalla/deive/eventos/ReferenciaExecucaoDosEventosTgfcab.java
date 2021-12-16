package br.com.dalla.deive.eventos;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

/* Criado para mostrar no console quando referida a��o � executada na TGFCAB
 * */

public class ReferenciaExecucaoDosEventosTgfcab implements EventoProgramavelJava {

	public void beforeInsert(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("beforeInsert");
		this.mostrarNoConsole(event);
	}

	public void afterInsert(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("afterInsert");
		this.mostrarNoConsole(event);
	}

	public void beforeUpdate(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("beforeUpdate");
		this.mostrarNoConsole(event);
	}
	
	public void afterUpdate(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("afterUpdate");
		this.mostrarNoConsole(event);
	}

	public void beforeDelete(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("beforeDelete");
		this.mostrarNoConsole(event);
	}

	public void afterDelete(PersistenceEvent event) throws Exception {
		this.mostrarQualMomentoExecutado("afterDelete");
		this.mostrarNoConsole(event);
	}

	public void beforeCommit(TransactionContext event) throws Exception { }
	
	public void mostrarNoConsole(PersistenceEvent event) {
		System.out.println(
			"TGFCAB " +
			"Nro. �nico: " + ((DynamicVO) event.getVo()).asBigDecimalOrZero("NUNOTA") +
			" - Tipo Opera��o: " + ((DynamicVO) event.getVo()).asBigDecimalOrZero("CODTIPOPER")
		);
	}
	
	public void mostrarQualMomentoExecutado(String mensagem) {
		System.out.println("TGFCAB = " + mensagem);
	}
	
}
