package com.convivir.app.dto;

public class LoginResponse {
	
	private String token;
	private String rol;
	private String correo;
	private String mensaje;
	
	public LoginResponse(String token, String rol, String correo, String mensaje) {
		this.token = token;
		this.rol = rol;
		this.correo = correo;
		this.mensaje = mensaje;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}
