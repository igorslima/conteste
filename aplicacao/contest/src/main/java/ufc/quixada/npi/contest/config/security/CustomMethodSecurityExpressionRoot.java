package ufc.quixada.npi.contest.config.security;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ufc.quixada.npi.contest.model.Pessoa;
import ufc.quixada.npi.contest.service.PessoaService;


public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations{
	
	PessoaService pessoaService;
	
	
	public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }
 
    public boolean isMember() {
        /*String cpf = SecurityContextHolder.getContext().getAuthentication().getName();
        Pessoa pessoa = pessoaService.getByCpf(cpf);
        if(pessoa != null){
        	for(ParticipacaoEvento participacao : pessoa.getParticipacoesEvento()){
            	if(participacao.getEvento().getId() == eventoId){
            		return true;
            	}
            }
        }*/
    	
    	Pessoa pessoa = (Pessoa) this.getPrincipal();
    	if(pessoa.getCpf().equals("11111111109")){
    		return true;
    	}
    	return false;
    }

	@Override
	public void setFilterObject(Object filterObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getFilterObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReturnObject(Object returnObject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getReturnObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getThis() {
		// TODO Auto-generated method stub
		return null;
	}
}
