package ufc.quixada.npi.contest.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.ufc.quixada.npi.model.Email;
import br.ufc.quixada.npi.model.Email.EmailBuilder;
import br.ufc.quixada.npi.service.SendEmailService;
import ufc.quixada.npi.contest.model.Pessoa;
import ufc.quixada.npi.contest.model.Token;
import ufc.quixada.npi.contest.util.Constants;

@Service
public class EnviarEmailService {
	@Autowired
	private SendEmailService service;
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private TokenService tokenService;
	
	//@Autowired
	//private EnviarEmailService emailService;
	
	public boolean enviarEmail(String titulo,String assunto, String emailDestinatario, String corpo) {
		
		
		EmailBuilder emailBuilder = new EmailBuilder(titulo,Constants.ENDERECO_EMAIL_CONTEST,assunto,emailDestinatario,corpo);
		
		Email email = new Email(emailBuilder);
		
        
		try {
			service.sendEmail(email);
			return true;
		} catch (MailException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	public String esqueciSenhaEmail(@RequestParam String email, RedirectAttributes redirectAttributes, HttpServletRequest request,String url) throws Exception{
		Pessoa pessoa = pessoaService.getByEmail(email);
		//String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		
		if(pessoa!=null){
			Token token = tokenService.novoToken(pessoa, Constants.ACAO_RECUPERAR_SENHA);
			String corpo = "Você pode alterar sua senha no link a seguir: "+url+"/resetar-senha/"+token.getToken();
			enviarEmail("Redefinição de senha", "[Contest] Redefinição de senha", email, corpo);
		}		
		//redirectAttributes.addFlashAttribute("esqueciSenha", true);
		return "";
	}
	

}