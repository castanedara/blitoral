package com.globalhitss.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "CLIENTE")
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "CODIGO")
	private Integer codigo;
	
	@NotEmpty
	@Column(name = "NOMBRE", nullable = false)
	private String nombre;
	
	@NotEmpty
	@Column(name = "APELLIDO", nullable = false)
	private String apellido;
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	@NotEmpty
	@Email(message = "email should be a valid email")
	private String email;
	
	@Column(name = "CREATE_AT")
	private Date createAt;
	
	
	private static final long serialVersionUID = 1L;


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getApellido() {
		return apellido;
	}


	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Date getCreateAt() {
		return createAt;
	}


	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
