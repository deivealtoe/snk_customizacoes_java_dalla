package br.com.dalla.deive.eventos;

import java.math.BigDecimal;
import java.sql.PreparedStatement;

import br.com.sankhya.extensions.eventoprogramavel.EventoProgramavelJava;
import br.com.sankhya.jape.dao.JdbcWrapper;
import br.com.sankhya.jape.event.PersistenceEvent;
import br.com.sankhya.jape.event.TransactionContext;
import br.com.sankhya.jape.vo.DynamicVO;

/* No pedido origem
 * limpa o campo adicional AD_VLRUNITORIG da TGFITE
 * para resolver o problema: Quando o pedido de vendas � confirmado, o pr�-faturamento � criado
 * e na tabela TGFITE do pedido de vendas o campo adicional AD_VLRUNITORIG � preenchido.
 * Quando o pr�-faturamento � deletado, esse campo adicional no pedido origem continua preenchido
 * e se o tipo de negocia��o � trocado, os valores dos produtos n�o mudam.
 * Acontece quando h� promo��o � vista e o tipo de negocia��o � alterado para 10x,
 * os produtos continuam com o pre�o a vista.
 * 
 * Evento na TGFCAB
 * 
 * Para n�o ficar limitado ao acesso do usu�rio logado, feito UPDATE direto no banco de dados
 */

public class EventoLimparAdVlrUnitOrigDoPedido implements EventoProgramavelJava {

	@Override
	public void afterDelete(PersistenceEvent persistenceEvent) throws Exception {
		DynamicVO cabecalhoPreFaturamento = (DynamicVO) persistenceEvent.getVo();
		
		if (this.ehPreFaturamento(cabecalhoPreFaturamento)) {
			if (this.temPedidoOrigem(cabecalhoPreFaturamento)) {
				BigDecimal nroUnicoPedidoOrigem = cabecalhoPreFaturamento.asBigDecimal("AD_NUNOTAORIG");
				
				System.out.println("EventoLimparAdVlrUnitOrigDoPedido. N�mero �nico do pedido de venda=" + nroUnicoPedidoOrigem + ". Marcando campo AD_VLRUNITORIG = null na TGFITE");
				
				this.limparAdVlrUnitOrig(nroUnicoPedidoOrigem, persistenceEvent);
			}
		}
	}

	@Override
	public void afterInsert(PersistenceEvent arg0) throws Exception { }

	@Override
	public void afterUpdate(PersistenceEvent arg0) throws Exception { }

	@Override
	public void beforeCommit(TransactionContext arg0) throws Exception { }

	@Override
	public void beforeDelete(PersistenceEvent arg0) throws Exception { }

	@Override
	public void beforeInsert(PersistenceEvent arg0) throws Exception { }

	@Override
	public void beforeUpdate(PersistenceEvent arg0) throws Exception { }
	
	private boolean ehPreFaturamento(DynamicVO cabecalho) {
		if (cabecalho.asBigDecimalOrZero("CODTIPOPER").equals(BigDecimal.valueOf(1100))) {
			return true;
		}
		
		return false;
	}
	
	private boolean temPedidoOrigem(DynamicVO cabecalho) {
		if (!cabecalho.asBigDecimalOrZero("AD_NUNOTAORIG").equals(BigDecimal.valueOf(0))) {
			return true;
		}
		
		return false;
	}
	
	private void limparAdVlrUnitOrig(BigDecimal nroUnicoOrigem, PersistenceEvent persistenceEvent) throws Exception {
		JdbcWrapper jdbc = null;
		
		try {
			jdbc = persistenceEvent.getJdbcWrapper();
			jdbc.openSession();
			PreparedStatement pstm = null;
			pstm = jdbc.getPreparedStatement("UPDATE TGFITE SET AD_VLRUNITORIG = null WHERE NUNOTA = " + nroUnicoOrigem + ";");
		    pstm.executeUpdate();
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		} finally {
			jdbc.closeSession();
		}
	}

}
