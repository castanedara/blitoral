package ec.fin.litoral.prueba.web.app.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import ec.fin.litoral.prueba.web.app.models.Usuario;

@Controller
@RequestMapping("/app")
public class IndexController {

	// @RequestMapping(value="/index", method = RequestMethod.GET)
	@GetMapping({ "/index", "/", "/home", "" })
	public String index(Model model) {
		model.addAttribute("titulo", "Spring Framework Ronald!");
		return "index";
	}

	@RequestMapping("/perfil")
	public String perfil(Model model) {
		Usuario usuario = new Usuario();

		usuario.setApellido("Castaneda");
		usuario.setNombre("Ronald");
//		usuario.setEmail("ronald.castanedaau@gmail.com");
		model.addAttribute("usuario", usuario);
		model.addAttribute("titulo", "Perfil de Usuario: ".concat(usuario.getNombre()));
		return "perfil";
	}
 
	@RequestMapping("/listar")
	public String listar(Model model) {
		List<Usuario> usuarios = new ArrayList<>();

//		usuario.setApellido("Castaneda");
//		usuario.setNombre("Ronald");
//		usuario.setEmail("ronald.castanedaau@gmail.com");
		usuarios.add(new Usuario("Ronald", "Castaneda", "ronald@gamil.com"));
		usuarios.add(new Usuario("Adres", "Castaneda", "Adres@gamil.com"));
		model.addAttribute("usuarios", usuarios);
		model.addAttribute("titulo", "Lista de usuarios");
		return "listar";
	}

}
