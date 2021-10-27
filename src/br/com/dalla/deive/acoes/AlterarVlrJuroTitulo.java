package br.com.dalla.deive.acoes;

import java.math.BigDecimal;

import br.com.sankhya.extensions.actionbutton.AcaoRotinaJava;
import br.com.sankhya.extensions.actionbutton.ContextoAcao;
import br.com.sankhya.extensions.actionbutton.Registro;
import br.com.sankhya.extensions.actionbutton.QueryExecutor;

// D� para alterar diretamente fazendo estorno do t�tulo e alterando o valor do juros direto na tela Movimenta��o Financeira

public class AlterarVlrJuroTitulo implements AcaoRotinaJava {

	@Override
	public void doAction(ContextoAcao contextoAcao) throws Exception {
		
		Registro registrosSelecionados[] = contextoAcao.getLinhas();
        
        if (registrosSelecionados.length == 0 || registrosSelecionados.length > 1) {
            contextoAcao.mostraErro("Selecione apenas uma linha.");
        } else {
        	boolean confirmaTrueFalse = contextoAcao.confirmarSimNao("Confirma��o", "Deseja realmente alterar o valor do juros do t�tulo?", 0);
        	
        	if (confirmaTrueFalse) {
        		QueryExecutor queryAlterarVlrJuro = contextoAcao.getQuery();
                
                BigDecimal nuFin = (BigDecimal) registrosSelecionados[0].getCampo("NUFIN");
                
                queryAlterarVlrJuro.setParam("P_NUFIN", nuFin);
                queryAlterarVlrJuro.setParam("P_VLRJURO", contextoAcao.getParam("P_VLRJURO"));
                
                queryAlterarVlrJuro.update("UPDATE TGFFIN SET VLRJURO = {P_VLRJURO} WHERE NUFIN = {P_NUFIN}");
                
                queryAlterarVlrJuro.close();
        	}
        }
		
	}
	
}
