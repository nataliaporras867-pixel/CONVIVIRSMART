package com.convivir.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.convivir.app.repository.UsuarioRepository;
import com.convivir.app.model.Usuario;
import com.convivir.app.model.Pqr;
import com.convivir.app.service.PqrService;
import com.convivir.app.service.CuotaService;
import com.convivir.app.service.ReservaService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminWebController {
	
	private final UsuarioRepository usuarioRepository;
	private final PqrService pqrService;
	private final CuotaService cuotaService;
	private final ReservaService reservaService;
	
	
	public AdminWebController(UsuarioRepository usuarioRepository, 
			                  PqrService pqrService, 
			                  CuotaService cuotaService, 
			                  ReservaService reservaService) {
		this.usuarioRepository = usuarioRepository;
		this.pqrService = pqrService;
		this.cuotaService = cuotaService;
		this.reservaService = reservaService;
	}
	
	
    @GetMapping("/morosos")
    public String mostrarMorosos() {
        return "admin/morosos"; 
    }
    
    
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
		
		List<Usuario> residentes = usuarioRepository.findByRol(Usuario.Rol.RESIDENTE);
		List<Usuario> propietarios = usuarioRepository.findByRol(Usuario.Rol.PROPIETARIO);
		
		long totalResidentes = residentes.size();
		long totalPropietarios = propietarios.size();
		
		model.addAttribute("totalResidentes", totalResidentes + totalPropietarios);
		model.addAttribute("totalPropietarios", totalPropietarios);
		
		List<Usuario> ultimosResidentes = residentes.stream()
				.limit(5)
				.collect(Collectors.toList());
		model.addAttribute("ultimosResidentes", ultimosResidentes);
		
		List<Pqr> todasLasPqrs = (List<Pqr>) pqrService.listarTodas();
		if (todasLasPqrs == null) {
			todasLasPqrs = new ArrayList<>();
		}
		
		long pqrsAbiertas = todasLasPqrs.stream()
				.filter(pqr -> "PENDIENTE".equalsIgnoreCase(pqr.getEstado()) 
				            || "EN_PROCESO".equalsIgnoreCase(pqr.getEstado()))
				.count();
		model.addAttribute("pqrsAbiertas", pqrsAbiertas);
		
		List<Pqr> ultimasPqrs = todasLasPqrs.stream()
				.limit(5)
				.collect(Collectors.toList());
		model.addAttribute("ultimasPqrs", ultimasPqrs);
		
		long cuotasPendientes = cuotaService.listarTodas().stream()
				.filter(c -> "PENDIENTE".equalsIgnoreCase(c.getEstado()))
				.count();
		model.addAttribute("cuotasPendientes", cuotasPendientes);
		
		
		long totalMorosos = cuotasPendientes; 
		model.addAttribute("totalMorosos", totalMorosos);
		
		
		model.addAttribute("reservasHoy", 0); 
		
		return "admin/dashboard";
	}
}