package ufc.quixada.npi.contest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import ufc.quixada.npi.contest.model.Evento;
import ufc.quixada.npi.contest.model.PapelEvento;
import ufc.quixada.npi.contest.model.Pessoa;
import ufc.quixada.npi.contest.service.ParticipacaoEventoService;
import ufc.quixada.npi.contest.service.PessoaService;

@Controller
@RequestMapping("/evento")
public class EventoController {

	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ParticipacaoEventoService participacaoEventoService;

	private static final String TEMPLATE_ADICIONAR_OU_EDITAR = "evento/add_ou_edit";

	@RequestMapping(value = "/adicionar", method = RequestMethod.GET)
	public String adicionarEvento() {
		return TEMPLATE_ADICIONAR_OU_EDITAR;
	}

	@RequestMapping(value = "/adicionar", method = RequestMethod.POST)
	public String adicionarEvento(@RequestParam String organizador, @Valid Evento evento, BindingResult result,
			Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("error", "Nome do Evento não pode ser vazio");
			return TEMPLATE_ADICIONAR_OU_EDITAR;
		}
			
		Pessoa pessoa = null;
		try{
			pessoa = pessoaService.findPessoaPorId(Long.valueOf(organizador));
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		
		if (pessoa != null) {
			participacaoEventoService.adicionarOuEditarParticipacaoEvento(evento, pessoa, PapelEvento.ORGANIZADOR);
		} else {
			model.addAttribute("error", "Essa pessoa não está cadastrada no sistema");
			return TEMPLATE_ADICIONAR_OU_EDITAR;
		}
		
		return "redirect:/evento";
	}
	
}